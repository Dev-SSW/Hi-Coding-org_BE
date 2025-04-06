package com.example.codingmall.User.Login.OAuth2dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

@Getter
public class GoogleResponse implements OAuth2Response {
    private final Map<String, Object> attribute;
    private LocalDate birth;           //생년월일 (People API)
    private String phoneNumber;     //전화번호 (People API)

    public GoogleResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attribute.get("sub").toString();
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
    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getPhoneNumber() {
        return this.phoneNumber != null ? this.phoneNumber : null;
    }

    @Override
    public String getBirth() {
        return this.birth != null ? this.birth.toString() : null;
    }
}
