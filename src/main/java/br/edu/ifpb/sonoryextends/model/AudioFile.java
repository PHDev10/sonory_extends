package br.edu.ifpb.sonoryextends.model;

public class AudioFile {
    private int id;
    private String nome;
    private String caminhoOriginal;
    private String formato;
    private long tamanho;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCaminhoOriginal() {
        return caminhoOriginal;
    }

    public void setCaminhoOriginal(String caminhoOriginal) {
        this.caminhoOriginal = caminhoOriginal;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public long getTamanho() {
        return tamanho;
    }

    public void setTamanho(long tamanho) {
        this.tamanho = tamanho;
    }
}
