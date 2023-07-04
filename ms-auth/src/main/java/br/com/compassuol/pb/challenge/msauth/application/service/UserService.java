package br.com.compassuol.pb.challenge.msauth.application.service;

import br.com.compassuol.pb.challenge.msauth.domain.dto.request.UserRequest;
import br.com.compassuol.pb.challenge.msauth.domain.dto.response.UserResponse;
import br.com.compassuol.pb.challenge.msauth.domain.model.Role;
import br.com.compassuol.pb.challenge.msauth.domain.model.UserModel;
import br.com.compassuol.pb.challenge.msauth.framework.adapters.out.RoleRepository;
import br.com.compassuol.pb.challenge.msauth.framework.adapters.out.UserRepository;
import br.com.compassuol.pb.challenge.msauth.framework.exceptions.EmailAlreadyExistsException;
import br.com.compassuol.pb.challenge.msauth.framework.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserResponse create(UserRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        if (userRepository.existsByEmail(email)) throw new EmailAlreadyExistsException(email);

        var user = UserModel.builder().request(request).build();
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(toRoles(request.getRoles()));
        var saved = userRepository.save(user);

        return createUserResponse(saved);
    }

    public UserResponse findById(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));
        return createUserResponse(user);
    }

    public UserResponse update(Long id, UserRequest request) {
        var oldUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));

        var user = UserModel.builder().request(request).build();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(toRoles(request.getRoles()));
        user.setId(id);
        var updated = userRepository.save(user);

        return createUserResponse(updated);
    }

    private UserResponse createUserResponse(UserModel user) {
        return UserResponse.builder().user(user).build();
    }

    private Set<Role> toRoles(Set<Long> rolesId) {
        var roles = new HashSet<Role>();

        rolesId
                .forEach(id -> {
                    var role = roleRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id.toString()));
                    roles.add(role);
                });
        return roles;
    }

}
