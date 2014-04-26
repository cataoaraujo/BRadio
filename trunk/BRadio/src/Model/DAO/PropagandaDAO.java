/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Propaganda;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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

        } catch (Exception e) {
            e.printStackTrace();
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

        } catch (Exception e) {
            e.printStackTrace();
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

        } catch (Exception e) {
            e.printStackTrace();
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
        } catch (Exception e) {
            //who cares?
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
        } catch (Exception e) {
            //who cares?
        }
        return null;
    }
    public Propaganda getByDia(Date data) {
        String sqlGetAll = "SELECT * FROM tb_diaspropagandas WHERE DIP_DATA =?";
        Collection<Propaganda> propagandas = new ArrayList<>();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlGetAll);
            pStatement.setObject(1, data);
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Propaganda propaganda = getByCodigo(rs.getInt("DIP_CODPROPAGANDA"));
                propaganda.setHorarios(rs.getTime("DIP_HORARIOPREVISTO"));
                propaganda.setData(rs.getDate("DIP_DATA"));
                return propaganda;
            }
        } catch (Exception e) {
            //who cares?
        }
        return null;
    }


}
