package org.teiacoltec.poo;

import org.teiacoltec.poo.Classes.*;
import org.teiacoltec.poo.ClassesDAO.*;
import org.teiacoltec.poo.conexao.FalhaConexaoException;

public class JDBC {

    static void testaInsere() {
        try {
            // Cria Banco
            Banco banco = new Banco("00.000.000/0001-91", "Banco do Brasil");
            BancoDAO.insere(banco);

            // Tenta recuperar o banco recém criado
            Banco bancoRecuperado = BancoDAO.obtemBancoPorCnpj("00.000.000/0001-91");

            // Mostra as informações dos dois para serem comparadas
            System.out.println("Banco do banco: " + banco.getCnpj());
            System.out.println("Banco recuperado: " + bancoRecuperado.getCnpj());

            // Cria Agencia
            banco.criaAgencia("Agência Banco do Brasil - Centro", "Avenida Afonso Pena, 1.001 - Centro, Belo Horizonte - MG", "(31) 3216-8888");
            AgenciaDAO.insere(banco.getAgencias().getFirst());

            // Cria Conta
            banco.getAgencias().getFirst().criaConta();
            ContaDAO.insere(banco.getAgencias().getFirst().getContas().getFirst());

            // Cria Cliente
            banco.getAgencias().getFirst().getContas().getFirst().criaCliente("123.456.789-09", "João da Silva", "Rua das Flores, 123, Bairro Jardim, Belo Horizonte - MG", "(31) 98765-4321", 300);
            ClienteDAO.insere(banco.getAgencias().getFirst().getContas().getFirst().getClientes().getFirst());

            // Adciona na tabela Conta_Cliente a relacao entre a conta e o cliente criados
            Conta_ClienteDAO.insere(banco.getAgencias().getFirst().getContas().getFirst().getNumero(), banco.getAgencias().getFirst().getContas().getFirst().getClientes().getFirst().getCpf_cnpj());

        } catch (BancoExistenteException | FalhaConexaoException | BancoNaoEncontradoException |
                 AgenciaExistenteException | ContaExistenteException | ClienteExistenteException e) {
            System.err.println("Excecao: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws FalhaConexaoException {

        // Cria as tabelas se necessario
        BancoDAO.criaTabelaBanco();
        AgenciaDAO.criaTabelaAgencia();
        ContaDAO.criaTabelaConta();
        ClienteDAO.criaTabelaCliente();

        testaInsere();
    }
}
