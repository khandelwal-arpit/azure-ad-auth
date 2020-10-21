package com.azure.ad.authentication.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * Created by @author Arpit Khandelwal
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest {

    private static final String BASE64_REGEX = "^(?:[a-zA-Z0-9+\\/]{4})*(?:|(?:[a-zA-Z0-9+\\/]{3}=)|(?:[a-zA-Z0-9+\\/]{2}==)|(?:[a-zA-Z0-9+\\/]{1}===))$";
    private static final String EMAIL_REGEX = "^[-!#-'*+\\/-9=?^-~]+(?:\\.[-!#-'*+\\/-9=?^-~]+)*@[-!#-'*+\\/-9=?^-~]+(?:\\.[-!#-'*+\\/-9=?^-~]+)+$";

    @NotEmpty(message = "Email is required")
    @Pattern(regexp = EMAIL_REGEX, message = "Invalid email address provided")
    private String email;

    @NotEmpty(message = "Password is required")
    @Pattern(regexp = BASE64_REGEX, message = "Password is not base64 encoded")
    private String password;
}

