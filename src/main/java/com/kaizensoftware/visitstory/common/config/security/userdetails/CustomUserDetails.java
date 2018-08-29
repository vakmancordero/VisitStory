package com.kaizensoftware.visitstory.common.config.security.userdetails;

import com.kaizensoftware.visitstory.app.persistence.model.auth.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    
	private static final long serialVersionUID = 1L;
	
	private String username;
    private String password;
    
    private Collection<? extends GrantedAuthority> authorities;
    
    public CustomUserDetails(User user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        
        this.authorities = this.getAuthorities(user.getRoles());
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(this.getPermission(roles));
    }
    
    private List<String> getPermission(Collection<Role> roles) {
        
        List<String> permissionsList = new ArrayList<>();
        List<Permission> permissions = new ArrayList<>();
        
        roles.forEach(role -> permissions.addAll(role.getPermissions()));
        
        permissions.forEach(permission -> permissionsList.add("ROLE_".concat(permission.getName())));
        
        return permissionsList;
    }
    
    private List<GrantedAuthority> getGrantedAuthorities(List<String> permissions) {
        
        List<GrantedAuthority> authorities = new ArrayList<>();

        permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));

        return authorities;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public String getUsername() {
        return username;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
