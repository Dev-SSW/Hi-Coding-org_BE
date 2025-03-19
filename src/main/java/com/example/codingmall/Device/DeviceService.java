package com.example.codingmall.Device;

import com.example.codingmall.Exception.SerialNumberNotFoundException;
import com.example.codingmall.Exception.UserNotFoundException;
import com.example.codingmall.Order.Order;
import com.example.codingmall.Order.OrderRepository;
import com.example.codingmall.OrderItem.OrderItem;
import com.example.codingmall.OrderItem.OrderItemRepository;
import com.example.codingmall.User.Role;
import com.example.codingmall.User.User;
import com.example.codingmall.User.UserRepository;
import com.example.codingmall.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long registerDevice(String serialNumber, User user) {
        // 1. SN이 있는 OrderItem 찾기
        OrderItem orderItem = orderItemRepository.findBySerialNumber(serialNumber).orElseThrow(() -> new SerialNumberNotFoundException("일련번호를 찾을 수 없습니다.") );

        // 2. 일련번호가 있다면 Device 테이블에 등록
        Device device = Device.createDevice(user);
        deviceRepository.save(device);

        // 3. 유저가 비영속 상태로 조회될 수 있으므로 다시 한 번 find
        User user1 = userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다."));

        // 4. 유저에게 ROLE_PLANT 역할 부여하기 (변경 감지로 수정하기에 save 작업 미필요)
        if (user1.getRole() != Role.ROLE_ADMIN && user1.getRole() != Role.ROLE_SOCIAL) {
            user1.setRole(Role.ROLE_PLANT);
        }
        return device.getId();
    }
}
