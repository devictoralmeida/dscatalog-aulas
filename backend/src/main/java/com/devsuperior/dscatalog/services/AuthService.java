package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.request.NewPasswordRequestDTO;
import com.devsuperior.dscatalog.dto.response.UserResponseDTO;
import com.devsuperior.dscatalog.entities.PasswordRecover;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.mappers.todto.UserMapperToDTO;
import com.devsuperior.dscatalog.repositories.PasswordRecoverRepository;
import com.devsuperior.dscatalog.repositories.UserRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordRecoverRepository passwordRecoverRepository;
    private final PasswordEncoder passwordEncoder;

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

    @Transactional
    public void saveNewPassword(NewPasswordRequestDTO payload) {
        String token = payload.getToken();
        List<PasswordRecover> result = passwordRecoverRepository.searchValidTokens(token, Instant.now());

        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Token inválido");
        }

        // Vamos encontrar o usuário e atualizar a senha dele (encriptada)
        User user = userRepository.findByEmail(result.get(0).getEmail());
        user.setPassword(passwordEncoder.encode(payload.getPassword()));
    }

    protected UserResponseDTO authenticated() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
            String username = jwtPrincipal.getClaim("username");
            User user = userRepository.findByEmail(username);
            return UserMapperToDTO.converter(user);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Invalid user");
        }
    }
}
