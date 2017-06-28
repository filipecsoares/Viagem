package br.com.filipe.viagem.entity;

/**
 * Created by filipe on 27/06/17.
 */

public class Viagem {
    private Integer id;
    private String destino;
    private Integer tipoViagem;
    private String dataSaida;
    private String dataChegada;
    private Double orcamento;
    private Integer qtdPessoa;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Integer getTipoViagem() {
        return tipoViagem;
    }

    public void setTipoViagem(Integer tipoViagem) {
        this.tipoViagem = tipoViagem;
    }

    public String getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(String dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(String dataChegada) {
        this.dataChegada = dataChegada;
    }

    public Double getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Double orcamento) {
        this.orcamento = orcamento;
    }

    public Integer getQtdPessoa() {
        return qtdPessoa;
    }

    public void setQtdPessoa(Integer qtdPessoa) {
        this.qtdPessoa = qtdPessoa;
    }
}