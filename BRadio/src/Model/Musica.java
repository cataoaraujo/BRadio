/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.Logger.GeradorLog;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
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
        if (titulo != null) {
            this.titulo = titulo.replace("\0", "");
        }
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        if (artista != null) {
            this.artista = artista.replace("\0", "");
        }
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        if (genero != null) {
            this.genero = genero.replace("\0", "");
        }
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        if (album != null) {
            this.album = album.replace("\0", "");
        }
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
            if (mp3file.hasID3v1Tag()) {
                ID3v1 tag = mp3file.getID3v1Tag();
                this.setArtista(tag.getArtist());
                this.setTitulo(tag.getTitle());
                if (mp3file.hasID3v2Tag()) {
                    AbstractID3v2 tag2 = mp3file.getID3v2Tag();
                    this.setGenero(tag2.getSongGenre());
                } else {
                    this.setGenero(tag.getSongGenre());
                }
                this.setAlbum(tag.getAlbum());
            } else if (mp3file.hasID3v2Tag()) {
                AbstractID3v2 tag = mp3file.getID3v2Tag();
                this.setArtista(tag.getLeadArtist());
                this.setTitulo(tag.getSongTitle());
                this.setGenero(tag.getSongGenre());
                this.setAlbum(tag.getAlbumTitle());
            }
        } catch (IOException | TagException ex) {

            GeradorLog.getLoggerFull().severe("Problema ao coletar dados da música \"" + this.getArquivo().getAbsolutePath() + "\"  Problema:" + ex);
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
