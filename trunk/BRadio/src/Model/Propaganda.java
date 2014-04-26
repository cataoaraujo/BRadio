/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.sql.Time;

/**
 *
 * @author Rodrigo
 */
public class Propaganda {
    private int codigo;
    private String nome;
    private Time hora;

    public Propaganda() {
    }

    public Propaganda(String nome, Time hora) {
        this.nome = nome;
        this.hora = hora;
    }

    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return hora+" "+nome;
    }
    
    
}
