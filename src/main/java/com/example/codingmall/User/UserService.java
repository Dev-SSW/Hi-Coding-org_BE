package com.example.codingmall.User;

import com.example.codingmall.Exception.InvalidPasswordException;
import com.example.codingmall.User.Login.LoginDto.PasswordChangeRequest;
import com.example.codingmall.User.Login.LoginDto.ResponseDto;
import com.example.codingmall.User.Login.LoginDto.UserDto;
import com.example.codingmall.User.Login.LoginDto.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        return user;
    }

    @Transactional
    public ResponseDto changePassword(PasswordChangeRequest request, User user){
       try{
           //User user = userRepository.findUserByUsername(userDetails.getUsername());

           if(user == null){
               return ResponseDto.builder()
                       .statusCode(HttpStatus.NOT_FOUND.value())
                       .error(String.valueOf(new UsernameNotFoundException("사용자를 찾을 수 없습니다.")))
                       .message("존재하지 않는 사용자입니다.")
                       .build();
           }
           //현재 비밀번호 확인
           if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword()))
               return ResponseDto.builder()
                       .statusCode(HttpStatus.BAD_REQUEST.value())
                       .message("현재 비밀번호가 일치하지 않습니다")
                       .error("비밀번호 불일치")
                       .build();

           // 새 비밀번호가 현재 비밀번호와 같을 경우
           if(passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
               return ResponseDto.builder()
                       .statusCode(HttpStatus.BAD_REQUEST.value())
                       .message("새 비밀번호가 현재 비밀번호와 같습니다.")
                       .error("비밀번호 중복")
                       .build();
           }

           String encodePassword = passwordEncoder.encode(request.getNewPassword());
           user.changePassword(encodePassword);
           userRepository.save(user);

           return ResponseDto.builder()
                   .statusCode(HttpStatus.OK.value())
                   .message("비밀번호 변경 성공")
                   .build();
       }
       catch (Exception e){
           return ResponseDto.builder()
                   .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                   .message("비밀번호 변경 중 오류가 발생하였습니다.")
                   .error("서버 오류")
                   .build();
       }
    }

    public UserInfo getUserInfo(User user) {
        if (user == null){
            return UserInfo.builder()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .error("로그인이 필요한 서비스입니다.")
                    .message("로그인이 필요합니다.")
                    .build();
        }
        try{
            String username = user.getUsername();
            //User user = userRepository.findUserByUsername(username);

            if (user == null){
                return UserInfo.builder()
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .error("해당 유저를 찾을 수 없습니다.")
                        .build();
            }

            UserDto userDto = UserDto.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .birth(user.getBirth())
                    .name(user.getName())
                    .phoneNumber(user.getPhoneNumber())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build();

            return UserInfo.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("회원 정보 조회 성공")
                    .userInfo(userDto)
                    .build();
        }
        catch (Exception e){
            return UserInfo.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .error("내부 서버 에러")
                    .message("회원 정보 조회 중 오류가 발생하였습니다.")
                    .build();
        }

    }
}
