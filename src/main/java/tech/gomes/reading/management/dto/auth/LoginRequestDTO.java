package tech.gomes.reading.management.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record LoginRequestDTO(@NotNull
                              String identifier,
                              @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$",
                                      message = "A senha deve ter entre 8 e 15 caracteres, contendo pelo menos uma letra maiúscula, uma minúscula, um número e um caractere especial.")
                              String password) {
}
