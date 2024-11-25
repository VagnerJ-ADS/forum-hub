package forum.hub.api.domain.topico;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String autor;
    private String curso;

    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    private EstadoTopico estadoTopico;


    public Topico(DadosRegistroTopico dados) {
        this.ativo = true;
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.dataCriacao = dados.dataCriacao();
        this.autor = dados.autor();
        this.curso = dados.nomeCurso();
        this.estadoTopico = dados.estadoTopico();
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoTopico dados) {
        if (dados.titulo() != null) {
            this.titulo = dados.titulo();
        }
        if (dados.mensagem() != null) {
            this.mensagem = dados.mensagem();
        }
        if (dados.curso() != null) {
            this.curso = dados.curso();
        }
        if (dados.autor() != null) {
            this.autor = dados.autor();
        }


    }

    public void excluir() {
        this.ativo = false;
    }
}
