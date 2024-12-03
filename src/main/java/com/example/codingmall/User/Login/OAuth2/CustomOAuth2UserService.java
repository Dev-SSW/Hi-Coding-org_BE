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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                    .email(oAuth2Response.getEmail())
                    //.birth()
                    //.phoneNumber()
                    .build();
            User userEntity = userDto.toEntity();
            userEntity.setRole(Role.ROLE_SOCIAL);
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
}
