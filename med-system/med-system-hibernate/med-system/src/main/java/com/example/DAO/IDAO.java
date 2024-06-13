package com.example.DAO;

import java.util.List;

public interface IDAO<E>
{
    //Select all record
    List<E> showAll();
    //Select record by id
    E findById(Long id);
    //Select record by key fields
    E findByKey(E template);
    boolean insert(E entityToSave);
    boolean update(Long id,E entityToUpdate);
    boolean delete(Long id);
}
