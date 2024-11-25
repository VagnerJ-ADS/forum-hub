package forum.hub.api.domain.topico;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoTopico(

        Long id,
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        String autor,
        String curso) {
}
