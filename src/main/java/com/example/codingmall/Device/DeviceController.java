package com.example.codingmall.Device;

import com.example.codingmall.User.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;

    @PostMapping("/user/device/register")
    @Operation(summary = "일련번호 등록")
    public ResponseEntity<String> registerDevice(@RequestParam(name = "serialNumber") String serialNumber, @AuthenticationPrincipal User user) {
        deviceService.registerDevice(serialNumber, user);
        return ResponseEntity.ok("일련번호 등록 완료 및 ROLE_PLANT 부여됨");
    }
}
