package org.teiacoltec.poo.ClassesDAO;

public class BancoNaoEncontradoException extends Exception {

    public BancoNaoEncontradoException(String cnpj) {
        super("Banco de CNPJ " + cnpj + " nao encontrado.");
    }

}
