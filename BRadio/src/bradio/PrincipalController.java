/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bradio;

import Model.*;
import Model.DAO.ConnectionFactory;
import Model.DAO.MusicaDAO;
import Model.DAO.PropagandaDAO;
import Model.DAO.VinhetaDAO;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 *
 * @author Rodrigo
 */
public class PrincipalController implements Initializable {

    private Player<Propaganda> playerPropaganda = new Player<>();
    private Player<Musica> playerMusica = new Player<>();
    private Player<Vinheta> playerVinheta = new Player<>();

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

    private Collection<Propaganda> propagandas;

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
        PropagandaDAO pd = new PropagandaDAO(ConnectionFactory.getConnection());
        propagandas = pd.getByDia(Date.valueOf(LocalDate.now()));
        listaPropagandas.setItems(FXCollections.observableArrayList(propagandas));

        Timeline timeline = new Timeline(new KeyFrame(Duration.minutes(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                atualizaPropagadas();
                System.out.println("Atualizou!");
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void atualizaPropagadas() {
        listaPropagandas.setCellFactory(new Callback<ListView<Propaganda>, ListCell<Propaganda>>() {

            @Override
            public ListCell<Propaganda> call(ListView<Propaganda> param) {
                return new ListCellPropaganda();
            }
        });
        listaPropagandas.setItems(FXCollections.observableArrayList(propagandas));
        listaPropagandas.getSelectionModel().clearSelection();
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

    public void tocarPropaganda() {
        Propaganda p = listaPropagandas.getSelectionModel().getSelectedItem();
        if (p != null) {
            playerPropaganda.setArquivo(p.getArquivo());
            playerPropaganda.addProgressBar(progressoVinheta);
            playerPropaganda.play();
            ultimaPropVin.setText(p.getNome());
            PropagandaDAO pd = new PropagandaDAO(ConnectionFactory.getConnection());
            pd.tocou(p);
            propagandas.remove(p);
            atualizaPropagadas();
        }
    }
}
