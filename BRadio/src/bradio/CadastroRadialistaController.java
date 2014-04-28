/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bradio;

/**
 *
 * @author Rodrigo
 */
import Model.DAO.ConnectionFactory;
import Model.DAO.RadialistaDAO;
import Model.Radialista;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class CadastroRadialistaController implements Initializable {

    @FXML
    TextField edtNome;
    @FXML
    TextField edtCPF;
    @FXML
    TextField edtEndereco;
    @FXML
    TextField edtTelefone;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void voltar() {
        BRadio.getInstance().goToLogin();
    }
    
    public void cadastrar() {
        Radialista radialista = new Radialista();
        radialista.setNome(edtNome.getText());
        radialista.setCPF(edtCPF.getText());
        radialista.setEndereco(edtEndereco.getText());
        radialista.setTelefone(edtTelefone.getText());
        RadialistaDAO rd = new RadialistaDAO(ConnectionFactory.getConnection());
        if (radialista.getNome().isEmpty()) {
            AlertDialog.show("Preencha pelo menos o Nome do radialista");
        } else {
            if (rd.insert(radialista)) {
                BRadio.getInstance().goToLogin();
            }
        }
    }

}
