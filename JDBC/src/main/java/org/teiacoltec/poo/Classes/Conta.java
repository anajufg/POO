package org.teiacoltec.poo.Classes;

import java.util.ArrayList;

public class Conta {
    final private int numero;
    final private Agencia agencia;
    private ArrayList<Cliente> clientes;
    private int proximoNumeroCliente;

    public Conta(int numero, Agencia agencia) {
        this.numero = numero;
        this.agencia = agencia;
        this.clientes = new ArrayList<Cliente>();
        this.proximoNumeroCliente = 1;
    }

    private int proximoNumero() {
        return this.proximoNumeroCliente++;
    }

    /* Associação e desassociação entre entidades */
    public void criaCliente(String cpf_cnpj, String nomeCompleto, String endereco, String telefone, float saldo) {
        Cliente cliente = new Cliente(cpf_cnpj, nomeCompleto, endereco, telefone, saldo);
        this.clientes.add(cliente);
    }
    public void removeCliente(String cpf_cnpj) {
        for (Cliente cliente: clientes ) {
            if (cliente.getCpf_cnpj().equals(cpf_cnpj)) {
                this.clientes.remove(cliente.getCpf_cnpj());
                break;
            }
        }
    }

    /* Gets e Sets */
    public int getNumero() { return this.numero; }

    public Agencia getAgencia() { return  this.agencia; }

    public ArrayList<Cliente> getClientes() { return this.clientes; }
}