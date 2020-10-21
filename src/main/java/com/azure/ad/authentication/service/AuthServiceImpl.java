package com.azure.ad.authentication.service;

import com.azure.ad.authentication.dto.UserDto;
import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by @author Arpit Khandelwal
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Value("${MS_AUTHORITY}")
    private String authority;

    @Value("${MS_GRAPH_URL}")
    private String graphURL;

    @Value("${CLIENT_ID}")
    private String clientID;

    @Override
    public AuthenticationResult authenticateUser(UserDto userDto) throws Exception {
        String email = userDto.getEmail();
        String password = new String(Base64.getDecoder().decode(userDto.getPassword()));
        ExecutorService service = Executors.newFixedThreadPool(1);
        try {
            //crate context with address of authority, throws exception if url incorrect
            AuthenticationContext context = new AuthenticationContext(authority, false, service);
            // Acquires a security token from the authority using a username/password flow.
            Future<AuthenticationResult> future = context.acquireToken(graphURL, clientID, email, password, null);
            return future.get();
        } finally {
            service.shutdown();
        }
    }
}
