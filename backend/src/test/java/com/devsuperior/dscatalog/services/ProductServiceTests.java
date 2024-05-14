package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.response.ProductResponseDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.factory.Factory;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ProductServiceTests {
    // Nos testes unitários, vamos precisar mockar as dependências desse serviço com o mockito

    @InjectMocks
    private ProductService service;

    // Com o @Mock, nós devemos simular o comportamento do repository
    @Mock
    private ProductRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private Product product;
    private Category category;
    private PageImpl<Product> page;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 3L;
        dependentId = 2L;
        product = Factory.createProduct();
        category = Factory.createCategory();
        page = new PageImpl<>(List.of(product));

        // Como o método deleteById retorna void, devemos fazer com o doNothing
        doNothing().when(repository).deleteById(existingId);

        // Quando o método deleteById for chamado com o id dependente, lançamos a exceção DataIntegrityViolationException
        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

        when(repository.existsById(dependentId)).thenReturn(true);
        when(repository.existsById(existingId)).thenReturn(true);
        when(repository.existsById(nonExistingId)).thenReturn(false);

        // Mock Simulação do findAll
        when(repository.findAll((Pageable) any())).thenReturn(page);

        // Mock Simulação do save e update
        when(repository.save(any())).thenReturn(product);
        when(repository.getReferenceById(existingId)).thenReturn(product);
        when(categoryRepository.findAllById(any())).thenReturn(List.of(category));
        doThrow(EntityNotFoundException.class).when(repository).getReferenceById(nonExistingId);

        // Mock Simulação do findById
        when(repository.findById(existingId)).thenReturn(Optional.of(product)); // Optional preenchido
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty()); // Optional vazio
    }

    @Test
    void delete_should_do_nothing_when_id_exists() {
        assertDoesNotThrow(() -> service.delete(existingId));

        // Verifica se o mock do repository chamou 1x o método deleteById com o id correto
        verify(repository, times(1)).deleteById(existingId);
    }

    @Test
    void delete_should_throw_ResourceNotFoundException_when_id_does_not_exist() {
        assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
    }

    @Test
    void delete_should_throw_DatabaseException_when_id_is_dependent() {
        assertThrows(DatabaseException.class, () -> {
            service.delete(dependentId);
        });
    }

    @Test
    void find_all_paged_should_return_page() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductResponseDTO> result = service.findAllPaged(pageable);

        // Verifica se o resultado não é nulo e se o método do depository foi chamado 1x
        assertNotNull(result);
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    void find_by_id_should_return_productDTO_when_id_exists() {
        ProductResponseDTO result = service.findById(existingId);

        // Verifica se o resultado não é nulo e se o método do depository foi chamado 1x
        assertNotNull(result);
        verify(repository, times(1)).findById(existingId);
    }

    @Test
    void find_by_id_should_throw_ResourceNotFoundException_when_id_does_not_exist() {
        assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    void update_should_return_productDTO_when_id_exists() {
        ProductResponseDTO result = service.update(existingId, Factory.createProductRequestDTO());

        // Verifica se o resultado não é nulo e se o método do depository foi chamado 1x
        assertNotNull(result);
        verify(repository, times(1)).getReferenceById(existingId);
        verify(repository, times(1)).save(any());
    }

    @Test
    void update_should_throw_ResourceNotFoundException_when_id_does_not_exist() {
        assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, Factory.createProductRequestDTO());
        });
    }
}
