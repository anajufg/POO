package org.teiacoltec.poo.ClassesDAO;

public class ClienteExistenteException extends Exception {

    public ClienteExistenteException(String cpf_cnpj) {
        super("Cliente com cpf_cnpj " + cpf_cnpj + " ja existente no banco de dados.");
    }
}