/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gishoauth.spring.services.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.gishoauth.model.entity.GishoUser;
import org.gishoauth.model.entity.UserRole;
import org.gishoauth.spring.repositories.GishoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 *
 * @author ZO-PC
 */
@Component
public class GishoUserServiceDetails implements UserDetailsService {

    private final GishoUserRepository userRepository;

	@Autowired
	public GishoUserServiceDetails(GishoUserRepository userRepository) {
		this.userRepository = userRepository;
	}
        
        @Override
        public UserDetails loadUserByUsername(String login) {
            GishoUser u = this.userRepository.findByLogin(login);

            if (u == null) {
                throw new UsernameNotFoundException("UserName " + login + " not found");
            }
            Set<UserRole> roles = u.getUserRoleList();
            List<GrantedAuthority> authorities = new ArrayList();
            roles.forEach(ur -> {
              
                authorities.add(new SimpleGrantedAuthority(ur.getLibelleRole()));
            });
            return new User(u.getLogin(), u.getPassword(),
                    true, true, true, true, authorities);
        }

}
