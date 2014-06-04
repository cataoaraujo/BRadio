/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author MatheuseJessica
 */
public enum TipoRelatorio {

    ENTREDATAS("Entre datas"),
    ATIVAS("Propagandas ativas"),
    ENTREDATASSIMPLES("Entre datas simplificado");

    private String nome;

    private TipoRelatorio(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
