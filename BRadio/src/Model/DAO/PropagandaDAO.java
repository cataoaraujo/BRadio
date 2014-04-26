/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.DAO;

import Model.Propaganda;
import java.sql.Connection;
import java.util.Collection;

/**
 *
 * @author Rodrigo
 */
public class PropagandaDAO extends GenericDAO<Propaganda>{

    public PropagandaDAO(Connection conn) {
        super(conn);
    }

    @Override
    public boolean insert(Propaganda o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Propaganda o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Propaganda o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Propaganda> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
