package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.dto.request.ProductRequestDTO;
import com.devsuperior.dscatalog.dto.response.ProductResponseDTO;
import com.devsuperior.dscatalog.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {
    private final ProductService service;

    public ProductResource(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> findAllPaged(
            @PageableDefault(size = 5, page = 0, sort = {"name"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ProductResponseDTO> result = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> save(@Valid @RequestBody ProductRequestDTO payload) {
        ProductResponseDTO response = service.save(payload);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO payload) {
        return ResponseEntity.ok().body(service.update(id, payload));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
