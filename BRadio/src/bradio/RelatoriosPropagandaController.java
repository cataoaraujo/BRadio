/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bradio;

import Model.RelatorioPropagandas;
import Model.TipoRelatorio;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

/**
 *
 * @author MatheuseJessica
 */
public class RelatoriosPropagandaController implements Initializable {

    @FXML
    DatePicker dataInicial, dataFinal;
    @FXML
    ComboBox tipoRelatorio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tipoRelatorio.getItems().add(TipoRelatorio.ENTREDATAS.getNome());
        tipoRelatorio.getItems().add(TipoRelatorio.ENTREDATASSIMPLES.getNome());
        tipoRelatorio.getItems().add(TipoRelatorio.ATIVAS.getNome());
    }

    public void gerar() {
        try {
            RelatorioPropagandas rp = new RelatorioPropagandas();
            if (tipoRelatorio.getSelectionModel().getSelectedItem() != null) {
                if (tipoRelatorio.getSelectionModel().getSelectedItem().equals(TipoRelatorio.ENTREDATAS)) {
                    if (dataInicial.getValue() != null && dataFinal.getValue() != null) {
                        rp.relatorioEntreDatas(dataInicial.getValue(), dataFinal.getValue());
                    } else {
                        AlertDialog.show("Selecione as datas");
                    }
                } else {
                    if (tipoRelatorio.getSelectionModel().getSelectedItem().equals(TipoRelatorio.ENTREDATASSIMPLES)) {
                        if (dataInicial.getValue() != null && dataFinal.getValue() != null) {
                            rp.relatorioDiasSemanaEntreDatas(dataInicial.getValue(), dataFinal.getValue());
                        } else {
                            AlertDialog.show("Selecione as datas");
                        }
                    } else {
                        rp.relatorioDiaSemana();
                    }
                }
            } else {
                AlertDialog.show("Selecione uma opção de reletório");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void voltar() {
        BRadio.getInstance().goToPrincipal();
    }

}
