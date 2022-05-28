package com.thekuzea.booking.support.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PrivilegeConverter {

    public static Set<GrantedAuthority> privilegesToAuthorities(final List<String> privileges) {
        final Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        privileges.forEach(privilege -> grantedAuthorities.add(new SimpleGrantedAuthority(privilege)));

        return grantedAuthorities;
    }
}
