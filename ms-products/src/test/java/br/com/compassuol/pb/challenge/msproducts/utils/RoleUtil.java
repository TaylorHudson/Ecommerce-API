package br.com.compassuol.pb.challenge.msproducts.utils;

import br.com.compassuol.pb.challenge.msproducts.domain.dto.request.RoleRequest;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.response.RoleResponse;
import br.com.compassuol.pb.challenge.msproducts.domain.model.Role;

import java.util.HashSet;
import java.util.Set;

public class RoleUtil {

    public static Set<Role> rolesDefault() {
        var roles = new HashSet<Role>();
        roles.add(roleDefault());
        return roles;
    }

    public static Role roleDefault() {
        return Role.builder()
                .id(1L)
                .name("ROLE_ADMIN")
                .build();
    }

    public static RoleRequest roleRequestDefault() {
        return RoleRequest.builder()
                .name("ROLE_ADMIN")
                .build();
    }

    public static RoleResponse roleResponseDefault() {
        return RoleResponse.builder()
                .id(1L)
                .name("ROLE_ADMIN")
                .build();
    }

}
