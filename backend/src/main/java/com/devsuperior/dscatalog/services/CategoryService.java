package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.request.CategoryRequestDTO;
import com.devsuperior.dscatalog.dto.response.CategoryResponseDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.mappers.todto.CategoryMapperToDTO;
import com.devsuperior.dscatalog.mappers.tomodel.CategoryMapperToModel;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAll() {
        return repository.findAll().stream().map(CategoryMapperToDTO::converter).toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponseDTO findById(Long id) {
        Category entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        return CategoryMapperToDTO.converter(entity);
    }

    @Transactional
    public CategoryResponseDTO save(CategoryRequestDTO payload) {
        Category entity = CategoryMapperToModel.converter(payload);
        repository.save(entity);
        return CategoryMapperToDTO.converter(entity);
    }

    @Transactional
    public CategoryResponseDTO update(Long id, CategoryRequestDTO payload) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria de id " + id + "não encontrada");
        }

        try {
            Category entity = repository.getReferenceById(id);
            CategoryMapperToModel.updateFromDto(entity, payload);
            Category savedEntity = repository.save(entity);
            return CategoryMapperToDTO.converter(savedEntity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Categoria de id " + id + "não encontrada");
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
}
