package com.azure.ad.authentication.service;

import com.microsoft.aad.adal4j.AuthenticationResult;
import com.azure.ad.authentication.dto.UserDto;

/**
 * Created by @author Arpit Khandelwal
 */
public interface AuthService {
    AuthenticationResult authenticateUser(UserDto userDto) throws Exception;
}
