package org.teiacoltec.poo.Classes;

import java.util.ArrayList;

public class Banco {
    final private String cnpj;
    private String nome;
    private ArrayList<Agencia> agencias;
    private int proximoNumeroAgencia;

    public Banco(String cnpj, String nome) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.agencias = new ArrayList<Agencia>();
        this.proximoNumeroAgencia = 1;
    }

    private int proximoNumero() {
        return this.proximoNumeroAgencia++;
    }

    /* Associação e desassociação entre entidades */
    public void criaAgencia (String nome, String endereco, String telefone) {
        Agencia agencia = new Agencia(proximoNumero(), nome, endereco, telefone, this);
        this.agencias.add(agencia);
    }

    public void removeAgencia(int numero) {
        for (Agencia agencia: agencias ) {
            if (agencia.getNumero() == numero) {
                agencias.remove(agencia.getNumero());
                break;
            }
        }
    }

    /* Gets e Sets */
    public String getCnpj() { return this.cnpj; }

    public void setNome(String nome) { this.nome = nome; }
    public String getNome() { return this.nome; }

    public ArrayList<Agencia> getAgencias() { return this.agencias; }

}
