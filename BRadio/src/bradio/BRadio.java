/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bradio;

import Model.Login;
import insidefx.undecorator.Undecorator;
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
        try {
            replaceSceneContent("Login.fxml");
        } catch (Exception ex) {
            System.out.println("erro: ProjetoFinal.goToLogin");
        }
    }

    public void goToPrincipal() {
        try {
            replaceSceneContent("Principal.fxml");
        } catch (Exception ex) {
            System.out.println("erro: ProjetoFinal.goToPrincipal");
        }
    }

    public void goToCadastroRadialista() {
        try {
            replaceSceneContent("cadastroRadialista.fxml");
        } catch (Exception ex) {

        }
    }

    public void goToCadastroPrograma() {
        try {
            replaceSceneContent("CadastroPrograma.fxml");
        } catch (Exception ex) {

        }
    }

    public void goToCadastroCliente() {
        try {
            replaceSceneContent("CadastroCliente.fxml");
        } catch (Exception e) {

        }
    }

    public void goToCadastroVinheta() {
        try {
            replaceSceneContent("CadastroVinheta.fxml");
        } catch (Exception e) {

        }
    }

    public void goToCadastroPropaganda() {
        try {
            replaceSceneContent("CadastroPropaganda.fxml");
        } catch (Exception e) {

        }
    }

    public void goToCadastroHorariosPropaganda() {
        try {
            replaceSceneContent("CadastroHorariosPropaganda.fxml");
        } catch (Exception e) {

        }
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
        } catch (Exception e) {
            System.out.println("Erro: replaceSceneContent: " + e);
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
