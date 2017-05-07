package com.ifpb.agendanota10.entidade;

import com.ifpb.agendanota10.controle.Autenticavel;
import com.ifpb.agendanota10.excecoes.InvalidEmailException;
import com.ifpb.agendanota10.excecoes.InvalidPasswordException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Usuario implements Serializable, Autenticavel{

    private String nome;
    private LocalDate nascimento;
    private char sexo;
    private String email;
    private String senha;
    
    public Usuario(){
        
    }
    
    public Usuario(String nome, LocalDate nascimento, char sexo, String email, String senha){
        this.nome = nome;
        this.nascimento = nascimento;
        this.sexo = sexo;
        this.email = email;
        this.senha = senha;
    }
    
    public String getNome(){
        return nome;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws InvalidEmailException {
        if (email.equals("")) {
            throw new InvalidEmailException("Email não pode ser vaio");
        }
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) throws InvalidPasswordException {
        if (senha.equals("")) {
            throw new InvalidPasswordException("Senha inválida");
        }
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nome=" + nome + ", nascimento=" + nascimento + ", sexo=" + sexo + ", email=" + email + ", senha=" + senha + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.nome);
        hash = 41 * hash + Objects.hashCode(this.nascimento);
        hash = 41 * hash + this.sexo;
        hash = 41 * hash + Objects.hashCode(this.email);
        hash = 41 * hash + Objects.hashCode(this.senha);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (this.sexo != other.sexo) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.senha, other.senha)) {
            return false;
        }
        if (!Objects.equals(this.nascimento, other.nascimento)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean autenticar(String email, String senha) {
        return this.email.equals(email) && this.senha.equals(senha);
    }
    
    
    
}
