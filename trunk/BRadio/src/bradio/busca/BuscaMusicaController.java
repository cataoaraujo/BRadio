/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bradio.busca;

import Model.DAO.ConnectionFactory;
import Model.DAO.MusicaDAO;
import Model.Musica;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class BuscaMusicaController implements Initializable {

    @FXML
    TableColumn cNome, cArtista, cGenero;
    @FXML
    TableView<Musica> tMusicas;
    @FXML
    ComboBox<String> generos;
    @FXML
    TextField titulo, artista;

    private MusicaDAO md = new MusicaDAO(ConnectionFactory.getConnection());

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cNome.setCellValueFactory(new PropertyValueFactory<Musica, String>("titulo"));
        cArtista.setCellValueFactory(new PropertyValueFactory<Musica, String>("artista"));
        cGenero.setCellValueFactory(new PropertyValueFactory<Musica, String>("genero"));

        tMusicas.setItems(FXCollections.observableArrayList(md.getAll()));
        generos.setItems(FXCollections.observableArrayList(md.getAllGeneros()));
    }

    public void pesquisar() {
        Collection<Musica> musicas = md.getBy(titulo.getText(), artista.getText(), generos.getSelectionModel().getSelectedItem());
        tMusicas.setItems(FXCollections.observableArrayList(musicas));
    }

    public void selecionaMusica() {
        Musica musica = tMusicas.getSelectionModel().getSelectedItem();
        BuscaMusica.setMusicaSelecionada(musica);
        BuscaMusica.close();
    }

    public void fechar() {
        BuscaMusica.close();
    }
}
