package com.thekuzea.booking.support.util

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

final class PrivilegeConverter {

    private PrivilegeConverter() {
    }

    static Set<GrantedAuthority> privilegesToAuthorities(final List<String> privileges) {
        final Set<GrantedAuthority> grantedAuthorities = new HashSet<>()
        privileges.forEach {
            grantedAuthorities.add(new SimpleGrantedAuthority(it))
        }

        grantedAuthorities
    }
}
