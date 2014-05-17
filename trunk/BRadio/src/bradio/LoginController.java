/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bradio;

import Model.DAO.ConnectionFactory;
import Model.DAO.MusicaDAO;
import Model.DAO.ProgramaDAO;
import Model.FiltroAudio;
import Model.Musica;
import Model.Programa;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class LoginController implements Initializable {

    @FXML
    ComboBox<Programa> cbProgramas;
    @FXML
    ProgressBar progresso;

    private static Programa programaAtual;
    private MusicaDAO musicaDao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        musicaDao = new MusicaDAO(ConnectionFactory.getConnection());
        ProgramaDAO pd = new ProgramaDAO(ConnectionFactory.getConnection());
        cbProgramas.getItems().addAll(pd.getAll());
    }

    public static Programa getProgramaAtual() {
        return programaAtual;
    }

    public void iniciar() {
        if (cbProgramas.getSelectionModel().getSelectedItem() != null) {
            programaAtual = cbProgramas.getSelectionModel().getSelectedItem();
            BRadio.getInstance().goToPrincipal();
        } else {
            AlertDialog.show("Escolha um programa antes de iniciar.");
        }
    }

    public void cadastrarRadialista() {
        BRadio.getInstance().goToCadastroRadialista();
    }

    public void cadastrarPrograma() {
        BRadio.getInstance().goToCadastroPrograma();
    }

    public void cadastrarCliente() {
        BRadio.getInstance().goToCadastroCliente();
    }

    public void cadastrarPropaganda() {
        BRadio.getInstance().goToCadastroPropaganda();
    }

    public void cadastrarVinheta() {
        BRadio.getInstance().goToCadastroVinheta();
    }

    public void cadastrarMusicas() {
        DirectoryChooser dc = new DirectoryChooser();
        File diretorio = dc.showDialog(new Stage());
        if (diretorio != null) {
            final ArrayList<File> musicas = getMusicas(diretorio);
            //System.out.println(musicas.size());
            new Thread() {
                @Override
                public void run() {
                    int i = musicas.size();
                    int total = musicas.size();
                    int erros = 0;
                    for (File file : musicas) {
                        i--;
                        Musica m = new Musica();
                        m.setArquivo(file);
                        m.getMetadata();
                        if (!musicaDao.insert(m)) {
                            erros++;
                        }
                        progresso.setProgress(1.0 * (total - i) / total);
                    }
                }
            }.start();
        }

    }

    private ArrayList<File> getMusicas(File arquivoOuDiretorio) {
        ArrayList<File> files = new ArrayList<>();
        if (arquivoOuDiretorio.isDirectory()) {
            FiltroAudio ff = new FiltroAudio();
            File[] arquivos = arquivoOuDiretorio.listFiles(ff);
            for (File file : arquivos) {
                files.addAll(getMusicas(file));
            }
        } else {
            files.add(arquivoOuDiretorio);
        }
        return files;
    }
}
