package br.edu.ifpb.sonoryextends.model;

import java.time.LocalDateTime;

public class ConversionHistory {
    private Long id;
    private String nomeOriginal;
    private String formatoOriginal;
    private String formatoDeConversao;
    private String pacoteDeSaida;
    private LocalDateTime dataConversao;
    private String status;

    public ConversionHistory(Long id, String nomeOriginal, String formatoOriginal, String formatoDeConversao, String pacoteDeSaida, LocalDateTime dataConversao, String status) {
        this.id = id;
        this.nomeOriginal = nomeOriginal;
        this.formatoOriginal = formatoOriginal;
        this.formatoDeConversao = formatoDeConversao;
        this.pacoteDeSaida = pacoteDeSaida;
        this.dataConversao = dataConversao;
        this.status = status;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeOriginal() {
        return nomeOriginal;
    }

    public void setNomeOriginal(String nomeOriginal) {
        this.nomeOriginal = nomeOriginal;
    }

    public String getFormatoOriginal() {
        return formatoOriginal;
    }

    public void setFormatoOriginal(String formatoOriginal) {
        this.formatoOriginal = formatoOriginal;
    }

    public String getFormatoConversao() {
        return formatoDeConversao;
    }

    public void setFormatoConversao(String formatoConversao) {
        this.formatoDeConversao = formatoConversao;
    }

    public String getPacoteDeSaida() {
        return pacoteDeSaida;
    }

    public void setPacoteDeSaida(String pacoteDeSaida) {
        this.pacoteDeSaida = pacoteDeSaida;
    }

    public LocalDateTime getDataConversao() {
        return dataConversao;
    }

    public void setDataConversao(LocalDateTime dataConversao) {
        this.dataConversao = dataConversao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
