package forum.hub.api.domain.topico;

import java.time.LocalDateTime;

public record DadosListagemTopico(Long id, String titulo, String mensagem, String autor, String curso,
                                  LocalDateTime dataCriacao, EstadoTopico estadoTopico) {

    public DadosListagemTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getAutor(), topico.getCurso(), topico.getDataCriacao(), topico.getEstadoTopico());
    }
}
