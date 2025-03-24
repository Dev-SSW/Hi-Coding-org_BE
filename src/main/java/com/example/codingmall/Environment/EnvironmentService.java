package com.example.codingmall.Environment;

import com.example.codingmall.Device.Device;
import com.example.codingmall.Device.DeviceRepository;
import com.example.codingmall.Exception.DeviceIdNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnvironmentService {
    private final DeviceRepository deviceRepository;
    private final EnvironmentRepository environmentRepository;

    @Transactional
    public Long createEnvironment(EnvironmentRequest environmentRequest) {
        Device device = deviceRepository.findById(environmentRequest.getDeviceId()).orElseThrow(() -> new DeviceIdNotFoundException("일치하는 Device가 없습니다."));
        Environment environment = Environment.createEnvironment(device, environmentRequest);
        environmentRepository.save(environment);
        return environment.getId();
    }
}
