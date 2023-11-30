package med.voll.api.domain.consulta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    boolean existsByMedicoIdAndData(Long medicoId, LocalDateTime data);

    boolean existsByPacienteIdAndDataBetween(Long pacienteId, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);

    Page<Consulta> findAllByAtivaTrue(Pageable paginacao);
}
