package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

public class ValidadorMedicoComOutraConsultaNoMesmoHorario {

    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {

        var possuiConsulta = repository.existsByMedicoIdAndData(dados.idMedico(), dados.data());

        if (possuiConsulta) {
            throw new ValidacaoException("Médico já possui outra consulta agendada nesse mesmo horário.");
        }
    }
}
