package br.com.compassuol.pb.challenge.msproducts.application.service;

import br.com.compassuol.pb.challenge.msproducts.domain.model.UserModel;
import br.com.compassuol.pb.challenge.msproducts.framework.adapters.out.RoleRepository;
import br.com.compassuol.pb.challenge.msproducts.framework.adapters.out.UserRepository;
import br.com.compassuol.pb.challenge.msproducts.framework.exception.EmailAlreadyExistsException;
import br.com.compassuol.pb.challenge.msproducts.framework.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static br.com.compassuol.pb.challenge.msproducts.utils.RoleUtil.roleDefault;
import static br.com.compassuol.pb.challenge.msproducts.utils.UserUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Spy
    private PasswordEncoder passwordEncoder;

    @Mock
    private PublisherEmailService publisherEmailService;

    @InjectMocks
    private UserService userService;

    @Test
    void createSuccess() {
        var user = userDefault();
        var role = roleDefault();
        var request = userRequestDefault();
        var expectedResponse = userResponseDefault();

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));
        when(userRepository.save(any(UserModel.class))).thenReturn(user);

        var response = userService.create(request);

        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getEmail(), response.getEmail());

        verify(userRepository).existsByEmail(anyString());
        verify(roleRepository).findById(anyLong());
        verify(userRepository).save(any(UserModel.class));
    }

    @Test
    void createErrorEmailAlreadyExistsException() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.create(userRequestDefault()));

        verify(userRepository).existsByEmail(anyString());
    }

    @Test
    void findByIdSuccess() {
        var user = userDefault();
        var expectedResponse = userResponseDefault();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        var response = userService.findById(anyLong());

        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getEmail(), response.getEmail());

        verify(userRepository).findById(anyLong());
    }

    @Test
    void findByIdErrorResourceNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,() -> userService.findById(anyLong()));

        verify(userRepository).findById(anyLong());
    }

    @Test
    void updateSuccess() {
        var user = userDefault();
        var role = roleDefault();
        var request = userRequestDefault();
        var expectedResponse = userResponseDefault();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));
        when(userRepository.save(any(UserModel.class))).thenReturn(user);

        var response = userService.update(1L, request);

        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getEmail(), response.getEmail());

        verify(userRepository).findById(anyLong());
        verify(userRepository).existsByEmail(anyString());
        verify(roleRepository).findById(anyLong());
        verify(userRepository).save(any(UserModel.class));
    }

    @Test
    void updateErrorResourceNotFoundException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,() -> userService.update(1L, userRequestDefault()));

        verify(userRepository).findById(anyLong());
    }

    @Test
    void updateErrorEmailAlreadyExistsException() {
        var user = userDefault();
        user.setEmail("john.do@email.com");
        var request = userRequestDefault();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class,() -> userService.update(1L, request));

        verify(userRepository).findById(anyLong());
        verify(userRepository).existsByEmail(anyString());
    }


}