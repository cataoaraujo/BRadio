/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.DAO;
import Model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.util.Collection;

/**
 *
 * @author Rodrigo
 */
public class ClienteDAO extends GenericDAO<Cliente>{

    public ClienteDAO(Connection conn) {
        super(conn);
    }

    @Override
    public boolean insert(Cliente o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Cliente o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                System.out.println(rs.getString("CLI_NOMEFANTASIA"));
                clientes.add(cliente);
            }
        } catch (Exception e) {
            //who cares?
            //GeradorLog.getLoggerBanco().severe(e.toString());
        }
        return clientes;
    }
    
}
