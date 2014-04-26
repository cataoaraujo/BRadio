/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.ArrayList;

/**
 *
 * @author Rodrigo
 */
public class Programa {
    private int codigo;
    private String nome;
    private ArrayList<Radialista> radialistas;

    public Programa() {
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

    public ArrayList<Radialista> getRadialistas() {
        return radialistas;
    }

    public void setRadialistas(ArrayList<Radialista> radialistas) {
        this.radialistas = radialistas;
    }
    
    
}
