package org.teiacoltec.poo.ClassesDAO;

public class ContaExistenteException extends Exception {

    public ContaExistenteException(int numero) {
        super("Conta com numero " + numero + " ja existente no banco de dados.");
    }
}