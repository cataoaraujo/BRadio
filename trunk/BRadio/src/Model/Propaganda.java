/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Rodrigo
 */
public class Propaganda {
    private int codigo;
    private String nome;
    private Date inicio;
    private Date fim;
    private File arquivo;
    private Cliente cliente;
    private Time horarios;
    private boolean ativa;

    public Propaganda() {
    }

    public Propaganda(String nome, Time hora) {
        this.nome = nome;
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

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public File getArquivo() {
        return arquivo;
    }

    public void setArquivo(File arquivo) {
        this.arquivo = arquivo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Time getHorarios() {
        return horarios;
    }

    public void setHorarios(Time horarios) {
        this.horarios = horarios;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

   

    @Override
    public String toString() {
        return nome;
    }
    
    
}
