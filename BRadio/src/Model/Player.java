/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;
import java.util.ArrayList;
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

    private ArrayList<File> arquivos = new ArrayList<>();
    private File arquivo;
    private EstadoPlayer estado;
    private MediaPlayer mediaPlayer;

    private ProgressBar pb;
    private ChangeListener<Duration> progressChangeListener;

    public void addArquivo(int index, File f) {
        arquivos.add(index, f);
    }

    public void addArquivo(File f) {
        arquivos.add(f);
    }

    public void removeArquivo(File f) {
        arquivos.remove(f);
    }

    public ArrayList<File> getArquivos() {
        return arquivos;
    }
    
    /*public File getArquivo() {
        return arquivo;
    }

    public void setArquivo(File arquivo) {
        this.arquivo = arquivo;
    }*/

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
        if (!arquivos.isEmpty()) {
            if (mediaPlayer != null) {
                mediaPlayer.dispose();
            }
            String source = this.arquivos.get(0).toURI().toString();
            removeArquivo(arquivos.get(0));
            Media media = new Media(source);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            if (this.pb != null) {
                atualizaProgressBar();
            }
            this.estado = EstadoPlayer.Tocando;
            if (!arquivos.isEmpty()) {
                tocarProxima();
            }
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

    public void tocarProxima() {
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                estado = EstadoPlayer.Ocioso;
                System.out.println("acabou!");
                /*if (!arquivos.isEmpty()) {
                    play();
                }*/
            }
        });
    }
}
