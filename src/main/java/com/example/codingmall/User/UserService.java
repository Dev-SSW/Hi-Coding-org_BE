package com.example.codingmall.User;

import com.example.codingmall.Exception.InvalidPasswordException;
import com.example.codingmall.User.Login.LoginDto.PasswordChangeRequest;
import com.example.codingmall.User.Login.LoginDto.UserDto;
import com.example.codingmall.User.Login.LoginDto.UserInfo;
import lombok.RequiredArgsConstructor;
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
    public void changePassword(PasswordChangeRequest request, String username){
        User user = userRepository.findUserByUsername(username);

        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(request.getCurrentPassword(),user.getPassword())){
            throw new InvalidPasswordException("현재 비밀번호가 일치하지 않습니다.");
        }
        // 새 비밀번호가 현재 비밀번호와 같을때
        if (passwordEncoder.matches(request.getNewPassword(),user.getPassword())){
            throw new InvalidPasswordException("새 비밀번호가 현재 비밀번호와 같습니다.");
        }

        String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());
        user.changePassword(encodedNewPassword);

        userRepository.save(user);
    }


    public UserDto getUserInfo(String username) {
        User user = userRepository.findUserByUsername(username);
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .birth(user.getBirth())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .build();
    }
}
