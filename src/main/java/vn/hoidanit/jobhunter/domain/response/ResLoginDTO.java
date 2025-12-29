package vn.hoidanit.jobhunter.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ResLoginDTO {
    @JsonProperty("access_token")
    private String accessToken;

    private UserLogin user;

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public static class UserLogin {
        private long id;
        private String email;
        
        // Ensure role is always included in JSON serialization
        @JsonProperty("role")
        private String role;
        
        // Constructor with all fields for convenience
        public UserLogin(long id, String email, String role) {
            this.id = id;
            this.email = email;
            // Ensure role is never null - use provided role or default to "USER"
            this.role = (role != null && !role.isEmpty()) ? role : "USER";
        }
        // private String name;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserGetAccount {
        private UserLogin user;
    }
}
