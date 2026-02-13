package com.koreanit.spring.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

  private SecurityUtils() {}

  // context 주입이 되어있으면 가져오고, 아니면 null
  public static Long currentUserId() {
    Authentication a = SecurityContextHolder.getContext().getAuthentication();
    if (a == null) return null;

    Object p = a.getPrincipal();
    if (p instanceof LoginUser u) {
      return u.getId();
    }
    return null;
  }
}