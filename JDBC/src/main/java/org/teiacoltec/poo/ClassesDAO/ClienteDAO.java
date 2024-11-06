package org.teiacoltec.poo.ClassesDAO;

import org.teiacoltec.poo.Classes.Cliente;
import org.teiacoltec.poo.conexao.Conexao;
import org.teiacoltec.poo.conexao.FalhaConexaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private static final int CPFCNPJ_POSICAO_TABELA = 1;
    private static final int NOMECOMPLETO_POSICAO_TABELA = 2;
    private static final int ENDERECO_POSICAO_TABELA = 3;
    private static final int TELEFONE_POSICAO_TABELA = 4;
    private static final int SALDO_POSICAO_TABELA = 5;

    static final int DUPLICATE_KEY_ERROR_CODE = 1062;

    public static void criaTabelaCliente() throws FalhaConexaoException {
        try {
            // Estabelece conexao
            Connection conexao = Conexao.obtemConexao();

            // Faz a consulta
            Statement stmt = conexao.createStatement();
            stmt.execute("CREATE SCHEMA IF NOT EXISTS `coltec` DEFAULT CHARACTER SET utf8;");
            stmt.execute("CREATE TABLE IF NOT EXISTS `coltec`.`Cliente` (" +
                    "cpf_cnpj VARCHAR(20) NOT NULL," +
                    "nomeCompleto VARCHAR(255) NOT NULL," +
                    "endereco VARCHAR(255) NOT NULL," +
                    "telefone VARCHAR(20) NOT NULL," +
                    "saldo FLOAT," +
                    "PRIMARY KEY (`cpf_cnpj`));");
        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public static Cliente obtemClientePorNumero(String cpf_cnpj) throws ClienteNaoEncontradoException, FalhaConexaoException {

        try {
            // Estabelecer conexao
            Connection conexao = Conexao.obtemConexao();

            // Faço a consulta
            Statement stmt = conexao.createStatement();
            ResultSet resultado = stmt.executeQuery("SELECT * from Cliente WHERE cpf_cnpj = '" + cpf_cnpj + "';");

            if (resultado.next()) {
                // Obtenho os dados
                return new Cliente(
                        resultado.getString(CPFCNPJ_POSICAO_TABELA),
                        resultado.getString(NOMECOMPLETO_POSICAO_TABELA),
                        resultado.getString(ENDERECO_POSICAO_TABELA),
                        resultado.getString(TELEFONE_POSICAO_TABELA),
                        resultado.getFloat(SALDO_POSICAO_TABELA)
                );
            }

            // Se chegou aqui é porque não tem agencia com esse numero
            throw new ClienteNaoEncontradoException(cpf_cnpj);
        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public static void insere(Cliente cliente) throws ClienteExistenteException, FalhaConexaoException {

        try {
            // Estabelecer conexao
            Connection conexao = Conexao.obtemConexao();

            // Faço a consulta
            PreparedStatement stmt = conexao.prepareStatement("INSERT INTO Cliente VALUES(?,?,?,?,?);");
            stmt.setString(1, cliente.getCpf_cnpj());
            stmt.setString(2, cliente.getNomeCompleto());
            stmt.setString(3, cliente.getEndereco());
            stmt.setString(4, cliente.getTelefone());
            stmt.setFloat(5, cliente.getSaldo());
            stmt.execute();

        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_KEY_ERROR_CODE) throw new ClienteExistenteException(cliente.getCpf_cnpj());
            throw new Error(e.getMessage());
        }
    }

    public static void atualiza(Cliente cliente) throws ClienteNaoEncontradoException, FalhaConexaoException {

        try {
            // Estabelecer conexao
            Connection conexao = Conexao.obtemConexao();

            // Faço a consulta
            PreparedStatement stmt = conexao.prepareStatement("UPDATE Cliente SET nomeCompleto = ?, endereco = ?, telefone = ?, saldo = ? WHERE cpf_cnpj = ?;");
            stmt.setString(1, cliente.getNomeCompleto());
            stmt.setString(2, cliente.getEndereco());
            stmt.setString(3, cliente.getTelefone());
            stmt.setFloat(4, cliente.getSaldo());
            stmt.setString(5, cliente.getCpf_cnpj());

            // Verifica a quantidade de registros alterados
            int nLinhasAlteradas = stmt.executeUpdate();

            // Se não alterou nenhuma linha é porque não tem cliente com esse numero
            if (nLinhasAlteradas == 0) throw new ClienteNaoEncontradoException(cliente.getCpf_cnpj());

        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public static void remove(Cliente cliente) throws ClienteNaoEncontradoException, FalhaConexaoException {

        try {
            // Estabelecer conexao
            Connection conexao = Conexao.obtemConexao();

            // Faço a consulta
            PreparedStatement stmt = conexao.prepareStatement("DELETE FROM Cliente WHERE cpf_cnpj = ?;");
            stmt.setString(1, cliente.getCpf_cnpj());

            // Verifica a quantidade de registros alterados
            int nLinhasAlteradas = stmt.executeUpdate();

            // Se não alterou nenhuma linha é porque não tem cliente com esse numero
            if (nLinhasAlteradas == 0) throw new ClienteNaoEncontradoException(cliente.getCpf_cnpj());

        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public static List<Cliente> obtemListaCliente() throws FalhaConexaoException {

        try {
            // Estabelecer conexao
            Connection conexao = Conexao.obtemConexao();

            // Faço a consulta
            Statement stmt = conexao.createStatement();
            ResultSet resultado = stmt.executeQuery("SELECT * from Cliente ORDER BY nomeCompleto;");

            // Crio a lista
            List<Cliente> lista = new ArrayList<>();

            while(resultado.next()) {
                // Obtenho os dados
                Cliente clienteTmp = new Cliente(
                        resultado.getString(CPFCNPJ_POSICAO_TABELA),
                        resultado.getString(NOMECOMPLETO_POSICAO_TABELA),
                        resultado.getString(ENDERECO_POSICAO_TABELA),
                        resultado.getString(TELEFONE_POSICAO_TABELA),
                        resultado.getFloat(SALDO_POSICAO_TABELA)
                );
                // Adiciono à lista
                lista.add(clienteTmp);
            }

            // Retorna a lista preenchida
            return lista;

        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }
}
