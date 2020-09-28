package com.personal.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.personal.domain.Authority;
import com.personal.domain.CustomUserDetails;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//User user = usersRepository.findByUsername(username);
		User user = null;

        if (user == null) {
            throw new UsernameNotFoundException(username + "is not found.");
        }
		
		CustomUserDetails customUserDetails = new CustomUserDetails();
		customUserDetails.setUsername(user.getUsername());
		customUserDetails.setPassword(user.getPassword());
		customUserDetails.setAuthorities(getAuthorities(username));
		customUserDetails.setEnabled(true);
        customUserDetails.setAccountNonExpired(true);
        customUserDetails.setAccountNonLocked(true);
        customUserDetails.setCredentialsNonExpired(true);

		return customUserDetails;
	}
	
	public Collection<GrantedAuthority> getAuthorities(String username) {
        //List<Authority> authList = authoritiesRepository.findByUsername(username);
		List<Authority> authList = null;
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Authority authority : authList) {
            authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        return authorities;
    }

}
