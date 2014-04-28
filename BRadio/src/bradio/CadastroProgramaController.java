/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bradio;

import Model.DAO.ConnectionFactory;
import Model.DAO.ProgramaDAO;
import Model.DAO.RadialistaDAO;
import Model.Programa;
import Model.Radialista;
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class CadastroProgramaController implements Initializable {

    @FXML
    ComboBox<Radialista> cbRadialistas;
    @FXML
    ListView<Radialista> listaRadialitas;
    @FXML
    TextField edtNome, edtHInicio, edtMInicio, edtSInicio, edtHFim, edtMFim, edtSFim;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbRadialistas.getItems().clear();
        RadialistaDAO rd = new RadialistaDAO(ConnectionFactory.getConnection());
        Collection<Radialista> radialistas = rd.getAll();

        //System.out.println(radialistas);
        cbRadialistas.getItems().addAll(radialistas);
    }

    public void addRadialista() {
        if (cbRadialistas.getSelectionModel().getSelectedItem() != null) {
            Radialista radialista = cbRadialistas.getSelectionModel().getSelectedItem();
            //System.out.println(radialista);
            listaRadialitas.getItems().add(radialista);
            cbRadialistas.getItems().remove(radialista);
        }else{
            AlertDialog.show("Escolhar um radialista para continuar.");
        }
    }

    public void voltar() {
        BRadio.getInstance().goToLogin();
    }

    public void cadastrar() {
        Programa p = new Programa();
        p.setNome(edtNome.getText());
        Time inicio, fim;
        if (edtHInicio.getText().isEmpty() || edtMInicio.getText().isEmpty() || edtSInicio.getText().isEmpty()) {
            inicio = Time.valueOf("00:00:00");
        } else {
            inicio = new Time(Integer.parseInt(edtHInicio.getText()), Integer.parseInt(edtMInicio.getText()), Integer.parseInt(edtSInicio.getText()));
        }
        if (edtHFim.getText().isEmpty() || edtMFim.getText().isEmpty() || edtSFim.getText().isEmpty()) {
            fim = Time.valueOf("00:00:00");
        } else {
            fim = new Time(Integer.parseInt(edtHFim.getText()), Integer.parseInt(edtMFim.getText()), Integer.parseInt(edtSFim.getText()));
        }
        p.setHoraInicio(inicio);
        p.setHorarioFim(fim);
        ArrayList<Radialista> lista = new ArrayList<>(listaRadialitas.getItems());
        p.setRadialistas(lista);
        if (lista.isEmpty() || p.getNome().isEmpty()) {
            //Alert que precisa de Radialista
            AlertDialog.show("Preencha pelo menos os campos: Nome e Adicione algum radialista");
        } else {
            ProgramaDAO pd = new ProgramaDAO(ConnectionFactory.getConnection());
            pd.insert(p);
            voltar();
        }

    }

}
