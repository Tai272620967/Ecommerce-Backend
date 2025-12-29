package vn.hoidanit.jobhunter.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.response.ResCreateUserDTO;
import vn.hoidanit.jobhunter.domain.response.ResUpdateUserDTO;
import vn.hoidanit.jobhunter.domain.response.ResUserDTO;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.UserRepositoty;

@Service
public class UserService {
    
    private final UserRepositoty userRepositoty;
    private final FileService fileService;
    
    @Value("${base-uri}")
    private String baseURI;

    public UserService(UserRepositoty userRepositoty, FileService fileService) {
        this.userRepositoty = userRepositoty;
        this.fileService = fileService;
    }

    public User handleCreateUser(User user) {
        return this.userRepositoty.save(user);
    }

    public void handleDeleteUser(long id) {
        this.userRepositoty.deleteById(id);
    }

    public User handleGetUserById(long id) {
        Optional<User> userOptional = this.userRepositoty.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepositoty.findByEmail(username);
    }

    public boolean isEmailExist(String email) {
        return this.userRepositoty.existsByEmail(email);
    }

    public ResCreateUserDTO convertToResCreateUserDTO(User user) {
        ResCreateUserDTO res = new ResCreateUserDTO();

        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setFirstName(user.getFirstName());
        res.setLastName(user.getLastName());
        res.setKataFirstName(user.getKataFirstName());
        res.setKataLastName(user.getKataLastName());
        res.setAddress1(user.getAddress1());
        res.setAddress2(user.getAddress2());
        res.setAddress3(user.getAddress3());
        res.setAddress4(user.getAddress4());
        res.setPhone(user.getPhone());
        res.setBirthday(user.getBirthday());
        res.setGender(user.getGender());
        res.setCreatedAt(user.getCreatedAt());
        return res;
    }

    public ResUserDTO convertToResUserDTO(User user) {
        if (user == null) {
            return null;
        }
        
        // Check if user has valid ID (not 0)
        if (user.getId() == 0) {
            return null;
        }
        
        ResUserDTO res = new ResUserDTO();
        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setFirstName(user.getFirstName());
        res.setLastName(user.getLastName());
        res.setKataFirstName(user.getKataFirstName());
        res.setKataLastName(user.getKataLastName());
        res.setAddress1(user.getAddress1());
        res.setAddress2(user.getAddress2());
        res.setAddress3(user.getAddress3());
        res.setAddress4(user.getAddress4());
        res.setPhone(user.getPhone());
        res.setBirthday(user.getBirthday());
        res.setGender(user.getGender());
        res.setRole(user.getRole());
        res.setAvatarUrl(user.getAvatarUrl());
        res.setCreatedAt(user.getCreatedAt());
        res.setUpdatedAt(user.getUpdatedAt());
        return res;
    }

    public ResUpdateUserDTO convertToResUpdateUserDTO(User user) {
        ResUpdateUserDTO res = new ResUpdateUserDTO();

        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setFirstName(user.getFirstName());
        res.setLastName(user.getLastName());
        res.setKataFirstName(user.getKataFirstName());
        res.setKataLastName(user.getKataLastName());
        res.setAddress1(user.getAddress1());
        res.setAddress2(user.getAddress2());
        res.setAddress3(user.getAddress3());
        res.setAddress4(user.getAddress4());
        res.setPhone(user.getPhone());
        res.setBirthday(user.getBirthday());
        res.setGender(user.getGender());
        res.setCreatedAt(user.getCreatedAt());
        return res;
    }

    @Transactional(readOnly = true)
    public ResultPaginationDTO handleGetAllUser(Specification<User> spec, Pageable pageable) {
        // Always use findAll without specification to avoid filtering issues
        Page<User> pageUser = this.userRepositoty.findAll(pageable);
        
        System.out.println("Total users in database: " + pageUser.getTotalElements());
        System.out.println("Users in current page: " + pageUser.getContent().size());
        
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();
        mt.setPage(pageUser.getNumber() + 1);
        mt.setPageSize(pageUser.getSize());
        mt.setPages(pageUser.getTotalPages());
        mt.setTotal(pageUser.getTotalElements());
        rs.setMeta(mt);

        // Convert users to DTOs
        List<ResUserDTO> listUser = new java.util.ArrayList<>();
        for (User user : pageUser.getContent()) {
            if (user != null) {
                System.out.println("Processing user - ID: " + user.getId() + ", Email: " + user.getEmail());
                ResUserDTO dto = convertToResUserDTO(user);
                if (dto != null) {
                    System.out.println("Converted DTO - ID: " + dto.getId() + ", Email: " + dto.getEmail());
                    listUser.add(dto);
                } else {
                    System.out.println("DTO is null for user ID: " + user.getId());
                }
            }
        }

        System.out.println("Final list size: " + listUser.size());
        rs.setResult(listUser);
        return rs;
    }

    public User handleUpdateUser(User reqUser) {
        User currentUser = this.handleGetUserById(reqUser.getId());
        if (currentUser != null) {
            currentUser.setAddress1(reqUser.getAddress1());
            currentUser.setAddress2(reqUser.getAddress2());
            currentUser.setAddress3(reqUser.getAddress3());
            currentUser.setAddress4(reqUser.getAddress4());
            currentUser.setGender(reqUser.getGender());
            currentUser.setBirthday(reqUser.getBirthday());
            currentUser.setFirstName(reqUser.getFirstName());
            currentUser.setLastName(reqUser.getLastName());
            currentUser.setKataFirstName(reqUser.getKataFirstName());
            currentUser.setKataLastName(reqUser.getKataLastName());
            currentUser.setPhone(reqUser.getPhone());
            
            // Update avatarUrl if provided
            if (reqUser.getAvatarUrl() != null) {
                currentUser.setAvatarUrl(reqUser.getAvatarUrl());
            }

            // update
            currentUser = this.userRepositoty.save(currentUser);
        }
        return currentUser;
    }

    public void updateUserToken(String token, String email) {
        User currentUser = this.handleGetUserByUsername(email);
        if (currentUser != null) {
            currentUser.setRefreshToken(token);
            this.userRepositoty.save(currentUser);
        }
    }

    public User getUserByRefreshTokenAndEmail(String token, String email) {
        return this.userRepositoty.findByRefreshTokenAndEmail(token, email);
    }
    
    public User handleUpdateUserWithAvatar(User reqUser, MultipartFile avatarFile) throws IOException, URISyntaxException {
        User currentUser = this.handleGetUserById(reqUser.getId());
        if (currentUser != null) {
            currentUser.setAddress1(reqUser.getAddress1());
            currentUser.setAddress2(reqUser.getAddress2());
            currentUser.setAddress3(reqUser.getAddress3());
            currentUser.setAddress4(reqUser.getAddress4());
            currentUser.setGender(reqUser.getGender());
            currentUser.setBirthday(reqUser.getBirthday());
            currentUser.setFirstName(reqUser.getFirstName());
            currentUser.setLastName(reqUser.getLastName());
            currentUser.setKataFirstName(reqUser.getKataFirstName());
            currentUser.setKataLastName(reqUser.getKataLastName());
            currentUser.setPhone(reqUser.getPhone());
            
            // Upload avatar if provided
            if (avatarFile != null && !avatarFile.isEmpty()) {
                String avatarUrl = uploadAvatar(avatarFile);
                currentUser.setAvatarUrl(avatarUrl);
            }

            // update
            currentUser = this.userRepositoty.save(currentUser);
        }
        return currentUser;
    }
    
    private String uploadAvatar(MultipartFile avatarFile) throws IOException, URISyntaxException {
        if (avatarFile == null || avatarFile.isEmpty()) {
            return null;
        }

        // Store file in uploads/images/users/ directory
        String finalName = this.fileService.store(avatarFile, "uploads/images/users");

        // Return URL path to serve image via HTTP endpoint /uploads/images/users/**
        return "/uploads/images/users/" + finalName;
    }
}
