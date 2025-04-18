package tms.crimsoncompass.TMS_OAuth.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tms.crimsoncompass.TMS_OAuth.util.JwtUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserSyncService userSyncService;
    private final JwtUtils jwtUtils;

    public OAuth2AuthenticationSuccessHandler(UserSyncService userSyncService, JwtUtils jwtUtils) {
        this.userSyncService = userSyncService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Extract user details from OAuth2User
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        // Trigger sync of user data to TMS service
        userSyncService.syncUser(oauthUser);

        // Generate a JWT containing the user's internal authId
        String authId = (String) oauthUser.getAttribute("authId");
        String token = jwtUtils.generateToken(authId);

        // response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");

        // Redirect with the generated token (in production, consider returning token as a secure cookie)
        response.sendRedirect("http://localhost:3000?token=" + token);
    }
}
