package com.project.util;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

  public static boolean userIsAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return false;
    }
    // Check if the user is not "anonymous"
    return !(authentication.getPrincipal() instanceof String
        && authentication.getPrincipal().equals("anonymousUser"));
  }
}
