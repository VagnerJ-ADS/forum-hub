package forum.hub.api.controller;


import forum.hub.api.domain.topico.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DadosRegistroTopico dados, UriComponentsBuilder uriBuilder) {

        if (repository.existsByTitulo(dados.titulo())) {
            throw new IllegalArgumentException("Já existe um tópico com o mesmo título.");
        }
        if (repository.existsByMensagem(dados.mensagem())) {
            throw new IllegalArgumentException("Já existe um tópico com a mesma mensagem.");
        }


        var topico = new Topico(dados);
        repository.save(topico);


        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }


    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listar(@PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemTopico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarTopico(@PathVariable Long id) {
        var topico = repository.findByIdAndAtivoTrue(id);
        if (topico.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensagem", "Tópico com ID " + id + " não foi encontrado ou está inativo."));
        }
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico.get()));
    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody DadosAtualizacaoTopico dados) {

        Optional<Topico> topicoOptional = repository.findById(id);
        if (topicoOptional.isEmpty()) {
            throw new EntityNotFoundException("Tópico não encontrado.");
        }

        var topico = topicoOptional.get();


        if (dados.titulo() != null && !dados.titulo().equals(topico.getTitulo()) &&
                repository.existsByTitulo(dados.titulo())) {
            throw new IllegalArgumentException("Já existe um tópico com o mesmo título.");
        }


        if (dados.mensagem() != null && !dados.mensagem().equals(topico.getMensagem()) &&
                repository.existsByMensagem(dados.mensagem())) {
            throw new IllegalArgumentException("Já existe um tópico com a mesma mensagem.");
        }


        topico.atualizarInformacoes(dados);


        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            var topico = repository.getReferenceById(id);

            if (!topico.getAtivo()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("mensagem", "Tópico com ID " + id + " já está excluído."));
            }

            topico.excluir();
            return ResponseEntity.noContent().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensagem", "Tópico com ID " + id + " não foi encontrado."));
        }
    }


}
