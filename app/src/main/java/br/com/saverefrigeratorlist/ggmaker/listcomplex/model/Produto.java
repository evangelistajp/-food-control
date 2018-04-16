package br.com.saverefrigeratorlist.ggmaker.listcomplex.model;

import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by evang on 03/01/2018.
 */

public class Produto implements Serializable {

    private int id;
    private String codigo;
    private String nome;
    private Double preco;
    private Date data_compra;
    private Date data_validade;


    public Produto() {
    }

    public Produto(String cod, String nome ) {
        this.codigo = cod;
        this.nome = nome;
    }

    public Produto(String cod, String nome , Date compra, Date vencimento) {
        this.codigo = cod;
        this.nome = nome;
        this.data_compra = compra;
        this.data_validade = vencimento;
    }



    public Produto(String cod, String nome ,double preco ,Date compra, Date vencimento) {

        this.codigo = cod;
        this.nome = nome;
        this.preco = preco;
        this.data_compra = compra;
        this.data_validade = vencimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getData_compra() {
        return data_compra;
    }

    public void setData_compra(Date data_compra) {
        this.data_compra = data_compra;
    }

    public Date getData_validade() {
        return data_validade;
    }

    public void setData_validade(Date data_validade) {
        this.data_validade = data_validade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", preço='" + preco + '\'' +
                ", data_compra=" + data_compra +
                ", data_validade=" + data_validade +
                '}';
    }
}
