/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bradio;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Rodrigo
 */
public class AlertDialog {

    private static String mensagem = "";
    private static final Stage dialog = new Stage(StageStyle.UNDECORATED);
    private static Text label;
    public static void setMensagem(String mensagem) {
        AlertDialog.mensagem = mensagem;
        label.setText(mensagem);
    }
    
    public static void show(String mensagem) {
        AlertDialog.mensagem = mensagem;
        label = new Text(mensagem);
        label.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        Button okButton = new Button("Ok");
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        FlowPane pane = new FlowPane(20, 20);
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(okButton);
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-padding: 15;");
        vBox.setSpacing(20);
        vBox.getChildren().addAll(label, pane);
        Scene scene1 = new Scene(vBox);
        dialog.setScene(scene1);
        dialog.show();
    }

}
