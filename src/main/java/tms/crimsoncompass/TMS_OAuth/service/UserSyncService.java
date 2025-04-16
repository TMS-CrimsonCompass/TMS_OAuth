package tms.crimsoncompass.TMS_OAuth.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import tms.crimsoncompass.TMS_OAuth.client.TmsUserClient;

@Service
public class UserSyncService {

    private final TmsUserClient tmsUserClient;

    @Autowired
    public UserSyncService(TmsUserClient tmsUserClient) {
        this.tmsUserClient = tmsUserClient;
    }

    // Inner record representing the sync payload
    public record UserSyncRequest(String authId, String email, String name) {}

    public void syncUser(OAuth2User oauthUser) {
        UserSyncRequest request = new UserSyncRequest(
                (String) oauthUser.getAttribute("authId"),
                (String) oauthUser.getAttribute("email"),
                (String) oauthUser.getAttribute("name")
        );
        // Feign client call – sends a POST request to TMS /api/users/sync endpoint
        tmsUserClient.syncUser(request);
    }
}

