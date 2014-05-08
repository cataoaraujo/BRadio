/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ProgressBar;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 *
 * @author Rodrigo
 */
public class Player<T> {

    private File arquivo;
    private EstadoPlayer estado;
    private MediaPlayer mediaPlayer;

    private ProgressBar pb;
    private ChangeListener<Duration> progressChangeListener;

    public File getArquivo() {
        return arquivo;
    }

    public void setArquivo(File arquivo) {
        this.arquivo = arquivo;
    }

    public EstadoPlayer getEstado() {
        return estado;
    }

    public void addProgressBar(ProgressBar pb) {
        this.pb = pb;
    }

    private void atualizaProgressBar() {
        progressChangeListener = new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
                pb.setProgress(1.0 * mediaPlayer.getCurrentTime().toMillis() / mediaPlayer.getTotalDuration().toMillis());
            }

        };
        mediaPlayer.currentTimeProperty().addListener(progressChangeListener);
    }

    public boolean play() {
        if (arquivo != null) {
            if (mediaPlayer != null) {
                mediaPlayer.dispose();
            }
            String source = this.arquivo.toURI().toString();
            Media media = new Media(source);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            if (this.pb != null) {
                atualizaProgressBar();
            }
            this.estado = EstadoPlayer.Tocando;
            return true;
        } else {
            return false;
        }
    }

    public boolean stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.estado = EstadoPlayer.Parado;
        }
        return true;
    }
}
