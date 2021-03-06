package com.samsistemas.timesheet.controller.base;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.samsistemas.timesheet.entity.base.Entity;

import java.util.List;

/**
 * Interface for a generic controller class that provides crud operations to use with a contentProvider.
 *
 * @param <T> object class that extends Entity class.
 */
public interface BaseController<T extends Entity> {

    /**
     * Method that insert one object in the ContentProvider.
     *
     * @param context the context used to get the ContentResolver.
     * @param toInsert the object to insert.
     * @param uri the uri used to point the table to do the insert.
     * @return true or false, in case that the insert is successful.
     */
    boolean insert(@NonNull Context context, @NonNull T toInsert, @NonNull Uri uri);

    /**
     * Method that insert a massive count of objects in the ContentProvider.
     *
     * @param context the context used to get the ContentResolver.
     * @param toInserts the list of objects to insert.
     * @param uri the uri used to point the table to do the bulk insert.
     * @return true or false, in case that the bulk insert is successful.
     */
    boolean bulkInsert(@NonNull Context context, @NonNull List<T> toInserts, @NonNull Uri uri);

    /**
     * Method that gets an object from an id.
     *
     * @param context the context used to get the ContentResolver.
     * @param uri the uri that points to the object to get.
     * @return a null Client object if failed, else the Client object we wanted.
     */
    T get(@NonNull Context context, @NonNull Uri uri);

    /**
     * Method that gets a list of objects.
     *
     * @param context the context used to get the ContentResolver.
     * @param uri the uri that points to the table to query.
     * @return a null List if failed, else a List of objects.
     */
    List<T> listAll(@NonNull Context context, @NonNull Uri uri);

    /**
     * Method that updates an object in the ContentProvider.
     *
     * @param context the context used to get the ContentResolver.
     * @param toUpdate the object to update.
     * @param uri the uri that points the table used to do the update.
     * @return true or false, in case that the update is successful.
     */
    boolean update(@NonNull Context context, @NonNull T toUpdate, @NonNull Uri uri);

    /**
     * Method that deletes an object from the ContentProvider.
     *
     * @param context the context used to get the ContentResolver.
     * @param uri the uri that points the table used to do the delete.
     * @param id the id used to find the object to delete.
     * @return true or false, in case that the delete is successful.
     */
    boolean delete(@NonNull Context context, @NonNull Uri uri, long id);
}
