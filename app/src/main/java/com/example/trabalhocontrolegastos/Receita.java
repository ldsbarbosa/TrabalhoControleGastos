/**
 * RECEITAS: são salários recebidos ou qualquer outra fonte de renda
 *
 * Categoria da Receita (Fonte de Renda): Ex: Trabalho fixo, Trabalho variável(Freelancer), Renda passiva
 *
 * Descrição:  ex: Construção de site, trabalho no banco, consultoria para a empresa XPTO demandando maior
 * segurança nos sistemas, divindendos de uma empresa de tecnologia, etc.
 *
 * Valor
 *
 * Data
 *
 * Forma de recebimento: Dinheiro, Cartão de Débito, Cartão de Crédito, PIX
 */
package com.example.trabalhocontrolegastos;

import java.io.Serializable;

public class Receita implements Serializable {
    private Integer codigo;
    private String nome;
    private String categoria;
    private String descricao;
    private Double valor;
    private String data;
    private String formaDeRecebimento;

    public Receita(Integer codigo, String nome, String categoria, String descricao, Double valor, String data, String formaDeRecebimento) {
        this.codigo = codigo;
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.formaDeRecebimento = formaDeRecebimento;
    }

    public Receita() { }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public String getFormaDeRecebimento() {
        return formaDeRecebimento;
    }

    public void setFormaDeRecebimento(String formaDeRecebimento) {
        this.formaDeRecebimento = formaDeRecebimento;
    }

    @Override
    public String toString() {
        return  "Receita[" + codigo +
                "] nome=" + nome +
                " categoria=" + categoria +
                " descricao=" + descricao +
                " valor=" + valor +
                " data=" + data +
                " formaDeRecebimento=" + formaDeRecebimento;
    }
}
