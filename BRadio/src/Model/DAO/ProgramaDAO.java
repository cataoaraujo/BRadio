/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Logger.GeradorLog;
import Model.Programa;
import Model.Radialista;
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
public class ProgramaDAO extends GenericDAO<Programa> {

    public ProgramaDAO(Connection conn) {
        super(conn);
    }

    @Override
    public boolean insert(Programa o) {
        String sqlInsert = "INSERT INTO tb_programa(PROG_NOME, PROG_HORARIOINICIO, PROG_HORARIOFIM) VALUES (?,?,?)";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlInsert, java.sql.Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, o.getNome());
            pStatement.setTime(2, o.getHorarioInicio());
            pStatement.setTime(3, o.getHorarioFim());
            if (pStatement.executeUpdate() > 0) {
                ResultSet rs = pStatement.getGeneratedKeys();
                if (rs.next()) {
                    o.setCodigo(rs.getInt(1));
                    sqlInsert = "INSERT INTO tb_radialistaprograma(RAP_CODRADIALISTA, RAP_CODPROGRAMA) VALUES (?,?)";
                    PreparedStatement ps = conn.prepareStatement(sqlInsert, java.sql.Statement.RETURN_GENERATED_KEYS);
                    ArrayList<Radialista> rads = o.getRadialistas();
                    for (Radialista radialista : rads) {
                        ps.setInt(1, radialista.getCodigo());
                        ps.setInt(2, o.getCodigo());
                        if (ps.executeUpdate() > 0) {
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            GeradorLog.getLoggerFull().severe(e.toString());
        }
        return false;
    }

    @Override
    public boolean update(Programa o) {
        String sqlUpdate = "UPDATE tb_programa SET PROG_NOME=?, PROG_HORARIOINICIO=?, PROG_HORARIOFIM=? WHERE PROG_CODIGO=" + o.getCodigo();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlUpdate);
            pStatement.setString(1, o.getNome());
            pStatement.setTime(2, o.getHorarioInicio());
            pStatement.setTime(3, o.getHorarioFim());
            if (pStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            GeradorLog.getLoggerFull().severe(e.toString());
        }
        return false;
    }

    @Override
    public boolean delete(Programa o) {
        String sql = "DELETE FROM tb_radialistaprograma WHERE ID=" + o.getCodigo();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            if (pStatement.executeUpdate() > 0) {
                sql = "DELETE FROM tb_programa WHERE ID=" + o.getCodigo();
                PreparedStatement ps = conn.prepareStatement(sql);
                if (pStatement.executeUpdate() > 0) {
                    return true;
                }
            }
            return true;
        } catch (SQLException e) {
            GeradorLog.getLoggerFull().severe(e.toString());
        }
        return false;
    }

    @Override
    public Collection<Programa> getAll() {
        String sql = "SELECT * FROM tb_programa";
        Collection<Programa> programas = new ArrayList<>();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Programa programa = new Programa();
                programa.setCodigo(rs.getInt("PROG_CODIGO"));
                programa.setNome(rs.getString("PROG_NOME"));
                programa.setHoraInicio(rs.getTime("PROG_HORARIOINICIO"));
                programa.setHorarioFim(rs.getTime("PROG_HORARIOFIM"));
                programa.setRadialistas(getRadialistas(programa));
                programas.add(programa);
            }
            return programas;
        } catch (SQLException e) {
            GeradorLog.getLoggerFull().severe(e.toString());
        }
        return null;
    }

    public ArrayList<Radialista> getRadialistas(Programa o) {
        ArrayList<Radialista> radialistas = new ArrayList<>();
        String sql = "SELECT RAP_CODRADIALISTA FROM tb_radialista, tb_programa, tb_radialistaprograma WHERE PROG_CODIGO = " + o.getCodigo() + " AND RAP_CODPROGRAMA = PROG_CODIGO AND RAP_CODRADIALISTA = RAD_CODIGO";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            ResultSet rs = pStatement.executeQuery();
            RadialistaDAO rad = new RadialistaDAO(conn);
            while (rs.next()) {
                Radialista radialista = rad.getByCodigo(rs.getInt("RAP_CODRADIALISTA"));
                radialistas.add(radialista);
            }
        } catch (SQLException e) {
            GeradorLog.getLoggerFull().severe(e.toString());
        }
        return radialistas;
    }

    public Programa getByCodigo(int codigo) {
        String sql = "SELECT * FROM tb_programa WHERE PROG_CODIGO=" + codigo;
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Programa programa = new Programa();
                programa.setCodigo(rs.getInt("PROG_CODIGO"));
                programa.setNome(rs.getString("PROG_NOME"));
                programa.setHoraInicio(rs.getTime("PROG_HORARIOINICIO"));
                programa.setHorarioFim(rs.getTime("PROG_HORARIOFIM"));
                programa.setRadialistas(getRadialistas(programa));
                return programa;
            }
        } catch (SQLException e) {
            GeradorLog.getLoggerFull().severe(e.toString());
        }
        return null;
    }

}
