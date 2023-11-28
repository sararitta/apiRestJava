package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

import java.time.Duration;
import java.time.LocalDateTime;

public class ValidadorHorarioAntecedencia {

    public void validar(DadosAgendamentoConsulta dados) {

        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferenca = Duration.between(agora, dataConsulta).toMinutes();

        if (diferenca < 30) {
            throw new ValidacaoException("Consulta não pode ser marcada com menos de 30 minutos de antecedência.");
        }
    }
}
