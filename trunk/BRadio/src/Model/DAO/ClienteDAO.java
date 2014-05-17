/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.*;
import Model.Logger.GeradorLog;
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
public class ClienteDAO extends GenericDAO<Cliente> {

    public ClienteDAO(Connection conn) {
        super(conn);
    }

    @Override
    public boolean insert(Cliente o) {
        String sqlInsert = "INSERT INTO TB_CLIENTE(CLI_NOMEFANTASIA, CLI_RAZAOSOCIAL, CLI_CNPJ, CLI_ENDERECO, CLI_TELEFONE) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlInsert, java.sql.Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, o.getNomeFantasia());
            pStatement.setString(2, o.getRazaoSocial());
            pStatement.setString(3, o.getCNPJ());
            pStatement.setString(4, o.getEndereco());
            pStatement.setString(5, o.getTelefone());
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
    public boolean update(Cliente o) {
        String sqlInsert = "UPDATE TB_CLIENTE SET CLI_NOMEFANTASIA = ?, CLI_RAZAOSOCIAL = ?, CLI_CNPJ = ?, CLI_ENDERECO = ?, CLI_TELEFONE= ? WHERE CLI_CODIGO = " + o.getCodigo();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlInsert);
            pStatement.setString(1, o.getNomeFantasia());
            pStatement.setString(2, o.getRazaoSocial());
            pStatement.setString(3, o.getCNPJ());
            pStatement.setString(4, o.getEndereco());
            pStatement.setString(5, o.getTelefone());
            if (pStatement.executeUpdate() > 0) {
                return true;
            }

        } catch (SQLException e) {
            GeradorLog.getLoggerFull().severe(e.toString());
        }
        return false;
    }

    @Override
    public boolean delete(Cliente o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Cliente> getAll() {
        String sqlGetAll = "SELECT * FROM TB_CLIENTE";
        Collection<Cliente> clientes = new ArrayList<>();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlGetAll);
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setCodigo(rs.getInt("CLI_CODIGO"));
                cliente.setNomeFantasia(rs.getString("CLI_NOMEFANTASIA"));
                cliente.setRazaoSocial(rs.getString("CLI_RAZAOSOCIAL"));
                cliente.setCNPJ(rs.getString("CLI_CNPJ"));
                cliente.setTelefone(rs.getString("CLI_TELEFONE"));
                cliente.setEndereco(rs.getString("CLI_ENDERECO"));
                cliente.setDataCadastro(rs.getDate("CLI_DATACADASTRO"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            //who cares?
            GeradorLog.getLoggerFull().severe(e.toString());
        }
        return clientes;
    }

    public Cliente getByCodigo(int codigo) {
        String sqlGetAll = "SELECT * FROM TB_CLIENTE WHERE CLI_CODIGO = ?";
        Cliente cliente = null;
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlGetAll);
            pStatement.setInt(1, codigo);
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                cliente = new Cliente();
                cliente.setCodigo(rs.getInt("CLI_CODIGO"));
                cliente.setNomeFantasia(rs.getString("CLI_NOMEFANTASIA"));
                cliente.setRazaoSocial(rs.getString("CLI_RAZAOSOCIAL"));
                cliente.setCNPJ(rs.getString("CLI_CNPJ"));
                cliente.setTelefone(rs.getString("CLI_TELEFONE"));
                cliente.setEndereco(rs.getString("CLI_ENDERECO"));
                cliente.setDataCadastro(rs.getDate("CLI_DATACADASTRO"));
            }
        } catch (SQLException e) {
            //who cares?
            GeradorLog.getLoggerFull().severe(e.toString());
        }
        return cliente;
    }

}
