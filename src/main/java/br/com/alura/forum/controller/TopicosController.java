package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    TopicoRepository topicoRepository;

    @Autowired
    CursoRepository cursoRepository;

    @GetMapping
    public List<TopicoDto> lista(/*@RequestParam("nomeCurso")*/ String nomeCurso) {

        if (nomeCurso == null ) {
            List<Topico> topicos = topicoRepository.findAll();
            return TopicoDto.converter(topicos);
        }

        List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
        return TopicoDto.converter(topicos);
    }


    @PostMapping
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm topico, UriComponentsBuilder uriBuilder) {
        Topico top = topico.converter(cursoRepository);

        Topico novoTopico = topicoRepository.save(top);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(novoTopico.getId()).toUri();

        return ResponseEntity.created(uri).body(new TopicoDto(novoTopico));
    }

    @GetMapping("/{id}")
    public DetalhesDoTopicoDto detalhar(@PathVariable Long id) {
        Topico topico = topicoRepository.getOne(id);

        return new DetalhesDoTopicoDto(topico);
    }
}
