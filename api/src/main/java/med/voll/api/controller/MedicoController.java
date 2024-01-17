package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired//injeção de dependências
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastraMedico(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder ){
        var medico = new Medico(dados);
        repository.save(medico);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();//classe que encapsula o endereço da API

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
        //no corpo da resposta do cod 201 deve ter um cabeçalho http(endereço da aplicação) e os dados do novo registro
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listarMedicos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){ //carrega 10 registros e ordena pelo nome
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new); //converte uma lista de medicos p/ lista de dto
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarMedicos(@RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarMedico(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirMedico(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.inativarMedico();
        return ResponseEntity.noContent().build(); //sem conteúdo na resposta
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }



}
