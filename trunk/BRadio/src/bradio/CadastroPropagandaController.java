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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    TableView tabelaHorarios;
    @FXML
    DatePicker dataInicio, dataFim;
    @FXML
    VBox vbDias;
    private File file = null;

    private ArrayList<CheckBox> dias = new ArrayList<CheckBox>();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbClientes.getItems().clear();
        ClienteDAO cd = new ClienteDAO(ConnectionFactory.getConnection());
        Collection<Cliente> clientes = cd.getAll();
        cbClientes.getItems().addAll(clientes);

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

    private void criaDiaSemana(String nome, String id) {
        HBox dia = new HBox();
        CheckBox cDia = new CheckBox(nome);
        cDia.setId(id);
        dia.getChildren().add(cDia);
        vbDias.getChildren().add(dia);
        dias.add(cDia);
    }

    public void addHorarios() {
        LocalDate inicio = dataInicio.getValue();
        LocalDate fim = dataFim.getValue();
        vbDias.getChildren().clear();
        LocalDate data = inicio;
        if (ChronoUnit.DAYS.between(inicio, fim) >= 7) {
            criaDiaSemana(inicio.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()), inicio.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));
            for (int i = 0; i < 7; i++) {
                data = data.plus(1, ChronoUnit.DAYS);
                criaDiaSemana(data.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()), data.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));
            }
        }else{
            
            criaDiaSemana(inicio.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()), inicio.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));
            for (int i = 0; i < ChronoUnit.DAYS.between(inicio, fim); i++) {
                data = data.plus(1, ChronoUnit.DAYS);
                criaDiaSemana(data.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()), data.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));
            }
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

                    /*PropagandaDAO pd = new PropagandaDAO(ConnectionFactory.getConnection());
                     if(pd.insert(propaganda)){
                     BRadio.getInstance().goToLogin();
                     }else{
                     System.out.println("erro");
                     }  */
                }
            }
        }
    }

}
