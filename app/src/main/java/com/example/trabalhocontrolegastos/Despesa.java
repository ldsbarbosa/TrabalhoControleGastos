/**
 * DESPESAS: são os gastos, importante que seja armazenado
 *
 * Categoria do Gasto: Ex: Saúde, Alimentação, Educação, Lazer
 *
 * Descrição:  ex: compra de medicamentos - Farmácia - compras no supermercado - Almoço - Pagamento da faculdade etc
 *
 * Valor
 *
 * Data
 *
 * Forma de pagamento: Dinheiro, Cartão de Débito, Cartão de Crédito, PIX
 */
package com.example.trabalhocontrolegastos;

import java.io.Serializable;

public class Despesa implements Serializable {

    private Integer codigo_despesa;
    private String nome_despesa;
    private String categoria_despesa;
    private String descricao_despesa;
    private Double valor_despesa;
    private String data_despesa;
    private String formaDePagamento_despesa;

    public Despesa(Integer codigo_despesa, String nome_despesa, String categoria_despesa, String descricao_despesa,
                   Double valor_despesa, String data_despesa, String formaDePagamento_despesa) {
        this.codigo_despesa = codigo_despesa;
        this.nome_despesa = nome_despesa;
        this.categoria_despesa = categoria_despesa;
        this.descricao_despesa = descricao_despesa;
        this.valor_despesa = valor_despesa;
        this.data_despesa = data_despesa;
        this.formaDePagamento_despesa = formaDePagamento_despesa;
    }

    public Despesa() { }

    public Integer getCodigo_despesa() {
        return codigo_despesa;
    }

    public void setCodigo_despesa(Integer codigo_despesa) {
        this.codigo_despesa = codigo_despesa;
    }

    public String getNome_despesa() {
        return nome_despesa;
    }

    public void setNome_despesa(String nome_despesa) {
        this.nome_despesa = nome_despesa;
    }

    public String getCategoria_despesa() {
        return categoria_despesa;
    }

    public void setCategoria_despesa(String categoria_despesa) {
        this.categoria_despesa = categoria_despesa;
    }

    public String getDescricao_despesa() {
        return descricao_despesa;
    }

    public void setDescricao_despesa(String descricao_despesa) {
        this.descricao_despesa = descricao_despesa;
    }

    public Double getValor_despesa() {
        return valor_despesa;
    }

    public void setValor_despesa(Double valor_despesa) {
        this.valor_despesa = valor_despesa;
    }

    public String getData_despesa() {
        return data_despesa;
    }

    public void setData_despesa(String data_despesa) {
        this.data_despesa = data_despesa;
    }

    public String getFormaDePagamento_despesa() {
        return formaDePagamento_despesa;
    }

    public void setFormaDePagamento_despesa(String formaDePagamento_despesa) {
        this.formaDePagamento_despesa = formaDePagamento_despesa;
    }

    @Override
    public String toString() {
        return "Despesa{" +
                "codigo_despesa=" + codigo_despesa +
                ", nome_despesa='" + nome_despesa + '\'' +
                ", categoria_despesa='" + categoria_despesa + '\'' +
                ", descricao_despesa='" + descricao_despesa + '\'' +
                ", valor_despesa=" + valor_despesa +
                ", data_despesa='" + data_despesa + '\'' +
                ", formaDePagamento_despesa='" + formaDePagamento_despesa + '\'' +
                '}';
    }
}
