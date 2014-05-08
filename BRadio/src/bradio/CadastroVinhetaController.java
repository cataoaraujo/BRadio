/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bradio;

import Model.DAO.ConnectionFactory;
import Model.DAO.VinhetaDAO;
import Model.Vinheta;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 *
 * @author Rodrigo
 */
public class CadastroVinhetaController implements Initializable {

    @FXML
    Button btArquivo;
    @FXML
    TextField edtNome;

    File arquivo = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void escolherArquivo() {
        FileChooser fc = new FileChooser();
        fc.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Audio", "mp3", "wav"));
        arquivo = fc.showOpenDialog(null);
        if (arquivo != null) {
            btArquivo.setText(arquivo.getName());
        }
    }

    public void voltar() {
        BRadio.getInstance().goToLogin();
    }

    public void cadastrar() {
        if(arquivo!=null){
            Vinheta vinheta = new Vinheta();
            vinheta.setNome(edtNome.getText());
            vinheta.setArquivo(arquivo);
            VinhetaDAO vd = new VinhetaDAO(ConnectionFactory.getConnection());
            vd.insert(vinheta);
        }else{
            AlertDialog.show("Selecione um Arquivo de audio antes");
        }
        BRadio.getInstance().goToLogin();
    }

}
