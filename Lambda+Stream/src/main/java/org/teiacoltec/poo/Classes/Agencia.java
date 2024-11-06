package org.teiacoltec.poo.Classes;

import java.util.ArrayList;

public class Agencia {
    final private int numero;
    private String nome;
    private String endereco;
    private String telefone;
    final private Banco banco;
    private ArrayList<Conta> contas;
    private int proximoNumeroConta = 1;

    public Agencia(int numero, String nome, String endereco, String telefone, Banco banco) {
        this.numero = numero;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.banco = banco;
        this.contas = new ArrayList<Conta>();
        this.proximoNumeroConta = 1;
    }

    private int proximoNumero() {
        return this.proximoNumeroConta++;
    }

    /* Associação e desassociação entre entidades */
    public void criaConta() {
        Conta conta = new Conta(proximoNumero(), this);
        this.contas.add(conta);
    }
    public void removeConta(int numero) {
        for (Conta conta: contas ) {
            if (conta.getNumero() == numero) {
                this.contas.remove(conta.getNumero());
                break;
            }
        }
    }

    /* Gets e Sets */
    public int getNumero() { return this.numero; }

    public Banco getBanco() { return this.banco; }

    public void setNome(String nome) { this.nome = nome; }
    public String getNome() { return this.nome; }

    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getEndereco() { return this.endereco; }

    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getTelefone() { return this.telefone; }

    public ArrayList<Conta> getContas() { return this.contas; }
}