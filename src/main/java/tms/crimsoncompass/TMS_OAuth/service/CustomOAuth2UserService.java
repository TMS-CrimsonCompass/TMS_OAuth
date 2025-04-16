package tms.crimsoncompass.TMS_OAuth.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User user = super.loadUser(userRequest);
        Map<String, Object> attributes = user.getAttributes();

        // Map Google attributes to our internal names
        Map<String, Object> mappedAttributes = new HashMap<>();
        mappedAttributes.put("authId", attributes.get("sub"));
        mappedAttributes.put("email", attributes.get("email"));
        mappedAttributes.put("name", attributes.get("name"));

        return new DefaultOAuth2User(user.getAuthorities(), mappedAttributes, "email");
    }
}

