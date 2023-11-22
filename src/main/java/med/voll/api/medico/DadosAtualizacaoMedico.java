package med.voll.api.medico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(
        @NotNull
        Long id,
        String nome,
        @Pattern(regexp = "\\d{11}")
        String telefone,
        DadosEndereco endereco) {
}
