package com.azure.ad.authentication.controller;

import com.microsoft.aad.adal4j.AuthenticationResult;
import com.azure.ad.authentication.dto.UserDto;
import com.azure.ad.authentication.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by @author Arpit Khandelwal
 */
@RestController
@Validated
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginRequest loginRequest) throws Exception {
        UserDto userDto = new UserDto().setEmail(loginRequest.getEmail()).setPassword(loginRequest.getPassword());
        AuthenticationResult authenticationResult = authService.authenticateUser(userDto);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.OK.value());
        body.put("user-email", authenticationResult.getUserInfo().getDisplayableId());
        body.put("user-name", authenticationResult.getUserInfo().getGivenName());
        body.put("family-name", authenticationResult.getUserInfo().getFamilyName());
        body.put("tenant-id", authenticationResult.getUserInfo().getTenantId());

        return ResponseEntity.ok()
                .header("MS-Access-Token", authenticationResult.getAccessToken())
                .body(body);

    }
}
