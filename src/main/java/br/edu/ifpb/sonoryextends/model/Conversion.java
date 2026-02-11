package br.edu.ifpb.sonoryextends.model;

import java.time.LocalDateTime;

public class Conversion {
    private int id;
    private int audioFileId;
    private String formatoOrigem;
    private String formatoDestino;
    private String caminhoConvertido;
    private LocalDateTime dataConversao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAudioFileId() {
        return audioFileId;
    }

    public void setAudioFileId(int audioFileId) {
        this.audioFileId = audioFileId;
    }

    public String getFormatoOrigem() {
        return formatoOrigem;
    }

    public void setFormatoOrigem(String formatoOrigem) {
        this.formatoOrigem = formatoOrigem;
    }

    public String getFormatoDestino() {
        return formatoDestino;
    }

    public void setFormatoDestino(String formatoDestino) {
        this.formatoDestino = formatoDestino;
    }

    public String getCaminhoConvertido() {
        return caminhoConvertido;
    }

    public void setCaminhoConvertido(String caminhoConvertido) {
        this.caminhoConvertido = caminhoConvertido;
    }

    public LocalDateTime getDataConversao() {
        return dataConversao;
    }

    public void setDataConversao(LocalDateTime dataConversao) {
        this.dataConversao = dataConversao;
    }
}
