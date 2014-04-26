/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import java.sql.Connection;
import java.util.Collection;

/**
 *
 * @author Rodrigo
 */
public abstract class GenericDAO<T> {

    Connection conn;

    public GenericDAO(Connection conn) {
        this.conn = conn;
    }

    abstract public boolean insert(T o);

    abstract public boolean update(T o);

    abstract public boolean delete(T o);

    abstract public Collection<T> getAll();

}
