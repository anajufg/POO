package org.teiacoltec.poo.Classes;

import java.util.ArrayList;

public class Cliente {
    private String cpf_cnpj;
    private String nomeCompleto;
    private String endereco;
    private String telefone;
    private float saldo;
    private ArrayList<Conta> contas;
    private int proximoNumeroConta;

    public Cliente(String cpf_cnpj, String nomeCompleto, String endereco, String telefone, float saldo) {
        this.cpf_cnpj = cpf_cnpj;
        this.nomeCompleto = nomeCompleto;
        this.endereco = endereco;
        this.telefone = telefone;
        this.saldo = saldo;
        this.contas = new ArrayList<Conta>();
        this.proximoNumeroConta = 1;
    }

    private int proximoNumero() {
        return this.proximoNumeroConta++;
    }

    /* Associação e desassociação entre entidades */
    public void criaConta(Agencia agencia) {
        Conta conta = new Conta(proximoNumero(), agencia);
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
    public String getCpf_cnpj() { return this.cpf_cnpj; }

    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }
    public String getNomeCompleto() { return this.nomeCompleto; }

    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getEndereco() { return this.endereco; }

    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getTelefone() { return this.telefone; }

    public void setSaldo(float saldo) { this.saldo = saldo; }
    public float getSaldo() { return this.saldo; }

    public ArrayList<Conta> getContas() { return this.contas; }
}