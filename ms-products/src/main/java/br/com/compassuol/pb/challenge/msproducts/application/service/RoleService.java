package br.com.compassuol.pb.challenge.msproducts.application.service;

import br.com.compassuol.pb.challenge.msproducts.domain.dto.request.RoleRequest;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.response.RoleResponse;
import br.com.compassuol.pb.challenge.msproducts.domain.model.Role;
import br.com.compassuol.pb.challenge.msproducts.framework.adapters.out.RoleRepository;
import br.com.compassuol.pb.challenge.msproducts.framework.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleResponse create(RoleRequest request) {
        var role = createRoleModel(request);
        var saved = roleRepository.save(role);
        return createRoleResponse(saved);
    }

    public List<RoleResponse> findAll() {
        var roles = roleRepository.findAll();
        return roles.stream().map(this::createRoleResponse).collect(Collectors.toList());
    }

    public RoleResponse update(Long id, RoleRequest request) {
        roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id.toString()));

        var role = createRoleModel(request);
        role.setId(id);
        var updated = roleRepository.save(role);

        return createRoleResponse(updated);
    }

    private Role createRoleModel(RoleRequest request) {
        return Role.builder().name(request.getName()).build();
    }

    private RoleResponse createRoleResponse(Role role) {
        return RoleResponse.builder().id(role.getId()).name(role.getName()).build();
    }

}
