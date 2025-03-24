package com.example.codingmall.Environment;

import com.example.codingmall.User.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EnvironmentController {
    private final EnvironmentService environmentService;

    @PostMapping("/plant/environment/create")
    @Operation(summary = "사용자 환경 등록")
    public ResponseEntity<String> createEnvironment(@RequestBody EnvironmentRequest environmentRequest) {
        environmentService.createEnvironment(environmentRequest);
        return ResponseEntity.ok("사용자의 환경이 등록되었습니다.");
    }
}
