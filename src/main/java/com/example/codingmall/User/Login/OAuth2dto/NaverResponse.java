package com.example.codingmall.User.Login.OAuth2dto;

import java.time.LocalDate;
import java.util.Map;

public class NaverResponse implements OAuth2Response{
    private final Map<String, Object> attribute;
    private LocalDate birth;           //생년월일
    private String phoneNumber;     //전화번호
    public NaverResponse(Map<String, Object> attribute) {
        this.attribute = (Map<String, Object>) attribute.get("response");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }

    @Override
    public String getProfileImage() {
        return null;
    }

    //People API
    @Override
    public String getBirth() {
        return this.birth != null ? this.birth.toString() : null;
    }
    @Override
    public String getPhoneNumber() {
        return this.phoneNumber != null ? this.phoneNumber : null;
    }
}
