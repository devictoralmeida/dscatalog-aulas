package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.ProductDTO;
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
import org.springframework.data.domain.PageRequest;
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
    public Page<ProductDTO> findAll(PageRequest page) {
        return repository.findAll(page).map(products -> ProductMapperToDTO.converter(products, true));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        return ProductMapperToDTO.converter(entity, true);
    }

    @Transactional
    public ProductDTO save(ProductDTO payload) {
        Product entity = ProductMapperToModel.converter(payload);
        Set<Category> listaCategorias = getCategoriesList(payload);
        entity.setCategories(listaCategorias);
        repository.save(entity);
        return ProductMapperToDTO.converter(entity, true);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO payload) {
        try {
            Product entity = repository.getReferenceById(id);
            ProductMapperToModel.updateFromDto(entity, payload);
            entity.getCategories().clear();

            Set<Category> listaCategorias = getCategoriesList(payload);
            entity.setCategories(listaCategorias);

            Product savedEntity = repository.save(entity);
            return ProductMapperToDTO.converter(savedEntity, true);
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


    private Set<Category> getCategoriesList(ProductDTO payload) {
        Set<Category> listaCategorias = new HashSet<>();

        payload.getCategories().forEach(category -> {
            Category cat = categoryRepository.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
            listaCategorias.add(cat);
        });

        return listaCategorias;
    }
}
