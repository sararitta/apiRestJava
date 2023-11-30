package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

public record DadosListagemConsulta(Long id, String nomeMedico, String nomePaciente, LocalDateTime data) {

    public DadosListagemConsulta(Consulta consulta){
        this(consulta.getId(), consulta.getMedico().getNome(), consulta.getPaciente().getNome(), consulta.getData());

    }
}
