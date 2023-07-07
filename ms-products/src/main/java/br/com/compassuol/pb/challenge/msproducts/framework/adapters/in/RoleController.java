package br.com.compassuol.pb.challenge.msproducts.framework.adapters.in;

import br.com.compassuol.pb.challenge.msproducts.application.service.RoleService;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.request.RoleRequest;
import br.com.compassuol.pb.challenge.msproducts.domain.dto.response.RoleResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<RoleResponse> create(@Valid @RequestBody RoleRequest request) {
        var response = roleService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<RoleResponse>> findAll() {
        var response = roleService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> update(@PathVariable Long id, @Valid @RequestBody RoleRequest request) {
        var response = roleService.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
