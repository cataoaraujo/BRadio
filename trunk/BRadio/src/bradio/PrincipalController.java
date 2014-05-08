/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bradio;

import Model.*;
import Model.DAO.ConnectionFactory;
import Model.DAO.MusicaDAO;
import Model.DAO.VinhetaDAO;
import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
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

    private Player<Musica> playerMusica = new Player<Musica>();
    private Player<Vinheta> playerVinheta = new Player<Vinheta>();

    public ListView<Propaganda> listaPropagandas = new ListView<>();
    public ListView<Musica> listaMusicas = new ListView<>();
    public Label relogio = new Label();
    public Label data = new Label();

    @FXML
    public ListView<Musica> listaPlaylist;
    @FXML
    public TextField codigoSelecionada, tituloSelecionada, artistaSelecionada, albumSelecionada, generoSelecionada;
    @FXML
    public TextField codigoTocando, tituloTocando, artistaTocando, albumTocando, generoTocando;
    @FXML
    public ProgressBar progressoMusica = new ProgressBar(0), progressoVinheta = new ProgressBar(0);

    // Para tocar Vinhetas
    @FXML
    public Button Play1, Play2, Play3, Play4, Play5, Play6;
    @FXML
    public TextField vinheta1, vinheta2, vinheta3, vinheta4, vinheta5, vinheta6;
    @FXML
    public Label ultimaPropVin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerMusica.addProgressBar(progressoMusica);
        Relogio r = new Relogio(relogio, data);
        MusicaDAO md = new MusicaDAO(ConnectionFactory.getConnection());
        listaMusicas.getItems().addAll(md.getAll());

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

    public void selecionaMusica() {
        if (listaMusicas.getSelectionModel().getSelectedItem() != null) {
            Musica m = listaMusicas.getSelectionModel().getSelectedItem();
            codigoSelecionada.setText(m.getCodigo() + "");
            tituloSelecionada.setText(m.getTitulo());
            generoSelecionada.setText(m.getGenero());
            artistaSelecionada.setText(m.getArtista());
            albumSelecionada.setText(m.getAlbum());
        }
    }

    public void addPlaylist() {
        if (listaMusicas.getSelectionModel().getSelectedItem() != null) {
            Musica m = listaMusicas.getSelectionModel().getSelectedItem();
            listaPlaylist.getItems().add(m);
        }
    }

    public void tocarProxima() {
        if (listaPlaylist.getSelectionModel().getSelectedItem() != null) {
            tocarSelecionada();
        } else {
            if (listaPlaylist.getItems().size() > 0) {
                Musica m = listaPlaylist.getItems().get(0);
                listaPlaylist.getItems().remove(m);
                playerMusica.setArquivo(m.getArquivo());
                playerMusica.play();
                setMusicaTocando(m);
            }
        }
    }

    public void tocarSelecionada() {
        if (listaPlaylist.getSelectionModel().getSelectedItem() != null) {
            Musica m = listaPlaylist.getSelectionModel().getSelectedItem();
            listaPlaylist.getItems().remove(listaPlaylist.getSelectionModel().getSelectedIndex());
            playerMusica.setArquivo(m.getArquivo());
            playerMusica.play();
            setMusicaTocando(m);
            listaPlaylist.getSelectionModel().selectFirst();
        }
    }

    public void pararMusica() {
        playerMusica.stop();
    }

    private void setMusicaTocando(Musica m) {
        codigoTocando.setText(m.getCodigo() + "");
        tituloTocando.setText(m.getTitulo());
        generoTocando.setText(m.getGenero());
        artistaTocando.setText(m.getArtista());
        albumTocando.setText(m.getAlbum());

    }

    public void removeMusica() {
        if (listaPlaylist.getSelectionModel().getSelectedItem() != null) {
            Musica m = listaPlaylist.getSelectionModel().getSelectedItem();
            listaPlaylist.getItems().remove(m);
        }
    }

    public void tocarVinheta(ActionEvent ae) {
        Button b = (Button) ae.getSource();
        Vinheta v = new Vinheta();
        if (b.equals(Play1)) {
            v.setNome(vinheta1.getText());
        } else if (b.equals(Play2)) {
            v.setNome(vinheta2.getText());
        } else if (b.equals(Play3)) {
            v.setNome(vinheta3.getText());
        } else if (b.equals(Play4)) {
            v.setNome(vinheta4.getText());
        } else if (b.equals(Play5)) {
            v.setNome(vinheta5.getText());
        } else if (b.equals(Play6)) {
            v.setNome(vinheta6.getText());
        }
        VinhetaDAO vd = new VinhetaDAO(ConnectionFactory.getConnection());
        v = vd.getByNome(v.getNome());
        if (v != null) {
            playerVinheta.setArquivo(v.getArquivo());
            playerVinheta.addProgressBar(progressoVinheta);
            playerVinheta.play();
            ultimaPropVin.setText(v.getNome());
        }

    }
}
