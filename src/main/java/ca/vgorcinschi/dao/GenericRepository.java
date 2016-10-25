package ca.vgorcinschi.dao;

import java.io.Serializable;

/**
 * Since all repositories include basic operations to Create, Update or Delete
 * (but not Read!) we will factor-out these actions in a generic repository
 *
 * @author vgorcinschi
 */
public interface GenericRepository<E extends Serializable> {

    //add new record
    public void add(E entity);

    //update an existing record
    public void update(E entity);

    //delete an existing record
    public void delete(E entity);
}