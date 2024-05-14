package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.request.ProductRequestDTO;
import com.devsuperior.dscatalog.dto.response.ProductResponseDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.mappers.todto.ProductMapperToDTO;
import com.devsuperior.dscatalog.mappers.tomodel.ProductMapperToModel;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findAllPaged(Pageable page) {
        return repository.findAll(page).map(ProductMapperToDTO::converter);
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findById(Long id) {
        Product entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        return ProductMapperToDTO.converter(entity);
    }

    @Transactional
    public ProductResponseDTO save(ProductRequestDTO payload) {
        Set<Category> listaCategorias = getCategoriesList(payload);
        Product entity = ProductMapperToModel.converter(payload, listaCategorias);
        repository.save(entity);
        return ProductMapperToDTO.converter(entity);
    }

    @Transactional
    public ProductResponseDTO update(Long id, ProductRequestDTO payload) {
        try {
            Product entity = repository.getReferenceById(id);
            Set<Category> listaCategorias = getCategoriesList(payload);
            ProductMapperToModel.updateFromDto(entity, payload, listaCategorias);
            Product savedEntity = repository.save(entity);
            return ProductMapperToDTO.converter(savedEntity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Produto de id " + id + "não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }


    private Set<Category> getCategoriesList(ProductRequestDTO payload) {
        Set<Category> categories = new HashSet<>();
        payload.getCategories().forEach(categoryReq -> {
            Category cat = categoryRepository.findById(categoryReq.getId()).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
            categories.add(cat);
        });

        return categories;
    }

}
