/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.DAO;

import Model.Musica;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Rodrigo
 */
public class MusicaDAO extends GenericDAO<Musica>{

    public MusicaDAO(Connection conn) {
        super(conn);
    }

    @Override
    public boolean insert(Musica o) {
        String sqlInsert = "INSERT INTO TB_MUSICA(MUS_TITULO, MUS_ARTISTA, MUS_GENERO, MUS_ALBUM, MUS_ARQUIVO) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlInsert, java.sql.Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, o.getTitulo());
            pStatement.setString(2, o.getArtista());
            pStatement.setString(3, o.getGenero());
            pStatement.setString(4, o.getAlbum());
            pStatement.setString(5, o.getArquivo().getAbsolutePath());
            
            if (pStatement.executeUpdate() > 0) {
                ResultSet rs = pStatement.getGeneratedKeys();
                if (rs.next()) {
                    o.setCodigo(rs.getInt(1));
                }
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Musica o) {
        String sqlInsert = "UPDATE TB_MUSICA SET MUS_TITULO=?, MUS_ARTISTA=?, MUS_GENERO=?, MUS_ALBUM=?, MUS_ARQUIVO=? WHERE MUS_CODIGO = "+o.getCodigo();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlInsert);
            pStatement.setString(1, o.getTitulo());
            pStatement.setString(2, o.getArtista());
            pStatement.setString(3, o.getGenero());
            pStatement.setString(4, o.getAlbum());
            pStatement.setString(5, o.getArquivo().getAbsolutePath());
            
            if (pStatement.executeUpdate() > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Musica o) {
        String sqlInsert = "DELETE FROM TB_MUSICA WHERE MUS_CODIGO = ?";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlInsert);     
            pStatement.setInt(1, o.getCodigo());
            if (pStatement.executeUpdate() > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Collection<Musica> getAll() {
        String sqlGetAll = "SELECT * FROM TB_MUSICA ORDER BY MUS_TITULO";
        Collection<Musica> musicas = new ArrayList<>();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlGetAll);
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Musica musica = new Musica();
                musica.setCodigo(rs.getInt("MUS_CODIGO"));
                musica.setTitulo(rs.getString("MUS_TITULO"));
                musica.setGenero(rs.getString("MUS_GENERO"));
                musica.setArtista(rs.getString("MUS_ARTISTA"));
                musica.setAlbum(rs.getString("MUS_ALBUM"));
                musica.setArquivo(new File(rs.getString("MUS_ARQUIVO")));
                musicas.add(musica);
            }
        } catch (Exception e) {
            //who cares?
        }
        return musicas;
    }
    
    public Musica getByCodigo(int codigo) {
        String sqlGetAll = "SELECT * FROM TB_MUSICA WHERE MUS_CODIGO = ?";
        Musica musica = null;
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlGetAll);
            pStatement.setInt(1, codigo);
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                musica = new Musica();
                musica.setCodigo(rs.getInt("MUS_CODIGO"));
                musica.setTitulo(rs.getString("MUS_TITULO"));
                musica.setGenero(rs.getString("MUS_GENERO"));
                musica.setArtista(rs.getString("MUS_ARTISTA"));
                musica.setAlbum(rs.getString("MUS_ALBUM"));
                musica.setArquivo(new File(rs.getString("MUS_ARQUIVO")));
            }
        } catch (Exception e) {
            //who cares?
        }
        return musica;
    }
    
    public Collection<Musica> getByTitulo(String titulo) {
        String sqlGetAll = "SELECT * FROM TB_MUSICA WHERE MUS_TITULO LIKE ?";
        Collection<Musica> musicas = new ArrayList<>();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlGetAll);
            pStatement.setString(1, "%"+titulo+"%");
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Musica musica = new Musica();
                musica.setCodigo(rs.getInt("MUS_CODIGO"));
                musica.setTitulo(rs.getString("MUS_TITULO"));
                musica.setGenero(rs.getString("MUS_GENERO"));
                musica.setArtista(rs.getString("MUS_ARTISTA"));
                musica.setAlbum(rs.getString("MUS_ALBUM"));
                musica.setArquivo(new File(rs.getString("MUS_ARQUIVO")));
                musicas.add(musica);
            }
        } catch (Exception e) {
            //who cares?
        }
        return musicas;
    }
    
    public Collection<Musica> getByGenero(String genero) {
        String sqlGetAll = "SELECT * FROM TB_MUSICA WHERE MUS_GENERO LIKE ?";
        Collection<Musica> musicas = new ArrayList<>();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlGetAll);
            pStatement.setString(1, "%"+genero+"%");
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Musica musica = new Musica();
                musica.setCodigo(rs.getInt("MUS_CODIGO"));
                musica.setTitulo(rs.getString("MUS_TITULO"));
                musica.setGenero(rs.getString("MUS_GENERO"));
                musica.setArtista(rs.getString("MUS_ARTISTA"));
                musica.setAlbum(rs.getString("MUS_ALBUM"));
                musica.setArquivo(new File(rs.getString("MUS_ARQUIVO")));
                musicas.add(musica);
            }
        } catch (Exception e) {
            //who cares?
        }
        return musicas;
    }
    
    public Collection<Musica> getByArtista(String artista) {
        String sqlGetAll = "SELECT * FROM TB_MUSICA WHERE MUS_ARTISTA LIKE ?";
        Collection<Musica> musicas = new ArrayList<>();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlGetAll);
            pStatement.setString(1, "%"+artista+"%");
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Musica musica = new Musica();
                musica.setCodigo(rs.getInt("MUS_CODIGO"));
                musica.setTitulo(rs.getString("MUS_TITULO"));
                musica.setGenero(rs.getString("MUS_GENERO"));
                musica.setArtista(rs.getString("MUS_ARTISTA"));
                musica.setAlbum(rs.getString("MUS_ALBUM"));
                musica.setArquivo(new File(rs.getString("MUS_ARQUIVO")));
                musicas.add(musica);
            }
        } catch (Exception e) {
            //who cares?
        }
        return musicas;
    }
    
    public Collection<Musica> getByAlbum(String album) {
        String sqlGetAll = "SELECT * FROM TB_MUSICA WHERE MUS_TITULO LIKE ?";
        Collection<Musica> musicas = new ArrayList<>();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlGetAll);
            pStatement.setString(1, "%"+album+"%");
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Musica musica = new Musica();
                musica.setCodigo(rs.getInt("MUS_CODIGO"));
                musica.setTitulo(rs.getString("MUS_TITULO"));
                musica.setGenero(rs.getString("MUS_GENERO"));
                musica.setArtista(rs.getString("MUS_ARTISTA"));
                musica.setAlbum(rs.getString("MUS_ALBUM"));
                musica.setArquivo(new File(rs.getString("MUS_ARQUIVO")));
                musicas.add(musica);
            }
        } catch (Exception e) {
            //who cares?
        }
        return musicas;
    }
    
}
