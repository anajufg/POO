package org.teiacoltec.poo.ClassesDAO;

import org.teiacoltec.poo.Classes.Cliente;
import org.teiacoltec.poo.Classes.Conta;
import org.teiacoltec.poo.conexao.Conexao;
import org.teiacoltec.poo.conexao.FalhaConexaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Conta_ClienteDAO {

    private static final int NUMEROCONTA_POSICAO_TABELA = 1;
    private static final int CPF_CNPJ_CLIENTE_POSICAO_TABELA = 2;

    public static void criaTabelaConta_Cliente() throws FalhaConexaoException {
        try {
            // Estabelece conexao
            Connection conexao = Conexao.obtemConexao();

            // Faz a consulta
            Statement stmt = conexao.createStatement();
            stmt.execute("CREATE SCHEMA IF NOT EXISTS `coltec` DEFAULT CHARACTER SET utf8;");
            stmt.execute( "CREATE TABLE IF NOT EXISTS `coltec`.`Conta_Cliente` (" +
                    "`numero_conta` INT NOT NULL," +
                    "`cpf_cnpj_cliente` VARCHAR(20) NOT NULL," +
                    "PRIMARY KEY (`numero_conta`, `cpf_cnpj_cliente`)," +
                    "INDEX `fk_Conta_has_Cliente_Cliente1_idx` (`cpf_cnpj_cliente` ASC) VISIBLE," +
                    "INDEX `fk_Conta_has_Cliente_Conta1_idx` (`numero_conta` ASC) VISIBLE," +
                    "CONSTRAINT `fk_Conta_has_Cliente_Conta1` " +
                    "FOREIGN KEY (`numero_conta`) " +
                    "REFERENCES `coltec`.`Conta` (`numero`) ON DELETE NO ACTION ON UPDATE NO ACTION," +
                    "CONSTRAINT `fk_Conta_has_Cliente_Cliente1` " +
                    "FOREIGN KEY (`cpf_cnpj_cliente`) " +
                    "REFERENCES `coltec`.`Cliente` (`cpf_cnpj`) ON DELETE NO ACTION ON UPDATE NO ACTION) ENGINE = InnoDB;");
        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public static void insere(int numConta, String cpf_cnpj_cliente) throws FalhaConexaoException {

        try {
            // Estabelecer conexao
            Connection conexao = Conexao.obtemConexao();

            // Cria tabela se ela nao existe
            Conta_ClienteDAO.criaTabelaConta_Cliente();

            // Faço a consulta
            PreparedStatement stmt = conexao.prepareStatement("INSERT INTO Conta_Cliente VALUES(?,?);");
            stmt.setInt(1, numConta);
            stmt.setString(2, cpf_cnpj_cliente);
            stmt.execute();

        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public static void remove(int numConta, String cpf_cnpj_cliente) throws ClienteNaoEncontradoException, FalhaConexaoException {

        try {
            // Estabelecer conexao
            Connection conexao = Conexao.obtemConexao();

            // Faço a consulta
            PreparedStatement stmt = conexao.prepareStatement("DELETE FROM Conta_Cliente WHERE numero_conta = ? && cpf_cnpj_cliente = ?;");
            stmt.setInt(1, numConta);
            stmt.setString(2, cpf_cnpj_cliente);

            // Verifica a quantidade de registros alterados
            int nLinhasAlteradas = stmt.executeUpdate();

            // Se não alterou nenhuma linha
            if (nLinhasAlteradas == 0) throw new ClienteNaoEncontradoException(cpf_cnpj_cliente);

        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public static List<Conta> obtemListaContaDeUmCliente(String cpf_cnpj_cliente) throws ClienteNaoEncontradoException, FalhaConexaoException {

        try {
            // Estabelecer conexao
            Connection conexao = Conexao.obtemConexao();

            // Faço a consulta
            Statement stmt = conexao.createStatement();
            ResultSet resultado = stmt.executeQuery("SELECT numero_conta from Conta_Cliente WHERE cpf_cnpj_cliente = '" + cpf_cnpj_cliente + "';");

            // Crio a lista
            List<Conta> lista = new ArrayList<>();

            while(resultado.next()) {
                // Obtenho os dados
                Conta contaTmp = ContaDAO.obtemContaPorNumero(resultado.getInt(NUMEROCONTA_POSICAO_TABELA));
                // Adiciono à lista
                lista.add(contaTmp);
            }

            if (lista.isEmpty()) throw new ClienteNaoEncontradoException(cpf_cnpj_cliente);
            else return lista;

        } catch (SQLException | ContaNaoEncontradaException e) {
            throw new Error(e.getMessage());
        }
    }

    public static List<Cliente> obtemListaClienteDeUmaConta(int numConta) throws ContaNaoEncontradaException, FalhaConexaoException {

        try {
            // Estabelecer conexao
            Connection conexao = Conexao.obtemConexao();

            // Faço a consulta
            Statement stmt = conexao.createStatement();
            ResultSet resultado = stmt.executeQuery("SELECT numero_cliente from Conta_Cliente WHERE numero_conta = " + numConta + ";");

            // Crio a lista
            List<Cliente> lista = new ArrayList<>();

            while(resultado.next()) {
                // Obtenho os dados
                Cliente clienteTmp = ClienteDAO.obtemClientePorNumero(resultado.getString(CPF_CNPJ_CLIENTE_POSICAO_TABELA));
                // Adiciono à lista
                lista.add(clienteTmp);
            }

            if (lista.isEmpty()) throw new ContaNaoEncontradaException(numConta);
            else return lista;

        } catch (SQLException | ClienteNaoEncontradoException e) {
            throw new Error(e.getMessage());
        }
    }
}
