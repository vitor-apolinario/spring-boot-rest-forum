package br.com.alura.forum.controller.dto;

import br.com.alura.forum.model.Resposta;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RespostaDto {
    private Long id;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String nomeAutor;

    public RespostaDto() {
    }

    public RespostaDto(Resposta re) {
        this.mensagem = re.getMensagem();
        this.dataCriacao = re.getDataCriacao();
        this.nomeAutor = re.getAutor().getNome();
    }

    public Long getId() {
        return id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }
}
