package com.sn.socialnetworkapp.components;

import com.sn.socialnetworkapp.entity.User;
import com.sn.socialnetworkapp.util.CurrentUserUtil;
import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditAwareImpl implements AuditorAware<User> {
    @Override
    public @NonNull Optional<User> getCurrentAuditor() {
        return Optional.of(CurrentUserUtil.getCurrentUser());
    }
}
