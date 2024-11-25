package forum.hub.api.domain.topico;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(Long id, String titulo, String mensagem, String curso, EstadoTopico estadoTopico,
                                      String autor, LocalDateTime dataCriacao) {
    public DadosDetalhamentoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getCurso(), topico.getEstadoTopico(), topico.getAutor(), topico.getDataCriacao());
    }
}
