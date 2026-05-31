package com.sn.socialnetworkapp.util;

import com.sn.socialnetworkapp.entity.User;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

@UtilityClass
public class CurrentUserUtil {

    @NonNull
    public static User getCurrentUser() {
        return (User) Objects.requireNonNull(
                Objects.requireNonNull(
                        SecurityContextHolder.getContext().getAuthentication()
                ).getPrincipal()
        );
    }

}
