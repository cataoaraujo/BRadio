/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Logger.GeradorLog;
import Model.Vinheta;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Rodrigo
 */
public class VinhetaDAO extends GenericDAO<Vinheta> {

    public VinhetaDAO(Connection conn) {
        super(conn);
    }

    @Override
    public boolean insert(Vinheta o) {
        String sqlInsert = "INSERT INTO TB_VINHETA(VIN_NOME, VIN_ARQUIVO) VALUES (?,?)";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlInsert, java.sql.Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, o.getNome());
            pStatement.setString(2, o.getArquivo().getAbsolutePath());
            if (pStatement.executeUpdate() > 0) {
                ResultSet rs = pStatement.getGeneratedKeys();
                if (rs.next()) {
                    o.setCodigo(rs.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            GeradorLog.getLoggerFull().severe(e.toString());
        }
        return false;
    }

    @Override
    public boolean update(Vinheta o) {
        String sqlInsert = "UPDATE TB_VINHETA SET VIN_NOME=?, VIN_ARQUIVO=? WHERE VIN_CODIGO=?";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlInsert);
            pStatement.setString(1, o.getNome());
            pStatement.setString(2, o.getArquivo().getAbsolutePath());
            pStatement.setInt(3, o.getCodigo());
            if (pStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            GeradorLog.getLoggerFull().severe(e.toString());
        }
        return false;
    }

    @Override
    public boolean delete(Vinheta o) {
        String sqlInsert = "DELETE FROM TB_VINHETA WHERE VIN_CODIGO=?";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlInsert);
            pStatement.setInt(1, o.getCodigo());
            if (pStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            GeradorLog.getLoggerFull().severe(e.toString());
        }
        return false;
    }

    @Override
    public Collection<Vinheta> getAll() {
        String sqlGetAll = "SELECT * FROM TB_VINHETA";
        Collection<Vinheta> vinhetas = new ArrayList<>();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlGetAll);
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Vinheta vinheta = new Vinheta();
                vinheta.setCodigo(rs.getInt("VIN_CODIGO"));
                vinheta.setNome(rs.getString("VIN_NOME"));
                vinheta.setArquivo(new File(rs.getString("VIN_ARQUIVO")));
                vinhetas.add(vinheta);
            }
        } catch (SQLException e) {
            //who cares?
            GeradorLog.getLoggerFull().severe(e.toString());
        }
        return vinhetas;
    }

    public Vinheta getByCodigo(int codigo) {
        String sqlGetAll = "SELECT * FROM TB_VINHETA WHERE VIN_CODIGO=?";
        Vinheta vinheta = null;
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlGetAll);
            pStatement.setInt(1, codigo);
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                vinheta = new Vinheta();
                vinheta.setCodigo(rs.getInt("VIN_CODIGO"));
                vinheta.setNome(rs.getString("VIN_NOME"));
                vinheta.setArquivo(new File(rs.getString("VIN_ARQUIVO")));
            }
        } catch (SQLException e) {
            //who cares?
            GeradorLog.getLoggerFull().severe(e.toString());
        }
        return vinheta;
    }

    public Vinheta getByNome(String nome) {
        String sqlGetAll = "SELECT * FROM TB_VINHETA WHERE VIN_NOME=?";
        Vinheta vinheta = null;
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlGetAll);
            pStatement.setString(1, nome);
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                vinheta = new Vinheta();
                vinheta.setCodigo(rs.getInt("VIN_CODIGO"));
                vinheta.setNome(rs.getString("VIN_NOME"));
                vinheta.setArquivo(new File(rs.getString("VIN_ARQUIVO")));
            }
        } catch (SQLException e) {
            //who cares?
            GeradorLog.getLoggerFull().severe(e.toString());
        }
        return vinheta;
    }

}
