/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.Logger.GeradorLog;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.AbstractID3v2;
import org.farng.mp3.id3.ID3v1;

/**
 *
 * @author Rodrigo
 */
public class Musica {

    private int codigo;
    private String titulo;
    private String artista;
    private String genero;
    private String album;
    private File arquivo;

    public Musica() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public File getArquivo() {
        return arquivo;
    }

    public void setArquivo(File arquivo) {
        this.arquivo = arquivo;
    }

    public void getMetadata() {
        try {
            MP3File mp3file = new MP3File(this.getArquivo());
            if (mp3file.hasID3v2Tag()) {
                AbstractID3v2 tag = mp3file.getID3v2Tag();
                artista = tag.getLeadArtist();
                titulo = tag.getSongTitle();
                genero = tag.getSongGenre();
                album = tag.getAlbumTitle();
            } else if (mp3file.hasID3v1Tag()) {
                ID3v1 tag = mp3file.getID3v1Tag();
                artista = tag.getArtist();
                titulo = tag.getTitle();
                genero = tag.getGenre() + "";
                album = tag.getAlbum();
            }

        } catch (IOException | TagException ex) {
            
            GeradorLog.getLoggerFull().severe("Problema ao coletar dados da música \"" + this.getArquivo().getAbsolutePath() + "\" ");
            //Logger.getLogger(Musica.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (titulo == null) {
                titulo = arquivo.getName();
            } else {
                if (titulo.isEmpty()) {
                    titulo = arquivo.getName();
                }
            }
            if (artista == null) {
                artista = "Desconhecido";
            } else {
                if (artista.isEmpty()) {
                    artista = "Desconhecido";
                }
            }
            if (album == null) {
                album = "Desconhecido";
            }
        }
    }

    @Override
    public String toString() {
        return titulo + " - " + artista;
    }

}
