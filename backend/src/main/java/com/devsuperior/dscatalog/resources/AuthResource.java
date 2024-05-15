package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.dto.request.EmailRequestDTO;
import com.devsuperior.dscatalog.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthResource {
    private final AuthService authService;

    @PostMapping(value = "/recover-token")
    public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody EmailRequestDTO payload) {
        authService.createRecoverToken(payload.getEmail());
        return ResponseEntity.noContent().build();
    }
}
