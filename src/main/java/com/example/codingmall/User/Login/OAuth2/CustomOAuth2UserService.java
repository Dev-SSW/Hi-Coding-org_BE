package com.example.codingmall.User.Login.OAuth2;

import com.example.codingmall.User.Login.LoginDto.UserDto;
import com.example.codingmall.User.Login.OAuth2dto.GoogleResponse;
import com.example.codingmall.User.Login.OAuth2dto.NaverResponse;
import com.example.codingmall.User.Login.OAuth2dto.OAuth2Response;
import com.example.codingmall.User.Role;
import com.example.codingmall.User.User;
import com.example.codingmall.User.UserRepository;
import com.example.codingmall.User.UserStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        //제공자 이름 (Ex. google)
        String provider = userRequest.getClientRegistration().getRegistrationId();

        //System.out.println("provider의 이름은 : " + provider);

        //먼저 변수 선언
        OAuth2Response oAuth2Response = null;

        //제공자 별 분기
        if("google".equals(provider)) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
            /*try {
                // Google API 호출용 AccessToken
                String accessToken = userRequest.getAccessToken().getTokenValue();
                String peopleApiUrl = "https://people.googleapis.com/v1/people/me?personFields=birthdays,phoneNumbers";
                URL url = new URL(peopleApiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Authorization", "Bearer " + accessToken);
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> responseBody = objectMapper.readValue(connection.getInputStream(), Map.class);

                    // 생년월일 파싱
                    if (responseBody.containsKey("birthdays")) {
                        List<Map<String, Object>> birthdays = (List<Map<String, Object>>) responseBody.get("birthdays");
                        if (!birthdays.isEmpty()) {
                            Map<String, Object> date = (Map<String, Object>) birthdays.get(0).get("date");
                            if (date != null) {
                                Integer year = (Integer) date.get("year");
                                Integer month = (Integer) date.get("month");
                                Integer day = (Integer) date.get("day");
                                if (year != null && month != null && day != null) {
                                    ((GoogleResponse) oAuth2Response).setBirth(LocalDate.of(year, month, day));
                                }}}
                    }

                    // 응답에서 필요한 데이터 추출
                    System.out.println("Google People API 응답 전체: " + responseBody);
                    // 전화번호 파싱
                    if (responseBody.containsKey("phoneNumbers")) {
                        List<Map<String, Object>> phones = (List<Map<String, Object>>) responseBody.get("phoneNumbers");
                        System.out.println("phoneNumbers 응답: " + phones);
                        if (!phones.isEmpty()) {
                            String phoneNumber = (String) phones.get(0).get("value");
                            System.out.println("파싱된 전화번호: " + phoneNumber);
                            ((GoogleResponse) oAuth2Response).setPhoneNumber(phoneNumber);
                        } else {
                            System.out.println("phoneNumbers 항목은 있으나 값이 없습니다.");
                        }
                    } else {
                        System.out.println("phoneNumbers 항목이 응답에 없습니다.");
                    }
                } else {
                    System.err.println("Google People API 응답 실패. 코드: " + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace(); // 나중엔 logger로 교체하는 게 좋음
                System.err.println("Google People API 호출 중 예외 발생: " + e.getMessage());
            }*/
        } else if("naver".equals(provider)) {
            // 네이버의 경우 response 내부 값을 추출하여 처리
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        //사용자 명을 제공자_회원아이디 형식으로만들어 저장
        String username = oAuth2Response.getProvider() + "_" + oAuth2Response.getProviderId();

        //System.out.println("만들어진 사용자 명은 : " + username);

        //회원 정보가 이미 테이블에 존재하는지 확인 (중복 계정)
        Optional<User> existData = userRepository.findByUsername(username); //Optional로 일단 가져오기

        UserDto userDto;
        if(existData.isEmpty()) { //새로 만들기
            userDto = UserDto.builder()
                    .username(username)
                    .name(oAuth2Response.getName())
                    .birth(parseToLocalDate(oAuth2Response.getBirth()))
                    .phoneNumber(oAuth2Response.getPhoneNumber())
                    .role(Role.ROLE_SOCIAL)
                    .build();
            User userEntity = userDto.toEntity();
            userRepository.save(userEntity);
            System.out.println("새로운 유저가 가입했습니다. 유저 이름은 : " + userEntity.getUsername());
            return userEntity;
        }
        else {                   //기존 유저 가져오기
            User users = existData.orElseThrow(() -> new UsernameNotFoundException("이미 있는 계정이므로 USER 정보를 가져옵니다.")); //USER로 가져오기
            System.out.println("기존 유저를 찾았습니다. 유저 이름은 : " + users.getUsername());
            return users;
        }
    }

    private LocalDate parseToLocalDate(String birthStr) {
        if (birthStr == null || birthStr.isEmpty()) return null;
        return LocalDate.parse(birthStr); // "1990-01-01" 형식이어야 함
    }
}
