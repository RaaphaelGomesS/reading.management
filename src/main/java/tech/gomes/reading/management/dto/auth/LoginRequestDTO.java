package tech.gomes.reading.management.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record LoginRequestDTO(String username,

                              @Email(message = "O email deve ser válido.")
                              String email,

                              @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$",
                                      message = "A senha deve ter entre 8 e 15 caracteres, contendo pelo menos uma letra maiúscula, uma minúscula, um número e um caractere especial.")
                              String password) {
}
