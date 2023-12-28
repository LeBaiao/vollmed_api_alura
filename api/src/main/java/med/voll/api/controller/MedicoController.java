package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.endereco.Endereco;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired//injueção de dependências
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastraMedico(@RequestBody @Valid DadosCadastroMedico dados){ //record para o DTO
        repository.save(new Medico(dados));
    }

    @GetMapping
    public Page<DadosListagemMedico> listarMedicos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){ //carrega 10 registros e ordena pelo nome
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new); //converte uma lista de medicos p/ lista de dto
    }

    @PutMapping
    @Transactional
    public void atualizarMedicos(@RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarMedico(dados);
    }

    @DeleteMapping("/{id}")
    public void excluirMedico(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.inativarMedico();
    }



}
