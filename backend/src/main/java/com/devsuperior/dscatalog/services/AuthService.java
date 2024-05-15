package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.entities.PasswordRecover;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repositories.PasswordRecoverRepository;
import com.devsuperior.dscatalog.repositories.UserRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordRecoverRepository passwordRecoverRepository;

    @Value("${email.password-recover.token.minutes}")
    private Long tokenMinutes;

    @Value("${email.password-recover.uri}")
    private String baseUrl;

    @Transactional
    public void createRecoverToken(String email) {
        // Verificar se o email existe
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("Email não encontrado");
        }

        // Gerar token com validade e salvá-lo no banco
        String token = UUID.randomUUID().toString();

        PasswordRecover passwordRecover = new PasswordRecover();
        passwordRecover.setEmail(email);
        passwordRecover.setToken(token);
        passwordRecover.setExpiration(Instant.now().plusSeconds(60 * tokenMinutes));
        passwordRecoverRepository.save(passwordRecover);

        // Enviar email com um link para usar o token
        String body = "Acesse o link para definir uma nova senha \n\n" + baseUrl + token + "\n\n" +
                      "O link expira em " + tokenMinutes + " minutos.";

        emailService.sendEmail(email, "Recuperação de senha", body);
    }
}
