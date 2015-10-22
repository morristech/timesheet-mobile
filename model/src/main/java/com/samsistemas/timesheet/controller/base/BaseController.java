package com.samsistemas.timesheet.controller.base;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Interface that defines operations to make a more cleaner abstraction
 * from the CRUD operations.
 *
 * @author jonatan.salas
 */
public interface BaseController<T> {

    /**
     * Method that insert one object in the ContentProvider.
     *
     * @param context - the context used to get the ContentResolver.
     * @param toInsert - the object to insert.
     * @return true or false, in case that the insert is successful.
     */
    boolean insert(@NonNull Context context, @NonNull T toInsert);

    /**
     * Method that insert a massive count of objects in the ContentProvider.
     *
     * @param context - the context used to get the ContentResolver.
     * @param toInserts - the list of objects to insert.
     * @return true or false, in case that the bulk insert is successful.
     */
    boolean bulkInsert(@NonNull Context context, @NonNull List<T> toInserts);

    /**
     * Method that gets an object from an id.
     *
     * @param context - the context used to get the ContentResolver.
     * @param id - the id used to effectuate a select consulting.
     * @return a null Client object if failed, else the Client object we wanted.
     */
    T get(@NonNull Context context, long id);

    /**
     * Method that gets a list of objects.
     *
     * @param context - the context used to get the ContentResolver.
     * @return a null List if failed, else a List of objects.
     */
    List<T> listAll(@NonNull Context context);

    /**
     * Method that updates an object in the ContentProvider.
     *
     * @param context - the context used to get the ContentResolver.
     * @param toUpdate - the object to update.
     * @return true or false, in case that the update is successful.
     */
    boolean update(@NonNull Context context, @NonNull T toUpdate);

    /**
     * Method that deletes an object from the ContentProvider.
     *
      * @param context - the context used to get the ContentResolver.
     * @param id - the id used to find the object to delete.
     * @return true or false, in case that the delete is successful.
     */
    boolean delete(@NonNull Context context, long id);
}
