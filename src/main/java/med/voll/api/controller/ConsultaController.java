package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import med.voll.api.domain.medico.DadosListagemMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @Autowired
    private ConsultaRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){

        var dto = agenda.agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @PutMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados){

        agenda.cancelar(dados);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemConsulta>> listar(@PageableDefault(size = 10, sort = {"data"}) Pageable paginacao) {

        Page<DadosListagemConsulta> page = repository.findAllByAtivaTrue(paginacao).map(DadosListagemConsulta::new);
        return ResponseEntity.ok(page);
    }
}
