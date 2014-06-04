/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Logger.GeradorLog;
import Model.Propaganda;
import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Rodrigo
 */
public class PropagandaDAO extends GenericDAO<Propaganda> {

    public PropagandaDAO(Connection conn) {
        super(conn);
    }

    @Override
    public boolean insert(Propaganda o) {
        String sqlInsert = "INSERT INTO TB_PROPAGANDA(PRO_CODCLIENTE, PRO_NOME, PRO_ARQUIVO) VALUES (?,?,?)";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlInsert, java.sql.Statement.RETURN_GENERATED_KEYS);
            pStatement.setInt(1, o.getCliente().getCodigo());
            pStatement.setString(2, o.getNome());
            pStatement.setString(3, o.getArquivo().getAbsolutePath());
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

    public boolean insertNewHorario(Propaganda o) {
        String sqlInsert = "INSERT INTO TB_DIASPROPAGANDAS(DIP_CODPROPAGANDA,DIP_DATA,DIP_HORARIOPREVISTO) VALUES (?, ?, ?)";
        try {
            PreparedStatement pS = conn.prepareStatement(sqlInsert, java.sql.Statement.RETURN_GENERATED_KEYS);
            pS.setInt(1, o.getCodigo());
            pS.setDate(2, Date.valueOf(o.getData()));
            pS.setTime(3, Time.valueOf(o.getHora()));
            if (pS.executeUpdate() > 0) {
                return true;
            }

        } catch (SQLException e) {
            GeradorLog.getLoggerFull().severe(e.toString());
        }
        return false;
    }

    @Override
    public boolean update(Propaganda o) {
        String sqlInsert = "UPDATE TB_PROPAGANDA SET PRO_CODCLIENTE=?, PRO_NOME=?, PRO_ARQUIVO=? WHERE PRO_CODIGO=?";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlInsert);
            pStatement.setInt(1, o.getCliente().getCodigo());
            pStatement.setString(2, o.getNome());
            pStatement.setString(3, o.getArquivo().getAbsolutePath());
            pStatement.setInt(4, o.getCodigo());
            if (pStatement.executeUpdate() > 0) {
                return true;
            }

        } catch (SQLException e) {
            GeradorLog.getLoggerFull().severe(e.toString());
        }
        return false;
    }

    @Override
    public boolean delete(Propaganda o) {
        String sqlInsert = "DELETE FROM TB_PROPAGANDA WHERE PRO_CODIGO=?";
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
    public Collection<Propaganda> getAll() {
        String sqlGetAll = "SELECT * FROM TB_PROPAGANDA";
        Collection<Propaganda> propagandas = new ArrayList<>();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlGetAll);
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Propaganda propaganda = new Propaganda();
                propaganda.setCodigo(rs.getInt("PRO_CODIGO"));
                propaganda.setNome(rs.getString("PRO_NOME"));
                propaganda.setAtiva(rs.getBoolean("PRO_ATIVA"));
                ClienteDAO cliente = new ClienteDAO(ConnectionFactory.getConnection());
                propaganda.setCliente(cliente.getByCodigo(rs.getInt("PRO_CODCLIENTE")));
                propaganda.setArquivo(new File(rs.getString("PRO_ARQUIVO")));
                propagandas.add(propaganda);
            }
        } catch (SQLException e) {
            GeradorLog.getLoggerFull().severe(e.toString());
        }
        return propagandas;
    }

    public Propaganda getByCodigo(int codigo) {
        String sqlGetAll = "SELECT * FROM TB_PROPAGANDA WHERE PRO_CODIGO=?";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlGetAll);
            pStatement.setInt(1, codigo);
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Propaganda propaganda = new Propaganda();
                propaganda.setCodigo(rs.getInt("PRO_CODIGO"));
                propaganda.setNome(rs.getString("PRO_NOME"));
                propaganda.setAtiva(rs.getBoolean("PRO_ATIVA"));
                ClienteDAO cliente = new ClienteDAO(ConnectionFactory.getConnection());
                propaganda.setCliente(cliente.getByCodigo(rs.getInt("PRO_CODCLIENTE")));
                propaganda.setArquivo(new File(rs.getString("PRO_ARQUIVO")));
                return propaganda;
            }
        } catch (SQLException e) {
            GeradorLog.getLoggerFull().severe(e.toString());
        }
        return null;
    }

    /**
     * Retornar as Propagadas que ainda NÃO foram tocadas
     *
     * @param data
     * @return
     */
    public Collection<Propaganda> getByDia(Date data) {
        String sqlGetAll = "SELECT * FROM tb_diaspropagandas WHERE DIP_DATA =? and DIP_TOCADA is null";
        Collection<Propaganda> propagandas = new ArrayList<>();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlGetAll);
            pStatement.setObject(1, data);
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Propaganda propaganda = getByCodigo(rs.getInt("DIP_CODPROPAGANDA"));
                propaganda.setHora(rs.getTime("DIP_HORARIOPREVISTO").toLocalTime());
                propaganda.setData(rs.getDate("DIP_DATA").toLocalDate());
                propagandas.add(propaganda);
            }
        } catch (SQLException e) {
            GeradorLog.getLoggerFull().severe(e.toString());
        }
        return propagandas;
    }

    public boolean tocou(Propaganda o) {
        String sqlInsert = "UPDATE tb_diaspropagandas SET DIP_TOCADA=now() WHERE DIP_DATA=? AND DIP_HORARIOPREVISTO=? AND DIP_CODPROPAGANDA=?";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlInsert);
            pStatement.setDate(1, Date.valueOf(o.getData()));
            pStatement.setTime(2, Time.valueOf(o.getHora()));
            pStatement.setInt(3, o.getCodigo());
            if (pStatement.executeUpdate() > 0) {
                return true;
            }

        } catch (SQLException e) {
            GeradorLog.getLoggerFull().severe(e.toString());
        }
        return false;
    }

    public Collection<Propaganda> relatorioEntreDatas(LocalDate d1, LocalDate d2) {
        String sql = "SELECT * "
                + "FROM tb_diaspropagandas, tb_propaganda"
                + " WHERE DIP_DATA BETWEEN ? AND ? AND PRO_CODIGO = DIP_CODPROPAGANDA"
                + " ORDER BY DIP_DATA, DIP_HORARIOPREVISTO";
        Collection<Propaganda> propagandas = new ArrayList<>();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setObject(1, Date.valueOf(d1));
            pStatement.setObject(2, Date.valueOf(d2));
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Propaganda propaganda = getByCodigo(rs.getInt("DIP_CODPROPAGANDA"));
                propaganda.setNome(rs.getString("PRO_NOME"));
                propaganda.setHora(rs.getTime("DIP_HORARIOPREVISTO").toLocalTime());
                propaganda.setData(rs.getDate("DIP_DATA").toLocalDate());
                propagandas.add(propaganda);
            }
        } catch (Exception e) {

        }
        return propagandas;
    }

    public Collection<Propaganda> getBetween(LocalDate d1, LocalDate d2) {
        String sql = "SELECT * FROM tb_diaspropagandas, tb_propaganda "
                + "WHERE DIP_DATA BETWEEN ? AND ? AND PRO_CODIGO = DIP_CODPROPAGANDA "
                + "GROUP BY DIP_CODPROPAGANDA "
                + "ORDER BY DIP_DATA, DIP_HORARIOPREVISTO";
        Collection<Propaganda> propagandas = new ArrayList<>();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setObject(1, Date.valueOf(d1));
            pStatement.setObject(2, Date.valueOf(d2));
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Propaganda propaganda = getByCodigo(rs.getInt("DIP_CODPROPAGANDA"));
                propaganda.setNome(rs.getString("PRO_NOME"));
                propaganda.setHora(rs.getTime("DIP_HORARIOPREVISTO").toLocalTime());
                propaganda.setData(rs.getDate("DIP_DATA").toLocalDate());
                propagandas.add(propaganda);
            }
        } catch (Exception e) {

        }
        return propagandas;
    }

    public Collection<Propaganda> relatorioEntreDatas(LocalDate d1, LocalDate d2, Propaganda p) {
        String sql = "SELECT * "
                + "FROM tb_diaspropagandas, tb_propaganda"
                + " WHERE DIP_CODPROPAGANDA = ? AND DIP_DATA BETWEEN ? AND ? AND PRO_CODIGO = DIP_CODPROPAGANDA"
                + " ORDER BY DIP_DATA, DIP_HORARIOPREVISTO";
        Collection<Propaganda> propagandas = new ArrayList<>();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setInt(1, p.getCodigo());
            pStatement.setObject(2, Date.valueOf(d1));
            pStatement.setObject(3, Date.valueOf(d2));
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Propaganda propaganda = getByCodigo(rs.getInt("DIP_CODPROPAGANDA"));
                propaganda.setNome(rs.getString("PRO_NOME"));
                propaganda.setHora(rs.getTime("DIP_HORARIOPREVISTO").toLocalTime());
                propaganda.setData(rs.getDate("DIP_DATA").toLocalDate());
                propagandas.add(propaganda);
            }
        } catch (Exception e) {

        }
        return propagandas;
    }

    public Collection<Propaganda> getHorariosPropagandasAtivas(Propaganda p) {
        String sql = "SELECT * "
                + "FROM tb_diaspropagandas, tb_propaganda"
                + " WHERE DIP_CODPROPAGANDA = ? AND PRO_ATIVA = 'S' AND PRO_CODIGO = DIP_CODPROPAGANDA"
                + " AND DIP_DATA >= adddate(now(), -1)"
                + " ORDER BY DIP_DATA, DIP_HORARIOPREVISTO";
        Collection<Propaganda> propagandas = new ArrayList<>();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setObject(1, p.getCodigo());
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Propaganda propaganda = getByCodigo(rs.getInt("DIP_CODPROPAGANDA"));
                propaganda.setCodigo(rs.getInt("PRO_CODIGO"));
                propaganda.setNome(rs.getString("PRO_NOME"));
                propaganda.setHora(rs.getTime("DIP_HORARIOPREVISTO").toLocalTime());
                propaganda.setData(rs.getDate("DIP_DATA").toLocalDate());
                propagandas.add(propaganda);
            }
        } catch (Exception e) {

        }
        return propagandas;
    }

    public Collection<Propaganda> getHorariosPropagandas(Propaganda p) {
        String sql = "SELECT * "
                + "FROM tb_diaspropagandas, tb_propaganda"
                + " WHERE DIP_CODPROPAGANDA = ?  AND PRO_CODIGO = DIP_CODPROPAGANDA"
                + " ORDER BY DIP_DATA, DIP_HORARIOPREVISTO";
        Collection<Propaganda> propagandas = new ArrayList<>();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setObject(1, p.getCodigo());
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Propaganda propaganda = getByCodigo(rs.getInt("DIP_CODPROPAGANDA"));
                propaganda.setCodigo(rs.getInt("PRO_CODIGO"));
                propaganda.setNome(rs.getString("PRO_NOME"));
                propaganda.setHora(rs.getTime("DIP_HORARIOPREVISTO").toLocalTime());
                propaganda.setData(rs.getDate("DIP_DATA").toLocalDate());
                propagandas.add(propaganda);
            }
        } catch (Exception e) {

        }
        return propagandas;
    }

    public Collection<Propaganda> getDatasPorHorario(Propaganda p, LocalTime t) {
        String sql = "SELECT * "
                + "FROM tb_diaspropagandas, tb_propaganda "
                + "WHERE PRO_CODIGO = ? AND DIP_HORARIOPREVISTO = ? AND PRO_CODIGO = DIP_CODPROPAGANDA "
                + "ORDER BY DIP_DATA, DIP_HORARIOPREVISTO";
        Collection<Propaganda> propagandas = new ArrayList<>();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setObject(1, p.getCodigo());
            pStatement.setObject(2, Time.valueOf(t));
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Propaganda propaganda = getByCodigo(rs.getInt("DIP_CODPROPAGANDA"));
                propaganda.setCodigo(rs.getInt("PRO_CODIGO"));
                propaganda.setNome(rs.getString("PRO_NOME"));
                propaganda.setHora(rs.getTime("DIP_HORARIOPREVISTO").toLocalTime());
                propaganda.setData(rs.getDate("DIP_DATA").toLocalDate());
                propagandas.add(propaganda);
            }
        } catch (Exception e) {

        }
        return propagandas;
    }

}
