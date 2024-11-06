package org.teiacoltec.poo.ClassesDAO;

import org.teiacoltec.poo.Classes.Conta;
import org.teiacoltec.poo.conexao.Conexao;
import org.teiacoltec.poo.conexao.FalhaConexaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContaDAO {

    private static final int NUMERO_POSICAO_TABELA = 1;
    private static final int NUMEROAGENCIA_POSICAO_TABELA = 2;

    static final int DUPLICATE_KEY_ERROR_CODE = 1062;

    public static void criaTabelaConta() throws FalhaConexaoException {
        try {
            // Estabelece conexao
            Connection conexao = Conexao.obtemConexao();

            // Faz a consulta
            Statement stmt = conexao.createStatement();
            stmt.execute("CREATE SCHEMA IF NOT EXISTS `coltec` DEFAULT CHARACTER SET utf8;");
            stmt.execute("CREATE TABLE IF NOT EXISTS `coltec`.`Conta` (" +
                            "`numero` INT NOT NULL," +
                            "`numero_agencia` INT NOT NULL," +
                            "PRIMARY KEY (`numero`)," +
                            "INDEX `fk_Conta_Agencia1_idx` (`numero_agencia` ASC)," +
                            "CONSTRAINT `fk_Conta_Agencia1` FOREIGN KEY (`numero_agencia`) " +
                            "REFERENCES `coltec`.`Agencia` (`numero`) ON DELETE CASCADE) ENGINE = InnoDB;");
        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public static Conta obtemContaPorNumero(int numero) throws ContaNaoEncontradaException, FalhaConexaoException {

        try {
            // Estabelecer conexao
            Connection conexao = Conexao.obtemConexao();

            // Faço a consulta
            Statement stmt = conexao.createStatement();
            ResultSet resultado = stmt.executeQuery("SELECT * from Conta WHERE numero = " + numero + ";");

            if (resultado.next()) {
                // Obtenho os dados
                return new Conta(resultado.getInt(NUMERO_POSICAO_TABELA),
                        AgenciaDAO.obtemAgenciaPorNumero(resultado.getInt(NUMEROAGENCIA_POSICAO_TABELA))
                );
            }

            // Se chegou aqui é porque não tem agencia com esse numero
            throw new ContaNaoEncontradaException(numero);
        } catch (SQLException | AgenciaNaoEncontradaException e) {
            throw new Error(e.getMessage());
        }
    }

    public static void insere(Conta conta) throws ContaExistenteException, FalhaConexaoException {

        try {
            // Estabelecer conexao
            Connection conexao = Conexao.obtemConexao();

            // Faço a consulta
            PreparedStatement stmt = conexao.prepareStatement("INSERT INTO Conta VALUES(?,?);");
            stmt.setInt(1, conta.getNumero());
            stmt.setInt(2, conta.getAgencia().getNumero());
            stmt.execute();

        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_KEY_ERROR_CODE) throw new ContaExistenteException(conta.getNumero());
            throw new Error(e.getMessage());
        }
    }

    public static void atualiza(Conta conta) throws ContaNaoEncontradaException, FalhaConexaoException {

        try {
            // Estabelecer conexao
            Connection conexao = Conexao.obtemConexao();

            // Faço a consulta
            PreparedStatement stmt = conexao.prepareStatement("UPDATE Conta SET numero_agencia = ? WHERE numero = ?;");
            stmt.setInt(1, conta.getAgencia().getNumero());
            stmt.setInt(2, conta.getNumero());

            // Verifica a quantidade de registros alterados
            int nLinhasAlteradas = stmt.executeUpdate();

            // Se não alterou nenhuma linha é porque não tem conta com esse numero
            if (nLinhasAlteradas == 0) throw new ContaNaoEncontradaException(conta.getNumero());

        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public static void remove(Conta conta) throws ContaNaoEncontradaException, FalhaConexaoException {

        try {
            // Estabelecer conexao
            Connection conexao = Conexao.obtemConexao();

            // Faço a consulta
            PreparedStatement stmt = conexao.prepareStatement("DELETE FROM Conta WHERE numero = ?;");
            stmt.setInt(1, conta.getNumero());

            // Verifica a quantidade de registros alterados
            int nLinhasAlteradas = stmt.executeUpdate();

            // Se não alterou nenhuma linha é porque não tem conta com esse numero
            if (nLinhasAlteradas == 0) throw new ContaNaoEncontradaException(conta.getNumero());

        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public static List<Conta> obtemListaConta() throws FalhaConexaoException {

        try {
            // Estabelecer conexao
            Connection conexao = Conexao.obtemConexao();

            // Faço a consulta
            Statement stmt = conexao.createStatement();
            ResultSet resultado = stmt.executeQuery("SELECT * from Conta ORDER BY numero;");

            // Crio a lista
            List<Conta> lista = new ArrayList<>();

            while(resultado.next()) {
                // Obtenho os dados
                Conta contaTmp = new Conta(resultado.getInt(NUMERO_POSICAO_TABELA),
                        AgenciaDAO.obtemAgenciaPorNumero(resultado.getInt(NUMEROAGENCIA_POSICAO_TABELA)));
                // Adiciono à lista
                lista.add(contaTmp);
            }

            // Retorna a lista preenchida
            return lista;

        } catch (SQLException | AgenciaNaoEncontradaException e) {
            throw new Error(e.getMessage());
        }
    }
}
