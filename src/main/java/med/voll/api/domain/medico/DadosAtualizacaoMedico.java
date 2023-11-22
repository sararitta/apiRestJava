package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(
        @NotNull
        Long id,
        String nome,
        @Pattern(regexp = "\\d{11}")
        String telefone,
        DadosEndereco endereco) {
}
