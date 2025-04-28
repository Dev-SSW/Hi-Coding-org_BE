package com.example.codingmall.Device;

import com.example.codingmall.Exception.AlreadyHasPlantRoleException;
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
        OrderItem orderItem = orderItemRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new SerialNumberNotFoundException("일련번호를 찾을 수 없습니다.") );
        User foundUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다."));
        if (foundUser.getRole() == Role.ROLE_PLANT) {
            throw new AlreadyHasPlantRoleException("이미 식물 관리 권한이 부여된 사용자입니다.");
        }
        // 2. 일련번호가 있다면 Device 테이블에 등록
        Device device = Device.createDevice(user);
        deviceRepository.save(device);

        if (foundUser.getRole() == Role.ROLE_ADMIN) {
            throw new AlreadyHasPlantRoleException("ADMIN은 이미 식물 관리 권한이 부여되어 있습니다.");
        } else {
            foundUser.setRole(Role.ROLE_PLANT);
        }
        return device.getId();
    }
}
