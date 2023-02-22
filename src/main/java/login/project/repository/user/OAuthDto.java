package login.project.repository.user;

import java.util.Map;

public class OAuthDto {
    private Map<String, Object> attributes;
    private String attributeKey;
    private String email;
    private String name;
    private String picture;

    public static OAuthDto of(String provider, String attributeKey, Map<String, Object> attributes) {
        switch (provider) {
            case "google" :
                return ofGoogle(attributeKey, attributes);
        }
    }
}
