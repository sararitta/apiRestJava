package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {

        if(!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe.");
        }
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe.");
        }

        validadores.forEach(v -> v.validar(dados));

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);

        if (medico == null) {
            throw new ValidacaoException("Não existe médico disponível nesta data/horário.");
        }
        var consulta = new Consulta(null, medico, paciente, dados.data(), true, null);


        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {

        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido.");
        }

        var medico = medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
        if (medico == null) {

            throw new ValidacaoException("Nenhum médico disponível nesta data.");
        }

        return medico;
    }


    public void cancelar(DadosCancelamentoConsulta dados) {
        if (dados.motivo() == null) {
            throw new ValidacaoException("Motivo deve ser preenchido.");
        }
        if (dados.idConsulta() == null) {
            throw new ValidacaoException("Id da consulta deve ser informado.");
        }

        Consulta consulta = consultaRepository.getReferenceById(dados.idConsulta());

        if (consulta.getData().minusHours(24).isAfter(LocalDateTime.now())) {
            consulta.cancelar(dados);

        } else {
            throw new ValidacaoException("Somente é permitido cancelar uma consulta com até 24h de antecedência.");
        }
    }
}
