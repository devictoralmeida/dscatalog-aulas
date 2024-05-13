package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.mappers.todto.CategoryMapperToDTO;
import com.devsuperior.dscatalog.mappers.tomodel.CategoryMapperToModel;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(PageRequest page) {
        return repository.findAll(page).map(category -> CategoryMapperToDTO.converter(category, true));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Category entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        return CategoryMapperToDTO.converter(entity, true);
    }

    @Transactional
    public CategoryDTO save(CategoryDTO payload) {
        Category entity = CategoryMapperToModel.converter(payload);
        repository.save(entity);
        return CategoryMapperToDTO.converter(entity, true);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO payload) {
        try {
            Category entity = repository.getReferenceById(id);
            CategoryMapperToModel.updateFromDto(entity, payload);
            Category savedEntity = repository.save(entity);
            return CategoryMapperToDTO.converter(savedEntity, true);
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
