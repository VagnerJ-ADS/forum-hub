package forum.hub.api.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosRegistroTopico(

        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        @NotBlank
        String autor,
        @NotBlank
        String nomeCurso,
        @NotNull
        LocalDateTime dataCriacao,
        EstadoTopico estadoTopico) {

    public DadosRegistroTopico {

        if (dataCriacao == null) {
            dataCriacao = LocalDateTime.now();
        }

        if (estadoTopico == null) {
            estadoTopico = estadoTopico.ABERTO;
        }
    }


}
