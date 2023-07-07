package br.com.compassuol.pb.challenge.msproducts.application.service;

import br.com.compassuol.pb.challenge.msproducts.domain.dto.response.RoleResponse;
import br.com.compassuol.pb.challenge.msproducts.domain.model.Role;
import br.com.compassuol.pb.challenge.msproducts.framework.adapters.out.RoleRepository;
import br.com.compassuol.pb.challenge.msproducts.framework.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static br.com.compassuol.pb.challenge.msproducts.utils.RoleUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    void createSuccess() {
        var role = roleDefault();
        var request = roleRequestDefault();
        var expectedResponse = roleResponseDefault();

        when(roleRepository.save(any(Role.class))).thenReturn(role);

        var response = roleService.create(request);

        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getName(), response.getName());

        verify(roleRepository).save(any(Role.class));
    }

    @Test
    void findAllSuccess() {
        var roles = rolesDefault().stream().toList();
        var expectedResponses = new ArrayList<RoleResponse>();
        expectedResponses.add(roleResponseDefault());

        when(roleRepository.findAll()).thenReturn(roles);

        var response = roleService.findAll();

        assertEquals(expectedResponses.get(0).getId(), response.get(0).getId());
        assertEquals(expectedResponses.get(0).getName(), response.get(0).getName());

        verify(roleRepository).findAll();
    }

    @Test
    void updateSuccess() {
        var role = roleDefault();
        var request = roleRequestDefault();
        var expectedResponse = roleResponseDefault();

        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        var response = roleService.update(1L, request);

        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getName(), response.getName());

        verify(roleRepository).findById(anyLong());
        verify(roleRepository).save(any(Role.class));
    }

    @Test
    void updateErrorResourceNotFoundException() {
        var role = roleDefault();
        var request = roleRequestDefault();

        when(roleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roleService.update(1L, request));

        verify(roleRepository).findById(anyLong());
        verify(roleRepository, times(0)).save(any(Role.class));
    }

}