/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.util.Duration;

/**
 *
 * @author Rodrigo
 */
public class ListCellPropaganda extends ListCell<Propaganda> {

    @Override
    public void updateItem(final Propaganda item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            final ListCellPropaganda instance = this;
            //Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
              //  @Override
                //public void handle(ActionEvent event) {
                    if (item.getHora() != null) {
                        if (item.getHora().isBefore(LocalTime.now())) {
                            instance.setStyle("-fx-background-color: #f00;");
                            instance.setText(item.getHora()+" "+item.toString());
                        } else {
                            instance.setStyle("-fx-background-color: #00a;");
                            instance.setText(item.getHora()+" "+item.toString());
                        }
                    } else {
                        System.out.println("aqui");
                        instance.setStyle("-fx-background-color: #00a;");
                        instance.setText(item.getHora()+" "+item.toString());
                    }
               // }
            //}));
            //timeline.setCycleCount(Animation.INDEFINITE);
            //timeline.play();
        }
    }
}
