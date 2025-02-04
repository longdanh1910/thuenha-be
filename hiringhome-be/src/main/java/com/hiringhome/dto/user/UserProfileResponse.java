package com.hiringhome.dto.user;

import com.hiringhome.entity.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponse {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private UserRole role;
    private String avatarUrl;
}
