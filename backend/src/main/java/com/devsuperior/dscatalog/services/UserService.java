package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.request.UserRequestDTO;
import com.devsuperior.dscatalog.dto.request.UserRequestInsertDTO;
import com.devsuperior.dscatalog.dto.request.UserRequestUpdateDTO;
import com.devsuperior.dscatalog.dto.response.UserResponseDTO;
import com.devsuperior.dscatalog.dto.response.UserResponseInsertDTO;
import com.devsuperior.dscatalog.entities.Role;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.mappers.todto.UserMapperToDTO;
import com.devsuperior.dscatalog.mappers.tomodel.UserMapperToModel;
import com.devsuperior.dscatalog.repositories.RoleRepository;
import com.devsuperior.dscatalog.repositories.UserRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<UserResponseDTO> findAllPaged(Pageable page) {
        return repository.findAll(page).map(UserMapperToDTO::converter);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {
        User entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        return UserMapperToDTO.converter(entity);
    }

    @Transactional
    public UserResponseInsertDTO save(UserRequestInsertDTO payload) {
        Set<Role> roles = getRolesList(payload);
        User entity = UserMapperToModel.converterInsert(payload, roles);
        entity.setPassword(passwordEncoder.encode(payload.getPassword()));
        repository.save(entity);
        return UserMapperToDTO.converterInsert(entity);
    }


    @Transactional
    public UserResponseDTO update(Long id, UserRequestUpdateDTO payload) {
        try {
            User entity = repository.getReferenceById(id);
            Set<Role> roles = getRolesList(payload);
            UserMapperToModel.updateFromDto(entity, payload, roles);
            User savedEntity = repository.save(entity);
            return UserMapperToDTO.converter(savedEntity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Usuário de id " + id + "não encontrado");
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

    private Set<Role> getRolesList(UserRequestDTO payload) {
        Set<Role> roles = new HashSet<>();
        payload.getRoles().forEach(role -> {
            Role roleEntity = roleRepository.findById(role.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Role não encontrada"));
            roles.add(roleEntity);
        });
        return roles;
    }

}
