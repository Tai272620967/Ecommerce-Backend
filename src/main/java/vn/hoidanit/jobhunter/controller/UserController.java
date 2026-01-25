package vn.hoidanit.jobhunter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.response.ResCreateUserDTO;
import vn.hoidanit.jobhunter.domain.response.ResUpdateUserDTO;
import vn.hoidanit.jobhunter.domain.response.ResUserDTO;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.anotation.ApiMessage;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;
import vn.hoidanit.jobhunter.util.RoleUtil;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/users/register")
    @ApiMessage("Create a new user")
    public ResponseEntity<ResCreateUserDTO> createNewUser(@Valid @RequestBody User postManUser) throws IdInvalidException {
        boolean isEmailExist = this.userService.isEmailExist(postManUser.getEmail());
        if (isEmailExist) {
            throw new IdInvalidException(
                "Email " + postManUser.getEmail() + " đã tồn tại, vui lòng sử dụng email khác.");
        }
        String hashPassword = this.passwordEncoder.encode(postManUser.getPassword());
        postManUser.setPassword(hashPassword);
        User newUser = this.userService.handleCreateUser(postManUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertToResCreateUserDTO(newUser));
    }

    @DeleteMapping("/users/{id}")
    @ApiMessage("Delete a user")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) throws IdInvalidException {
        // Check if user is admin
        if (!RoleUtil.isAdmin()) {
            throw new IdInvalidException("Only admin users can delete users");
        }
        
        User currentUser = this.userService.handleGetUserById(id);
        if (currentUser == null) {
            throw new IdInvalidException("User với id = " + id + " không tồn tại.");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/users")
    @ApiMessage("fetch all users")
    public ResponseEntity<ResultPaginationDTO> getAllUser(
        @Filter Specification<User> spec,
        Pageable pageable
    ) throws IdInvalidException {
        // Check if user is admin
        if (!RoleUtil.isAdmin()) {
            throw new IdInvalidException("Only admin users can view all users");
        }
        ResultPaginationDTO result = this.userService.handleGetAllUser(spec, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{id}")
    @ApiMessage("fetch user by id")
    public ResponseEntity<ResUserDTO> getUserById(@PathVariable("id") long id) throws IdInvalidException {
        User fetchUser = this.userService.handleGetUserById(id);
        if (fetchUser == null) {
            throw new IdInvalidException("User với id = " + id + " không tồn tại");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.convertToResUserDTO(fetchUser));
    }

    @PutMapping("/users")
    @ApiMessage("Update a user")
    public ResponseEntity<ResUpdateUserDTO> updateUser(@RequestBody User user) throws IdInvalidException {
        // Check if user is admin
        if (!RoleUtil.isAdmin()) {
            throw new IdInvalidException("Only admin users can update users");
        }
        User updateUser = this.userService.handleUpdateUser(user);
        if (updateUser == null) {
            throw new IdInvalidException("User với id = " + user.getId() + " không tồn tại");
        }
        return ResponseEntity.ok(this.userService.convertToResUpdateUserDTO(updateUser));
    }
    
    @PutMapping("/users/{id}")
    @ApiMessage("Update a user with avatar")
    public ResponseEntity<ResUpdateUserDTO> updateUserWithAvatar(
            @PathVariable("id") long id,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "address1", required = false) String address1,
            @RequestParam(value = "address2", required = false) String address2,
            @RequestParam(value = "address3", required = false) String address3,
            @RequestParam(value = "address4", required = false) String address4,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "birthday", required = false) java.sql.Date birthday,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile
    ) throws IdInvalidException, java.io.IOException, java.net.URISyntaxException {
        // Check if user is admin
        if (!RoleUtil.isAdmin()) {
            throw new IdInvalidException("Only admin users can update users");
        }
        
        User reqUser = new User();
        reqUser.setId(id);
        reqUser.setFirstName(firstName);
        reqUser.setLastName(lastName);
        reqUser.setAddress1(address1);
        reqUser.setAddress2(address2);
        reqUser.setAddress3(address3);
        reqUser.setAddress4(address4);
        reqUser.setPhone(phone);
        reqUser.setBirthday(birthday);
        if (gender != null) {
            reqUser.setGender(vn.hoidanit.jobhunter.util.constant.GenderEnum.valueOf(gender.toUpperCase()));
        }
        
        User updateUser = this.userService.handleUpdateUserWithAvatar(reqUser, avatarFile);
        if (updateUser == null) {
            throw new IdInvalidException("User với id = " + id + " không tồn tại");
        }
        return ResponseEntity.ok(this.userService.convertToResUpdateUserDTO(updateUser));
    }
    
    @GetMapping("/users/me")
    @ApiMessage("Get current user profile")
    public ResponseEntity<ResUserDTO> getCurrentUserProfile() throws IdInvalidException {
        String email = vn.hoidanit.jobhunter.util.SecurityUtil.getCurrentUserLogin().isPresent() 
            ? vn.hoidanit.jobhunter.util.SecurityUtil.getCurrentUserLogin().get() 
            : "";
        if (email.isEmpty()) {
            throw new IdInvalidException("User not authenticated");
        }
        User currentUser = this.userService.handleGetUserByUsername(email);
        if (currentUser == null) {
            throw new IdInvalidException("User not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.convertToResUserDTO(currentUser));
    }
    
    @PutMapping("/users/me")
    @ApiMessage("Update current user profile")
    public ResponseEntity<ResUpdateUserDTO> updateCurrentUserProfile(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "address1", required = false) String address1,
            @RequestParam(value = "address2", required = false) String address2,
            @RequestParam(value = "address3", required = false) String address3,
            @RequestParam(value = "address4", required = false) String address4,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "birthday", required = false) java.sql.Date birthday,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile
    ) throws IdInvalidException, java.io.IOException, java.net.URISyntaxException {
        String email = vn.hoidanit.jobhunter.util.SecurityUtil.getCurrentUserLogin().isPresent() 
            ? vn.hoidanit.jobhunter.util.SecurityUtil.getCurrentUserLogin().get() 
            : "";
        if (email.isEmpty()) {
            throw new IdInvalidException("User not authenticated");
        }
        
        User currentUser = this.userService.handleGetUserByUsername(email);
        if (currentUser == null) {
            throw new IdInvalidException("User not found");
        }
        
        User reqUser = new User();
        reqUser.setId(currentUser.getId());
        reqUser.setFirstName(firstName);
        reqUser.setLastName(lastName);
        reqUser.setAddress1(address1);
        reqUser.setAddress2(address2);
        reqUser.setAddress3(address3);
        reqUser.setAddress4(address4);
        reqUser.setPhone(phone);
        reqUser.setBirthday(birthday);
        if (gender != null) {
            reqUser.setGender(vn.hoidanit.jobhunter.util.constant.GenderEnum.valueOf(gender.toUpperCase()));
        }
        
        User updateUser = this.userService.handleUpdateUserWithAvatar(reqUser, avatarFile);
        if (updateUser == null) {
            throw new IdInvalidException("Failed to update user");
        }
        return ResponseEntity.ok(this.userService.convertToResUpdateUserDTO(updateUser));
    }
}
