package com.devsuperior.dscatalog.services.validation;

import com.devsuperior.dscatalog.dto.request.UserRequestInsertDTO;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repositories.UserRepository;
import com.devsuperior.dscatalog.resources.exceptions.FieldMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// Implementa a interface ConstraintValidator, que recebe a anotação customizada e o tipo do DTO a ser validado
@RequiredArgsConstructor
public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserRequestInsertDTO> {
    private final UserRepository repository;

    @Override
    public void initialize(UserInsertValid ann) {
    }

    // Método para testar se o DTO é válido ou não
    @Override
    public boolean isValid(UserRequestInsertDTO dto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();
        // Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à lista

        // Testa se o email já existe no banco de dados
        User user = repository.findByEmail(dto.getEmail());
        if (user != null) {
            list.add(new FieldMessage("email", "Email já existe"));
        }


        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }
}
