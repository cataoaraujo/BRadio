/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bradio;

import Model.*;
import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;
import javafx.event.EventType;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 *
 * @author Rodrigo
 */
class ListCellPropaganda extends ListCell<Propaganda> {

    @Override
    public void updateItem(Propaganda item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            if (item.getNome().equals("ATENÇÃO LOCUTOR")) {
                this.setStyle("-fx-background-color: #f00;");
                this.setText(item.toString());
            } else {
                this.setStyle("-fx-background-color: #00a;");
                this.setText(item.toString());
            }
        }
    }
}

public class PrincipalController implements Initializable {

    public ListView<Propaganda> listaPropagandas = new ListView<Propaganda>();
    public Label relogio = new Label();
    public Label data = new Label();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Relogio r = new Relogio(relogio,data);
        // TODO
        listaPropagandas.setCellFactory(new Callback<ListView<Propaganda>, ListCell<Propaganda>>() {

            @Override
            public ListCell<Propaganda> call(ListView<Propaganda> param) {
                return new ListCellPropaganda();
            }
        });
        listaPropagandas.getItems().add(new Propaganda("ATENÇÃO LOCUTOR", new Time(06, 15, 00)));
        listaPropagandas.getItems().add(new Propaganda("ATENÇÃO LOCUTOR", new Time(06, 30, 00)));
        listaPropagandas.getItems().add(new Propaganda("ATENÇÃO LOCUTOR", new Time(06, 40, 00)));
        listaPropagandas.getItems().add(new Propaganda("ATENÇÃO LOCUTOR", new Time(06, 50, 00)));
        listaPropagandas.getItems().add(new Propaganda("IDENTIFICAÇÃO", new Time(06, 58, 00)));
        listaPropagandas.getItems().add(new Propaganda("HORA CERTA", new Time(06, 59, 00)));
        listaPropagandas.getItems().add(new Propaganda("TEMPERATURA", new Time(07, 35, 00)));
        listaPropagandas.getItems().add(new Propaganda("BOM DIA", new Time(07, 35, 00)));
        listaPropagandas.getItems().add(new Propaganda("FARMACENTER", new Time(07, 35, 00)));
        listaPropagandas.getItems().add(new Propaganda("PRONATEC", new Time(07, 35, 00)));
        listaPropagandas.getItems().add(new Propaganda("SANTA TEREZINHA", new Time(07, 35, 00)));
        listaPropagandas.getItems().add(new Propaganda("DENGUE", new Time(07, 35, 00)));
        listaPropagandas.getItems().add(new Propaganda("ABERT", new Time(07, 35, 00)));
        listaPropagandas.getItems().add(new Propaganda("PERNAMBUCANAS", new Time(07, 35, 00)));
        listaPropagandas.getItems().add(new Propaganda("PAN CENTER", new Time(07, 35, 00)));
        listaPropagandas.getItems().add(new Propaganda("EPT", new Time(07, 35, 00)));
        listaPropagandas.getItems().add(new Propaganda("ANUNCIE", new Time(07, 35, 00)));
        listaPropagandas.getItems().add(new Propaganda("EDUCAÇÃO", new Time(07, 35, 00)));
        listaPropagandas.getItems().add(new Propaganda("MUITAS OUTRA PROPAGANDAS", new Time(07, 35, 00)));
    }

}
