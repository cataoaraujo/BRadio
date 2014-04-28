/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;
import java.time.LocalDateTime;
import java.time.format.*;

/**
 *
 * @author Rodrigo
 */
public class Relogio {

    private Label relogio;
    private Label data;
    
    public Relogio(Label relogio) {
        this.relogio = relogio;
        this.getHorario();
    }

    public Relogio(Label relogio, Label data) {
        this.relogio = relogio;
        this.data = data;
        this.getHorario();
        this.getData();
    }
    

    // the digital clock updates once a second.
    private void getHorario() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LocalDateTime ldt = LocalDateTime.now();
                relogio.setText(ldt.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)));
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    private void getData() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LocalDateTime ldt = LocalDateTime.now();
                data.setText(ldt.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
