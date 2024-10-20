package dev.mosaleh.Library.Management.System.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {
    UserDetailsService userDetailsService();
}

