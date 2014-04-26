/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.DAO;

import Model.Musica;
import Model.Radialista;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Rodrigo
 */
public class RadialistaDAO extends GenericDAO<Radialista>{

    public RadialistaDAO(Connection conn) {
        super(conn);
    }

    @Override
    public boolean insert(Radialista o) {
         String sqlInsert = "INSERT INTO TB_RADIALISTA(RAD_NOME, RAD_CPF, RAD_ENDERECO, RAD_TELEFONE) VALUES (?,?,?,?)";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlInsert, java.sql.Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, o.getNome());
            pStatement.setString(2, o.getCPF());
            pStatement.setString(3, o.getEndereco());
            pStatement.setString(4, o.getTelefone());
            
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
    public boolean update(Radialista o) {
        String sqlInsert = "UPDATE TB_RADIALISTA SET RAD_NOME=?, RAD_CPF=?, RAD_ENDERECO=?, RAD_TELEFONE=? WHERE RAD_CODIGO = ?";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlInsert);
            pStatement.setString(1, o.getNome());
            pStatement.setString(2, o.getCPF());
            pStatement.setString(3, o.getEndereco());
            pStatement.setString(4, o.getTelefone());
            pStatement.setInt(5, o.getCodigo());
            
            if (pStatement.executeUpdate() > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Radialista o) {
        String sqlInsert = "DELETE FROM TB_RADIALISTA WHERE RAD_CODIGO = ?";
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
    public Collection<Radialista> getAll() {
        String sqlGetAll = "SELECT * FROM TB_RADIALISTA ORDER BY RAD_NOME";
        Collection<Radialista> radialistas = new ArrayList<>();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlGetAll);
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Radialista radialista = new Radialista();
                radialista.setCodigo(rs.getInt("RAD_CODIGO"));
                radialista.setCPF(rs.getString("RAD_CPF"));
                radialista.setEndereco(rs.getString("RAD_ENDERECO"));
                radialista.setInicio(rs.getDate("RAD_DATAINICIO"));
                radialista.setTelefone(rs.getString("RAD_TELEFONE"));
                radialistas.add(radialista);
            }
        } catch (Exception e) {
            //who cares?
        }
        return radialistas;
    }
    
    public Radialista getByCodigo(int codigo) {
        String sqlGetAll = "SELECT * FROM TB_RADIALISTA WHERE RAD_CODIGO = ?";
        Radialista radialista = null;
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlGetAll);
            pStatement.setInt(1, codigo);
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                radialista = new Radialista();
                radialista.setCodigo(rs.getInt("RAD_CODIGO"));
                radialista.setCPF(rs.getString("RAD_CPF"));
                radialista.setEndereco(rs.getString("RAD_ENDERECO"));
                radialista.setInicio(rs.getDate("RAD_DATAINICIO"));
                radialista.setTelefone(rs.getString("RAD_TELEFONE"));
            }
        } catch (Exception e) {
            //who cares?
        }
        return radialista;
    }
    
}
