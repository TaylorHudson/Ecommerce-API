package br.com.compassuol.pb.challenge.msproducts.application.service;

import br.com.compassuol.pb.challenge.msproducts.domain.dto.request.EmailRequest;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.request.UserRequest;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.response.UserResponse;
import br.com.compassuol.pb.challenge.msproducts.domain.model.Role;
import br.com.compassuol.pb.challenge.msproducts.domain.model.UserModel;
import br.com.compassuol.pb.challenge.msproducts.framework.adapters.out.RoleRepository;
import br.com.compassuol.pb.challenge.msproducts.framework.adapters.out.UserRepository;
import br.com.compassuol.pb.challenge.msproducts.framework.exception.EmailAlreadyExistsException;
import br.com.compassuol.pb.challenge.msproducts.framework.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final NotificationServiceFeign notificationServiceFeign;

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

    @Transactional
    public UserResponse update(Long id, UserRequest request) {
        String email = request.getEmail();

        var oldUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));

        if (!Objects.equals(email, oldUser.getEmail()) & userRepository.existsByEmail(email)) throw new EmailAlreadyExistsException(request.getEmail());

        var user = UserModel.builder().request(request).build();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(toRoles(request.getRoles()));
        user.setId(id);
        var updated = userRepository.save(user);

        notificationServiceFeign.create(EmailRequest.builder().toEmail(updated.getEmail()).build());

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
