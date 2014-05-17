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
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author MatheuseJessica
 */
public class CadastroPropagandaController implements Initializable {

    @FXML
    TextField edtAbrir, edtNome;
    @FXML
    ComboBox<Cliente> cbClientes;
    //@FXML
    //TableView tabelaHorarios;
    @FXML
    ListView<LocalTime> listaHorarios;
    @FXML
    DatePicker dataInicio, dataFim;
    @FXML
    VBox vbDias;
    private File file = null;

    LocalDate inicio = LocalDate.now();
    LocalDate fim = LocalDate.now();
    private ArrayList<CheckBox> dias = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbClientes.getItems().clear();
        ClienteDAO cd = new ClienteDAO(ConnectionFactory.getConnection());
        Collection<Cliente> clientes = cd.getAll();
        cbClientes.getItems().addAll(clientes);
        listaHorarios.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        listaHorarios.getItems().addAll(getHorarios());
    }

    private ArrayList<LocalTime> getHorarios() {
        ArrayList<LocalTime> horarios = new ArrayList<>();
        LocalTime inicia = LocalTime.parse("00:00:00");
        LocalTime termina = LocalTime.parse("23:45:00");
        horarios.add(inicia);
        while (inicia.isBefore(termina)) {
            inicia = inicia.plusMinutes(15);
            horarios.add(inicia);
        }
        return horarios;
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

    private void criaDiaSemana(String nome, int id) {
        HBox dia = new HBox();
        CheckBox cDia = new CheckBox(nome);
        cDia.setId(id + "");
        dia.getChildren().add(cDia);
        vbDias.getChildren().add(dia);
        dias.add(cDia);
    }

    public void addHorarios() {
        if (dataInicio.getValue() != null && dataFim.getValue() != null) {
            inicio = dataInicio.getValue();
            fim = dataFim.getValue();
            vbDias.getChildren().clear();
            LocalDate data = inicio;
            
            criaDiaSemana(inicio.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()), inicio.getDayOfWeek().getValue());
            long diferenca = ChronoUnit.DAYS.between(inicio, fim);
            for (int i = 0; i < diferenca && i < 6; i++) {
                data = data.plus(1, ChronoUnit.DAYS);
                criaDiaSemana(data.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()), data.getDayOfWeek().getValue());
            }

        }
    }

    private boolean verificaDia(int dia) {
        for (CheckBox d : dias) {
            if (d.getId().equals(dia + "")) {
                return d.isSelected();
            }
        }
        return false;
    }

    public void cadastrar() {
        Propaganda propaganda = new Propaganda();
        if (cbClientes.getSelectionModel().getSelectedItem() != null && edtAbrir.getText() != null && file != null) {

            propaganda.setCliente(cbClientes.getSelectionModel().getSelectedItem());
            propaganda.setArquivo(file);
            propaganda.setNome(edtNome.getText());

            PropagandaDAO pd = new PropagandaDAO(ConnectionFactory.getConnection());

            pd.insert(propaganda);

            long diferenca = ChronoUnit.DAYS.between(inicio, fim);
            LocalDate data = inicio;
            for (int i = 0; i < diferenca + 1; i++) {
                if (verificaDia(data.getDayOfWeek().getValue())) {
                    propaganda.setData(data);
                    for (LocalTime hora : listaHorarios.getSelectionModel().getSelectedItems()) {
                        propaganda.setHora(hora);

                        pd.insertNewHorario(propaganda);
                    }
                }
                data = data.plus(1, ChronoUnit.DAYS);
            }
            BRadio.getInstance().goToLogin();
        }

    }

}
