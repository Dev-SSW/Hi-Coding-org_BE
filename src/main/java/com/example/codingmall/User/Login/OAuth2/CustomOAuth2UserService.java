package com.example.codingmall.User.Login.OAuth2;

import com.example.codingmall.User.Login.LoginDto.UserDto;
import com.example.codingmall.User.Login.OAuth2dto.GoogleResponse;
import com.example.codingmall.User.Login.OAuth2dto.NaverResponse;
import com.example.codingmall.User.Login.OAuth2dto.OAuth2Response;
import com.example.codingmall.User.Role;
import com.example.codingmall.User.User;
import com.example.codingmall.User.UserRepository;
import com.example.codingmall.User.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        User existData = userRepository.findUserByUsername(username);
        UserDto userDto;
        if(existData == null) {
            userDto = UserDto.builder()
                    .username(username)
                    .name(oAuth2Response.getName())
                    .email(oAuth2Response.getEmail())
                    //.birth()
                    //.phoneNumber()
                    .build();
            User userEntity = userDto.toEntity();
            userRepository.save(userEntity);
            System.out.println("새로운 유저가 가입했습니다. 유저 이름은 : " + userEntity.getUsername());
            return userEntity;
        }
        else {
            //회원정보가 존재한다면 조회된 데이터로 반환
            userDto = UserDto.builder()
                    .username(username)
                    .name(existData.getName())
                    .email(existData.getEmail())
                    //.birth()
                    //.phoneNumber()
                    .build();

            User userEntity = userDto.toEntity();
            System.out.println("기존 유저를 찾았습니다. 유저 이름은 : " + userEntity.getUsername());
            return userEntity;
        }
    }
}
