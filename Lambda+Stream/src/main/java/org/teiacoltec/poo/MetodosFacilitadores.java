package org.teiacoltec.poo;

import org.teiacoltec.poo.Classes.*;
import org.teiacoltec.poo.ClassesDAO.*;
import org.teiacoltec.poo.conexao.FalhaConexaoException;

import java.util.List;
import java.util.stream.Collectors;

public class MetodosFacilitadores {
    private List<Agencia> agencias;
    private List<Conta> contas;
    private List<Cliente> clientes;
    private List<Banco> bancos;

    public MetodosFacilitadores() throws FalhaConexaoException {
        // Obtem os bancos presentes do BD
        bancos = BancoDAO.obtemListaBanco();

        // Obtem as agencias presentes do BD
        agencias = AgenciaDAO.obtemListaAgencia();

        // Obtem as contas presentes do BD
        contas = ContaDAO.obtemListaConta();

        // Obtem os cliente presentes do BD
        clientes = ClienteDAO.obtemListaCliente();
    }

    public void listaContasAgencia(int numAgencia) {
         contas.stream().filter(c -> c.getAgencia().getNumero() == numAgencia).forEach(c -> System.out.println(c.getNumero()));
    }

    public void listaClienteSaldoNegativo() {
        clientes.stream().filter(c -> c.getSaldo() < 0).forEach(c -> System.out.println(c.getCpf_cnpj()));
    }

    public List<Integer> obtemNumerosAgenciasBanco(String cnpjBanco) {
        List<Integer> listanumeros = agencias.stream().
                                     filter(a -> a.getBanco().getCnpj().equals(cnpjBanco)).
                                     map(a -> a.getNumero()).collect(Collectors.toList());
        return listanumeros;

    }

    public void listaTelefoneClientes() {
        clientes.stream().forEach(c -> System.out.println(c.getTelefone()));
    }

    public int obtemNumeroContasAgencia(int numAgencia) {
        int numConta = (int) contas.stream().
                             filter(c -> c.getAgencia().getNumero() == numAgencia).
                             count();
        return numConta;

    }

}
