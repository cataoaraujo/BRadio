/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bradio;

import Model.DAO.ConnectionFactory;
import Model.DAO.MusicaDAO;
import Model.Musica;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class EditarMusicaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    TableColumn cNome, cArtista, cGenero, cAlbum;
    @FXML
    TableView<Musica> tMusicas;
    @FXML
    ComboBox<String> generos;
    @FXML
    TextField titulo, artista;

    private MusicaDAO md = new MusicaDAO(ConnectionFactory.getConnection());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cNome.setCellFactory(TextFieldTableCell.forTableColumn());
        cNome.setCellValueFactory(new PropertyValueFactory<Musica, String>("titulo"));
        cNome.setOnEditCommit(
                new EventHandler<CellEditEvent<Musica, String>>() {
                    @Override
                    public void handle(CellEditEvent<Musica, String> t) {
                        Musica musica = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
                        musica.setTitulo(t.getNewValue());
                    }
                }
        );
        cArtista.setCellFactory(TextFieldTableCell.forTableColumn());
        cArtista.setCellValueFactory(new PropertyValueFactory<Musica, String>("artista"));
        cArtista.setOnEditCommit(
                new EventHandler<CellEditEvent<Musica, String>>() {
                    @Override
                    public void handle(CellEditEvent<Musica, String> t) {
                        Musica musica = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
                        musica.setArtista(t.getNewValue());
                        md.update(musica);
                    }
                }
        );
        cGenero.setCellFactory(TextFieldTableCell.forTableColumn());
        cGenero.setCellValueFactory(new PropertyValueFactory<Musica, String>("genero"));
        cGenero.setOnEditCommit(
                new EventHandler<CellEditEvent<Musica, String>>() {
                    @Override
                    public void handle(CellEditEvent<Musica, String> t) {
                        Musica musica = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
                        musica.setGenero(t.getNewValue());
                        md.update(musica);
                    }
                }
        );
        cAlbum.setCellFactory(TextFieldTableCell.forTableColumn());
        cAlbum.setCellValueFactory(new PropertyValueFactory<Musica, String>("album"));
        cAlbum.setOnEditCommit(
                new EventHandler<CellEditEvent<Musica, String>>() {
                    @Override
                    public void handle(CellEditEvent<Musica, String> t) {
                        Musica musica = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
                        musica.setAlbum(t.getNewValue());
                        md.update(musica);
                    }
                }
        );

        tMusicas.setItems(FXCollections.observableArrayList(md.getAll()));
        generos.setItems(FXCollections.observableArrayList(md.getAllGeneros()));
    }

    public void pesquisar() {
        Collection<Musica> musicas = md.getBy(titulo.getText(), artista.getText(), generos.getSelectionModel().getSelectedItem());
        tMusicas.setItems(FXCollections.observableArrayList(musicas));
    }

    public void salvar(CellEditEvent<Musica, String> e) {
       BRadio.getInstance().goToLogin();
    }

    public void fechar() {
        BRadio.getInstance().goToLogin();
    }

}
