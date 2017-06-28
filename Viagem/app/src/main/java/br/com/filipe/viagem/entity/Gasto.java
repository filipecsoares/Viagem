package br.com.filipe.viagem.entity;

/**
 * Created by filipe on 27/06/17.
 */

public class Gasto {
    private Integer id;
    private String categoria;
    private Double valor;
    private String data;
    private String descricao;
    private String local;
    private Integer fkViagem;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Integer getFkViagem() {
        return fkViagem;
    }

    public void setFkViagem(Integer fkViagem) {
        this.fkViagem = fkViagem;
    }
}