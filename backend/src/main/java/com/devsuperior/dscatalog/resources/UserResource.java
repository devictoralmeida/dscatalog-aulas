package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.dto.request.UserRequestDTO;
import com.devsuperior.dscatalog.dto.request.UserRequestInsertDTO;
import com.devsuperior.dscatalog.dto.response.UserResponseDTO;
import com.devsuperior.dscatalog.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
    private final UserService service;

    public UserResource(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> findAllPaged(
            @PageableDefault(size = 5, page = 0, sort = {"firstName"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<UserResponseDTO> result = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> save(@RequestBody UserRequestInsertDTO payload) {
        UserResponseDTO response = service.save(payload);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @RequestBody UserRequestDTO payload) {
        return ResponseEntity.ok().body(service.update(id, payload));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
