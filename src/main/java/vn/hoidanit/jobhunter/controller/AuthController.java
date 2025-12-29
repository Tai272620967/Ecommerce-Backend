package vn.hoidanit.jobhunter.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.request.ReqLoginDTO;
import vn.hoidanit.jobhunter.domain.response.ResLoginDTO;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.SecurityUtil;
import vn.hoidanit.jobhunter.util.anotation.ApiMessage;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;
import vn.hoidanit.jobhunter.util.constant.RoleEnum;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

        private final AuthenticationManagerBuilder authenticationManagerBuilder;
        private final SecurityUtil securityUtil;
        private final UserService userService;

        @Value("${hoidanit.jwt.refresh-token-validity-in-seconds}")
        private long refreshTokenExpiration;

        public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil, UserService userService) {
            this.authenticationManagerBuilder = authenticationManagerBuilder;
            this.securityUtil = securityUtil;
            this.userService = userService;
        }
    @PostMapping("/auth/login")
    public ResponseEntity<ResLoginDTO> login(@Valid @RequestBody ReqLoginDTO loginDto){

        //Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        
        // xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        
        // set thông tin người dùng đăng nhập vào context (có thể sử dụng sau này)
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        ResLoginDTO res = new ResLoginDTO();
        User currentUserDB = this.userService.handleGetUserByUsername(loginDto.getUsername());
        if (currentUserDB != null) {
            // Debug: Log role from database
            System.out.println("=== AuthController.login Debug ===");
            System.out.println("User email: " + currentUserDB.getEmail());
            System.out.println("User role from DB: " + currentUserDB.getRole());
            System.out.println("User role name: " + (currentUserDB.getRole() != null ? currentUserDB.getRole().name() : "NULL"));
            
            String roleName = currentUserDB.getRole() != null ? currentUserDB.getRole().name() : RoleEnum.USER.name();
            // Use constructor to ensure all fields including role are set
            ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin(
                currentUserDB.getId(),
                currentUserDB.getEmail(),
                roleName
            );
            System.out.println("UserLogin role: " + userLogin.getRole());
            System.out.println("UserLogin object: " + userLogin.toString());
            System.out.println("UserLogin getRole() method: " + userLogin.getRole());
            res.setUser(userLogin);
            System.out.println("ResLoginDTO user after set: " + res.getUser());
            System.out.println("ResLoginDTO user role after set: " + (res.getUser() != null ? res.getUser().getRole() : "NULL"));
        }

        //create a token
        String access_token = this.securityUtil.createAccessToken(authentication.getName(), res.getUser());

        res.setAccessToken(access_token);

        //create refresh token
        String refresh_token = this.securityUtil.createRefreshToken(loginDto.getUsername(), res);

        //update user
        this.userService.updateUserToken(refresh_token, loginDto.getUsername());

        //set cookies
        ResponseCookie resCookies = ResponseCookie.from("refresh_token", refresh_token)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(refreshTokenExpiration)
            .build();

        // Debug: Log final response before returning
        System.out.println("=== Final ResLoginDTO before return ===");
        System.out.println("ResLoginDTO user: " + res.getUser());
        if (res.getUser() != null) {
            System.out.println("ResLoginDTO user role: " + res.getUser().getRole());
            System.out.println("ResLoginDTO user id: " + res.getUser().getId());
            System.out.println("ResLoginDTO user email: " + res.getUser().getEmail());
        }
        
        return ResponseEntity
            .ok()
            .header(HttpHeaders.SET_COOKIE, resCookies.toString())
            .body(res);
    }

    @GetMapping("/auth/account")
    @ApiMessage("fetch account")
    public ResponseEntity<ResLoginDTO.UserGetAccount> getAccount() {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ?
        SecurityUtil.getCurrentUserLogin().get() : "";
        User currentUserDB = this.userService.handleGetUserByUsername(email);
        ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin();
        ResLoginDTO.UserGetAccount userGetAccount = new ResLoginDTO.UserGetAccount();

        if (currentUserDB != null) {
            userLogin.setId(currentUserDB.getId());
            userLogin.setEmail(currentUserDB.getEmail());
            userLogin.setRole(currentUserDB.getRole() != null ? currentUserDB.getRole().name() : RoleEnum.USER.name());
            // userLogin.setName(currentUserDB.getName());
            userGetAccount.setUser(userLogin);
        }
        return ResponseEntity.ok().body(userGetAccount);
    }

    @GetMapping("/auth/refresh")
    @ApiMessage("Get User by refresh token")
    public ResponseEntity<ResLoginDTO> getRefreshToken(
        @CookieValue(name = "refresh_token") String refresh_token
    ) throws IdInvalidException {
        // check valid
        Jwt decodedToken = this.securityUtil.checkValidRefreshToken(refresh_token);
        String email = decodedToken.getSubject();

        // check user by token + email
        User currentUser = this.userService.getUserByRefreshTokenAndEmail(refresh_token, email);
        if (currentUser == null) {
            throw new IdInvalidException("Refresh Token không hợp lệ");
        }

        // issue new token/set refresh token as cookies
        ResLoginDTO res = new ResLoginDTO();
        User currentUserDB = this.userService.handleGetUserByUsername(email);
        if (currentUserDB != null) {
            ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin(
                currentUserDB.getId(),
                currentUserDB.getEmail(),
                currentUserDB.getRole() != null ? currentUserDB.getRole().name() : RoleEnum.USER.name()
            );
            res.setUser(userLogin);
        }

        //create a token
        String access_token = this.securityUtil.createAccessToken(email, res.getUser());

        res.setAccessToken(access_token);

        //create refresh token
        String new_refresh_token = this.securityUtil.createRefreshToken(email, res);

        //update user
        this.userService.updateUserToken(refresh_token, email);

        //set cookies
        ResponseCookie resCookies = ResponseCookie.from("refresh_token", new_refresh_token)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(refreshTokenExpiration)
            .build();

        return ResponseEntity
            .ok()
            .header(HttpHeaders.SET_COOKIE, resCookies.toString())
            .body(res);
    }

    @PostMapping("/auth/logout")
    @ApiMessage("Logout User")
    public ResponseEntity<Void> logout() throws IdInvalidException {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";

        if (email.equals("")) {
            throw new IdInvalidException("Access Token không hợp lệ");
        }

        // update refresh token = null
        this.userService.updateUserToken(null, email);

        // remove refresh token cookie
        ResponseCookie resCookies = ResponseCookie.from("refresh_token", null)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(0)
            .build();

        return ResponseEntity
            .ok()
            .header(HttpHeaders.SET_COOKIE, resCookies.toString())
            .body(null);
    }
}
