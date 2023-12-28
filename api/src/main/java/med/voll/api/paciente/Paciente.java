package med.voll.api.paciente;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.endereco.Endereco;
import org.springframework.web.bind.annotation.RequestBody;

@Table(name="Pacientes")
@Entity(name="Paciente")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private boolean ativo;

    @Embedded
    private Endereco endereco;

    public Paciente(@RequestBody @Valid DadosCadastroPaciente dados){
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarPaciente(DadosAtualizacaoPaciente dados) {
        if(dados.nome() != null){
            this.nome = dados.nome();
        }
        if (dados.telefone() !=  null){
            this.telefone = dados.telefone();
        }
        if (dados.endereco() != null){
            this.endereco.atualizarEndereco(dados.endereco());
        }
    }

    public void inativarPaciente() {
        this.ativo = false;
    }
}
