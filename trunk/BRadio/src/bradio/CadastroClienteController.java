/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bradio;

import Model.Cliente;
import Model.DAO.ClienteDAO;
import Model.DAO.ConnectionFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 *
 * @author MatheuseJessica
 */
public class CadastroClienteController implements Initializable{
    @FXML
    TextField edtNome, edtRazao, edtCnpj, edtTelefone, edtEndereco;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    public void voltar(){
        BRadio.getInstance().goToLogin();
    }
    public void cadastrar(){
        Cliente cliente = new Cliente();
        cliente.setNomeFantasia(edtNome.getText());
        cliente.setRazaoSocial(edtRazao.getText());
        cliente.setCNPJ(edtCnpj.getText());
        cliente.setTelefone(edtTelefone.getText());
        cliente.setEndereco(edtEndereco.getText());
        ClienteDAO cd = new ClienteDAO(ConnectionFactory.getConnection());
        cd.insert(cliente);
        BRadio.getInstance().goToLogin();
    }
}
