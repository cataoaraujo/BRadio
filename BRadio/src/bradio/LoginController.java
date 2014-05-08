/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bradio;

import Model.DAO.ConnectionFactory;
import Model.DAO.MusicaDAO;
import Model.DAO.ProgramaDAO;
import Model.Musica;
import Model.Programa;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class LoginController implements Initializable {

    @FXML
    ComboBox<Programa> cbProgramas;

    private static Programa programaAtual;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ProgramaDAO pd = new ProgramaDAO(ConnectionFactory.getConnection());
        cbProgramas.getItems().addAll(pd.getAll());
    }

    public static Programa getProgramaAtual() {
        return programaAtual;
    }

    public void iniciar() {
        if (cbProgramas.getSelectionModel().getSelectedItem() != null) {
            programaAtual = cbProgramas.getSelectionModel().getSelectedItem();
            BRadio.getInstance().goToPrincipal();
        } else {
            AlertDialog.show("Escolha um programa antes de iniciar.");
        }
    }

    public void cadastrarRadialista() {
        BRadio.getInstance().goToCadastroRadialista();
    }

    public void cadastrarPrograma() {
        BRadio.getInstance().goToCadastroPrograma();
    }
    
    public void cadastrarCliente(){
        BRadio.getInstance().goToCadastroCliente();
    }
    public void cadastrarPropaganda(){
        BRadio.getInstance().goToCadastroPropaganda();
    }
}
