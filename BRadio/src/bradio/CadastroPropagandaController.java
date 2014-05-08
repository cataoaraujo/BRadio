/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bradio;

import Model.Cliente;
import Model.DAO.ClienteDAO;
import Model.DAO.ConnectionFactory;
import Model.DAO.PropagandaDAO;
import Model.Propaganda;
import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFileChooser;

/**
 *
 * @author MatheuseJessica
 */
public class CadastroPropagandaController implements Initializable {

    @FXML
    TextField edtAbrir, edtNome;
    @FXML
    ComboBox<Cliente> cbClientes;
    @FXML
    ListView<String> listaDias;
    @FXML
    TableView tabelaHorarios;
    
    private File file = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbClientes.getItems().clear();
        ClienteDAO cd = new ClienteDAO(ConnectionFactory.getConnection());
        Collection<Cliente> clientes = cd.getAll();
        cbClientes.getItems().addAll(clientes);
        
        ObservableList<String> itens = FXCollections.observableArrayList("Segunda-Feira", "Terça-Feira", 
                "Quarta-Feira", "Quinta-Feira", "Sexta-Feira", "Sábado", "Domingo");
        listaDias.getItems().addAll(itens);
        
        TableColumn horario = new TableColumn("Horário");
        horario.setEditable(false);
        TableColumn propaganda = new TableColumn("Propaganda");
        propaganda.setMinWidth(190);
        tabelaHorarios.getColumns().clear();
        tabelaHorarios.getColumns().addAll(horario, propaganda);
    }

    public void voltar() {
        BRadio.getInstance().goToLogin();
    }

    public void abrir() {
        FileChooser fc = new FileChooser();
        file = fc.showOpenDialog(new Stage());
        if (file != null) {
            edtAbrir.setText(file.toString());
        }
    }

    public void cadastrar() {
        Propaganda propaganda = new Propaganda();
        if (cbClientes.getSelectionModel().getSelectedItem() != null) {
            if (edtAbrir.getText() != null) {
                if (file != null) {
                    propaganda.setCliente(cbClientes.getSelectionModel().getSelectedItem());
                    propaganda.setArquivo(file);
                    propaganda.setNome(edtNome.getText());
                    PropagandaDAO pd = new PropagandaDAO(ConnectionFactory.getConnection());
                    if(pd.insert(propaganda)){
                        BRadio.getInstance().goToLogin();
                    }else{
                        System.out.println("erro");
                    }                 
                }
            }
        }
    }

}
