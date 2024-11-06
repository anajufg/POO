package org.teiacoltec.poo.ClassesDAO;

public class ContaNaoEncontradaException extends Exception {

    public ContaNaoEncontradaException(int numero) {
        super("Conta de numero " + numero + " nao encontrada.");
    }

}
