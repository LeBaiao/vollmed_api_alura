package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(
        @NotBlank //somente para String
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}")//vão de 4 a 6 dígitos
        String crm,
        @NotNull
        Especialidade especialidade,
        @NotNull
        @Valid //além de validar esse dto, vai validar o dto de endereco
        DadosEndereco endereco) {
}
