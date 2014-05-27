/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bradio.busca;

import Model.Logger.GeradorLog;
import Model.Musica;
import insidefx.undecorator.Undecorator;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author Rodrigo
 */
public class BuscaMusica {

    private static Stage stage;
    private static Musica musicaSelecionada;
    private static BuscaMusica instance;
    private static ListView lista;

    public static void setMusicaSelecionada(Musica musicaSelecionada) {
        BuscaMusica.musicaSelecionada = musicaSelecionada;
        lista.getItems().add(musicaSelecionada);
    }

    public static Musica getMusicaSelecionada() {
        return musicaSelecionada;
    }

    public boolean pesquisando() {
        return stage.isShowing();
    }

    private BuscaMusica() {
//        if(instance ==null){
//            instance = this;
//        }
        musicaSelecionada = null;
        stage = new Stage();
        replaceSceneContent("BuscaMusica.fxml");
        stage.show();
    }

    public static boolean show(ListView lista) {
        instance = new BuscaMusica();
        BuscaMusica.lista=lista;
        return true;
    }
    public static boolean close() {
        stage.close();
        return true;
    }

    private Parent replaceSceneContent(String fxml) {
        try {
            Parent page = (Parent) FXMLLoader.load(BuscaMusica.class.getResource(fxml), null, new JavaFXBuilderFactory());
            stage.close(); //Para nao dar erro no Undecorator na hora de trocar a tela
            stage = new Stage(StageStyle.TRANSPARENT);
            Undecorator undecorator = new Undecorator(stage, (Region) page);
            undecorator.getStylesheets().add("skin/undecorator.css");
            Scene scene = new Scene(undecorator);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);

            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();
            return page;
        } catch (IOException e) {
            GeradorLog.getLoggerFull().severe("FXML: " + fxml + " --- replaceSceneContent: " + e);
            return null;
        }
    }

    public void fechar() {
        stage.close();
    }
}
