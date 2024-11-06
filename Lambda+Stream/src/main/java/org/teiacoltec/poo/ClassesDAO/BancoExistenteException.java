package org.teiacoltec.poo.ClassesDAO;

public class BancoExistenteException extends Exception {

    public BancoExistenteException(String cnpj) {
        super("Banco com CNPJ " + cnpj + " ja existente no banco de dados.");
    }
}