package com.sn.socialnetworkapp.payload.auth;

import com.sn.socialnetworkapp.util.AppConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ResetPasswordConfirmDto(@NotBlank
                                      @Email(regexp = AppConstants.EMAIL_PATTERN)
                                      String email,
                                      @NotBlank
                                      @Length(min = 4, max = 4)
                                      String code,
                                      @Length(min = 6)
                                      @NotBlank
                                      String password,
                                      @Length(min = 6)
                                      @NotBlank
                                      String confirmPassword) {
}
