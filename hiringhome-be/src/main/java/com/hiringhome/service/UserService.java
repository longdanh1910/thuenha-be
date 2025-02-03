//package com.hiringhome.service;
//
//import com.hiringhome.dto.user.UserProfileRequest;
//import com.hiringhome.dto.user.UserProfileResponse;
//import com.hiringhome.entity.User;
//import com.hiringhome.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final String UPLOAD_DIR = "uploads/avatars/";
//
//    public UserProfileResponse getMyProfile() {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        return mapToResponse(user);
//    }
//
//    public UserProfileResponse updateProfile(UserProfileRequest request) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
//            if (userRepository.existsByEmail(request.getEmail())) {
//                throw new RuntimeException("Email already in use");
//            }
//            user.setEmail(request.getEmail());
//        }
//
//        if (request.getPhoneNumber() != null) {
//            user.setPhoneNumber(request.getPhoneNumber());
//        }
//
//        if (request.getFullName() != null) {
//            user.setFullName(request.getFullName());
//        }
//
//        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
//            user.setPassword(passwordEncoder.encode(request.getPassword()));
//        }
//
//        return mapToResponse(userRepository.save(user));
//    }
//
//    public void uploadAvatar(MultipartFile avatar) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        try {
//            String fileName = UUID.randomUUID().toString() + "_" + avatar.getOriginalFilename();
//            Path uploadPath = Paths.get(UPLOAD_DIR);
//            if (!Files.exists(uploadPath)) {
//                Files.createDirectories(uploadPath);
//            }
//            Files.copy(avatar.getInputStream(), uploadPath.resolve(fileName));
//
//            user.setAvatarUrl(UPLOAD_DIR + fileName);
//            userRepository.save(user);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to upload avatar", e);
//        }
//    }
//
//    private UserProfileResponse mapToResponse(User user) {
//        UserProfileResponse response = new UserProfileResponse();
//        response.setId(user.getId());
//        response.setUsername(user.getUsername());
//        response.setEmail(user.getEmail());
//        response.setPhoneNumber(user.getPhoneNumber());
//        response.setFullName(user.getFullName());
//        response.setRole(user.getRole());
//        response.setAvatarUrl(user.getAvatarUrl());
//        response.setCreatedAt(user.getCreatedAt());
//        return response;
//    }
//}
