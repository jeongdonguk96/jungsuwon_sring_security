package io.security.springsecurity.service;

import io.security.springsecurity.domain.Role;

import java.util.List;

public interface RoleService {

    Role getRole(long id);

    List<Role> getRoles();

    void createRole(Role role);
}