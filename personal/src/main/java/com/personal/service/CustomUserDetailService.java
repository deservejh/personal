package com.personal.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.personal.domain.Authority;
import com.personal.domain.CustomUserDetails;
import com.personal.domain.User;
import com.personal.mapper.AuthorityMapper;
import com.personal.mapper.UserMapper;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	AuthorityMapper authorityMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.selectUserByUserName(username);
		
		if (user == null) { throw new UsernameNotFoundException(username + "is not found."); }
		
		CustomUserDetails customUserDetails = new CustomUserDetails();
		customUserDetails.setUsername(user.getUserName());
		customUserDetails.setPassword(user.getPassword());
		customUserDetails.setAuthorities(getAuthorities(user.getUserId()));
		customUserDetails.setEnabled(true);
        customUserDetails.setAccountNonExpired(true);
        customUserDetails.setAccountNonLocked(true);
        customUserDetails.setCredentialsNonExpired(true);

		return customUserDetails;
	}
	
	public Collection<GrantedAuthority> getAuthorities(int userId) {
        List<Authority> authList = authorityMapper.selectAuthority(userId);
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Authority authority : authList) {
            authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        return authorities;
    }

}
