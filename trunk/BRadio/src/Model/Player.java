/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Rodrigo
 */
public class Player<T> {

    private File arquivo;
    private EstadoPlayer estado;
    private MediaPlayer mediaPlayer;

    public File getArquivo() {
        return arquivo;
    }

    public void setArquivo(File arquivo) {
        this.arquivo = arquivo;
    }

    public EstadoPlayer getEstado() {
        return estado;
    }

    public boolean play() {
        if (arquivo != null) {
            if(mediaPlayer!=null){
                mediaPlayer.dispose();
            }
            String source = this.arquivo.toURI().toString();
            Media media = new Media(source);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            return true;
        } else {
            return false;
        }
    }

    public boolean stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        return true;
    }
}
