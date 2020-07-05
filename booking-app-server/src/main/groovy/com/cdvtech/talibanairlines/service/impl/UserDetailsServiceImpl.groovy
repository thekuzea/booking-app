package com.cdvtech.talibanairlines.service.impl

import com.cdvtech.talibanairlines.model.entity.Profile
import com.cdvtech.talibanairlines.repository.ProfileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ProfileRepository profileRepository

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Profile profile = profileRepository.findById(username).get()
        if(profile == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>()
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"))

        return new User(profile.username, profile.password, grantedAuthorities)
    }

}
