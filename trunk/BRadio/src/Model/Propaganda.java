/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author Rodrigo
 */
public class Propaganda {

    private int codigo;
    private String nome;
    private LocalDate data;
    private File arquivo;
    private Cliente cliente;
    private LocalTime hora;
    private boolean ativa;

    public Propaganda() {
    }

    public Propaganda(String nome, LocalTime hora) {
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
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

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
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
