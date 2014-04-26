/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.sql.Time;
import java.util.ArrayList;

/**
 *
 * @author Rodrigo
 */
public class Programa {
    private int codigo;
    private String nome;
    private Time horarioInicio;
    private Time horarioFim;
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

    public Time getHorarioInicio() {
        return horarioInicio;
    }

    public void setHoraInicio(Time horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public Time getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(Time horarioFim) {
        this.horarioFim = horarioFim;
    }
    
    public ArrayList<Radialista> getRadialistas() {
        return radialistas;
    }

    public void setRadialistas(ArrayList<Radialista> radialistas) {
        this.radialistas = radialistas;
    }
    
    
}
