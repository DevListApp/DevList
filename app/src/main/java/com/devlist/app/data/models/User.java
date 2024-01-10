package com.devlist.app.data.models;

public class User {
    // Atributos da classe representando os dados de um usuário
    private int id; // Identificador único do usuário
    private String name; // Nome do usuário
    private String email; // Endereço de e-mail do usuário
    private String password; // Senha do usuário
    private String confirmPassword; // Confirmação de senha (usado geralmente em processos de registro)

    // Construtor vazio (default) da classe
    public User() {
    }

    // Construtor com parâmetros para inicializar os atributos ao criar uma instância
    public User(String name, String email, String password, String confirmPassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    // Métodos getter e setter para acessar e modificar os atributos do usuário

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
