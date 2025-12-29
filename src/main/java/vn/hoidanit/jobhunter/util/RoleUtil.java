package vn.hoidanit.jobhunter.util;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Utility class for role-based access control
 */
public class RoleUtil {
    
    /**
     * Get the role of the current authenticated user from JWT
     * @return Optional containing the role string, or empty if not found
     */
    public static Optional<String> getCurrentUserRole() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            String role = jwt.getClaimAsString("role");
            return Optional.ofNullable(role);
        }
        
        return Optional.empty();
    }
    
    /**
     * Check if the current user has ADMIN role
     * @return true if user is ADMIN, false otherwise
     */
    public static boolean isAdmin() {
        Optional<String> role = getCurrentUserRole();
        return role.isPresent() && "ADMIN".equals(role.get());
    }
    
    /**
     * Check if the current user has a specific role
     * @param roleName the role name to check
     * @return true if user has the role, false otherwise
     */
    public static boolean hasRole(String roleName) {
        Optional<String> role = getCurrentUserRole();
        return role.isPresent() && roleName.equals(role.get());
    }
}

