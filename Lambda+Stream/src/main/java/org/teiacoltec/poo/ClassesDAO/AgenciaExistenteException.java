package org.teiacoltec.poo.ClassesDAO;

public class AgenciaExistenteException extends Exception {

    public AgenciaExistenteException(int numero) {
        super("Agencia com numero " + numero + " ja existente no banco de dados.");
    }
}