package com.samsistemas.timesheet.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.samsistemas.timesheet.controller.base.BaseController;
import com.samsistemas.timesheet.entity.base.Entity;
import com.samsistemas.timesheet.mapper.base.EntityMapper;

import java.util.List;

/**
 * Generic controller used to access the ContentProvider
 *
 * @author jonatan.salas
 * @param <T> class that extends Entity
 */
public class Controller<T extends Entity> implements BaseController<T> {
    private static final String CLAUSE = " id =? ";
    private final EntityMapper<T, Cursor> entityMapper;

    /**
     * Default Controller constructor with params
     *
     * @param mapper EntityMapper used to convert an object to Cursor/ContentValues/Entity
     */
    public Controller(EntityMapper<T, Cursor> mapper) {
        this.entityMapper = mapper;
    }

    @Override
    public boolean insert(@NonNull Context context, @NonNull T object, @NonNull Uri uri) {
        final ContentValues values = entityMapper.asContentValues(object);
        final Uri resultUri = context.getContentResolver().insert(uri, values);
        return (null != resultUri);
    }

    @Override
    public boolean bulkInsert(@NonNull Context context, @NonNull List<T> list, @NonNull Uri uri) {
        final ContentValues[] values = new ContentValues[list.size()];

        for (int i = 0; i < list.size(); i++) {
            values[i] = entityMapper.asContentValues(list.get(i));
        }

        Log.d(Controller.class.getSimpleName(), "Values: " + values );

        int count = context.getContentResolver().bulkInsert(uri, values);

        return (count != 0);
    }

    @Override
    public T get(@NonNull Context context, @NonNull Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        return entityMapper.asEntity(cursor);
    }

    @Override
    public List<T> listAll(@NonNull Context context, @NonNull Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        return entityMapper.asEntityList(cursor);
    }

    @Override
    public boolean update(@NonNull Context context, @NonNull T toUpdate, @NonNull Uri uri) {
        final ContentValues values = entityMapper.asContentValues(toUpdate);
        final String[] whereArgs = new String[] {
                String.valueOf(toUpdate.getId())
        };

        int updatedRows = context.getContentResolver().update(uri, values, CLAUSE, whereArgs);

        return (0 != updatedRows);
    }

    @Override
    public boolean delete(@NonNull Context context, @NonNull Uri uri, long id) {
        int deletedRows = context.getContentResolver().delete(uri, CLAUSE, new String[] {
                String.valueOf(id)
        });
        return (0 != deletedRows);
    }
}
