package com.example.codingmall.User;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoResponse {
    private String username;
    private String name;
    private String phoneNumber;

    public static UserInfoResponse from(User user) {
        return UserInfoResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
