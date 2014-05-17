/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bradio;

import Model.Configuration;
import Model.DAO.ConnectionFactory;
import Model.Logger.GeradorLog;
import Model.Login;
import insidefx.undecorator.Undecorator;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Rodrigo
 */
public class BRadio extends Application {

    private static BRadio instance;
    private Stage stage;

    public BRadio() {
        instance = this;
        boot();
        Configuration conf = Configuration.getInstance();
        conf.readConfiguration();
    }

    private void boot() {
        boolean problema = false;
        if (ConnectionFactory.getConnection() == null) {
            problema = true;
            GeradorLog.getLoggerFull().severe("Banco de Dados não iniciou.");
        }

        if (problema) {
            try {
                //Abrir Log
                Runtime.getRuntime().exec("explorer log.html");
            } catch (IOException ex) {
                GeradorLog.getLoggerFull().severe("Não foi abrir o arquivo de log.");
            }
            stage.close();
        }
    }

    public static BRadio getInstance() {
        return instance;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Login login = Login.getInstance();
        stage = primaryStage;
        goToLogin();
        stage.show();
    }

    public void goToLogin() {
        replaceSceneContent("Login.fxml");
    }

    public void goToPrincipal() {
        replaceSceneContent("Principal.fxml", Configuration.getInstance());
    }

    public void goToCadastroRadialista() {
        replaceSceneContent("cadastroRadialista.fxml");
    }

    public void goToCadastroPrograma() {
        replaceSceneContent("CadastroPrograma.fxml");
    }

    public void goToCadastroCliente() {
        replaceSceneContent("CadastroCliente.fxml");
    }

    public void goToCadastroVinheta() {
        replaceSceneContent("CadastroVinheta.fxml");
    }

    public void goToCadastroPropaganda() {
        replaceSceneContent("CadastroPropaganda.fxml");
    }

    public void goToCadastroHorariosPropaganda() {
        replaceSceneContent("CadastroHorariosPropaganda.fxml");
    }

    private Parent replaceSceneContent(String fxml) {
        try {
            Parent page = (Parent) FXMLLoader.load(BRadio.class.getResource(fxml), null, new JavaFXBuilderFactory());
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

    private Parent replaceSceneContent(String fxml, Configuration conf) {
        try {
            Parent page = (Parent) FXMLLoader.load(BRadio.class.getResource(fxml), null, new JavaFXBuilderFactory());
            stage.close(); //Para nao dar erro no Undecorator na hora de trocar a tela
            stage = new Stage(StageStyle.TRANSPARENT);
            Undecorator undecorator = new Undecorator(stage, (Region) page);
            undecorator.getStylesheets().add("skin/undecorator.css");
            Scene scene = new Scene(undecorator);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);

            //stage.sizeToScene();
            stage.centerOnScreen();

//Configuration
            stage.setWidth(conf.getWidth());
            stage.setHeight(conf.getHeight());
            stage.setFullScreen(conf.isFullScreen());
            System.out.println(conf.isFullScreen());
            stage.show();
            return page;
        } catch (IOException e) {
            GeradorLog.getLoggerFull().severe("FXML: " + fxml + " --- replaceSceneContent: " + e);
            return null;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
