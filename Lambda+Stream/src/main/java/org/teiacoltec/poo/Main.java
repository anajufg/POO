package org.teiacoltec.poo;

import org.teiacoltec.poo.conexao.FalhaConexaoException;

import java.util.List;

public class Main {

    public static void main(String[] args) throws FalhaConexaoException {

        MetodosFacilitadores metodos = new MetodosFacilitadores();

        System.out.println("----------------------------------");
        System.out.println("Clientes com saldo negativo:");
        metodos.listaClienteSaldoNegativo();
        System.out.println("----------------------------------\n");

        System.out.println("----------------------------------");
        System.out.println("Contas da agencia 1:");
        metodos.listaContasAgencia(1);
        System.out.println("----------------------------------\n");

        System.out.println("----------------------------------");
        System.out.println("Telefone de todos os clientes:");
        metodos.listaTelefoneClientes();
        System.out.println("----------------------------------\n");

        System.out.println("----------------------------------");
        System.out.println("Numero de contas da agencia 1: " + metodos.obtemNumeroContasAgencia(1));
        System.out.println("----------------------------------\n");

        System.out.println("----------------------------------");
        System.out.println("Numeros das agencias do banco 1: ");
        List<Integer> numerosAgencia = metodos.obtemNumerosAgenciasBanco("00.000.000/0001-91");
        for (int num : numerosAgencia) {
            System.out.println(num);
        }
        System.out.println("----------------------------------\n");
    }
}
