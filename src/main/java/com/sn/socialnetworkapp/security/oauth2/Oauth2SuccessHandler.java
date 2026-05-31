package com.sn.socialnetworkapp.security.oauth2;

import com.sn.socialnetworkapp.entity.Attachment;
import com.sn.socialnetworkapp.entity.User;
import com.sn.socialnetworkapp.repository.UserRepository;
import com.sn.socialnetworkapp.security.JWTService;
import com.sn.socialnetworkapp.service.AttachmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AttachmentService attachmentService;
    private final JWTService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(@NonNull HttpServletRequest request,
                                        @NonNull HttpServletResponse response,
                                        @NonNull Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) Objects.requireNonNull(authentication.getPrincipal());

        String email = Objects.requireNonNull(oAuth2User.getAttribute("email"));

        User user;
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            user.setActive(true);
        } else {
            user = new User();
            user.setEmail(email);
            user.setPassword(UUID.randomUUID().toString());
            user.setFirstname(oAuth2User.getAttribute("given_name"));
            user.setLastname(oAuth2User.getAttribute("family_name"));
            user.setActive(true);

            // save profile
            String picture = oAuth2User.getAttribute("picture");
            if (picture != null) {
                try (HttpClient client = HttpClient.newHttpClient()) {
                    HttpRequest req = HttpRequest.newBuilder()
                            .uri(URI.create(picture))
                            .build();

                    HttpResponse<InputStream> resp = client.send(req, HttpResponse.BodyHandlers.ofInputStream() );

                    try (InputStream is = resp.body()) {
                        Attachment avatar = attachmentService.create(is.readAllBytes(), "avatar.jpg");
                        user.setAvatar(avatar);
                    }
                } catch (InterruptedException _) {
                }
            }

            userRepository.save(user);
        }

        // create token
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        response.sendRedirect( "http://localhost:5173/oauth2-success?accessToken=" + accessToken + "&refreshToken=" + refreshToken);
    }
}
