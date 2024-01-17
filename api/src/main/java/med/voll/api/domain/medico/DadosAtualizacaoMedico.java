package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(
        @NotNull//somente o id é obrigatorio
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
