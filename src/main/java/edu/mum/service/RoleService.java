package edu.mum.service;

import edu.mum.domain.Role;

import java.util.List;

public interface RoleService {
    public Role saveRole(Role role);

    public List<Role> getRoles();

    public Role getRoleById(Long id);
}
