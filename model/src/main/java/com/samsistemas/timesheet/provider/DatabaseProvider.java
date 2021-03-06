package com.samsistemas.timesheet.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.samsistemas.timesheet.data.R;
import com.samsistemas.timesheet.database.Database;
import com.samsistemas.timesheet.helper.UriHelper;
import static com.samsistemas.timesheet.util.ContentUriKeys.*;

/**
 * The content provider used to effectuate CRUD operations in the app.
 *
 * @author jonatan.salas
 */
public class DatabaseProvider extends ContentProvider {
    private static final String EQUALS = " = ";
    private UriMatcher mUriMatcher;
    private Database mDatabase;
    private Context mContext;

    @Override
    public boolean onCreate() {
        mContext = getContext();

        if (null != mContext) {
            mDatabase = Database.newInstance(mContext);
            mUriMatcher = UriHelper.buildUriMatcher(mContext);
        }

        return true;
    }

    @Nullable @Override
    public Cursor query(
        @NonNull Uri uri,
        String[] projection,
        String selection,
        String[] selectionArgs,
        String sortOrder) {

        SQLiteDatabase readableDatabase = mDatabase.getReadableDatabase();
        Cursor retCursor;

        switch(mUriMatcher.match(uri)) {
            case CLIENTS:
                retCursor = readableDatabase.query(
                        mContext.getString(R.string.client_table),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case CLIENT_ID:
                retCursor = readableDatabase.query(
                        mContext.getString(R.string.client_table),
                        projection,
                        mContext.getString(R.string.client_id) + EQUALS + "'" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            case WORK_POSITION:
                retCursor = readableDatabase.query(
                        mContext.getString(R.string.work_position_table),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case WORK_POSITION_ID:
                retCursor = readableDatabase.query(
                        mContext.getString(R.string.work_position_table),
                        projection,
                        mContext.getString(R.string.work_position_id) + EQUALS + "'" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            case PERSONS:
                retCursor = readableDatabase.query(
                        mContext.getString(R.string.person_table),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case PERSON_ID:
                retCursor = readableDatabase.query(
                        mContext.getString(R.string.person_table),
                        projection,
                        mContext.getString(R.string.person_id) + EQUALS + "'" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            case TASK_TYPES:
                retCursor = readableDatabase.query(
                        mContext.getString(R.string.task_type_table),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case TASKTYPE_ID:
                retCursor = readableDatabase.query(
                        mContext.getString(R.string.task_type_table),
                        projection,
                        mContext.getString(R.string.task_type_id) + EQUALS + "'" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            case PROJECTS:
                retCursor = readableDatabase.query(
                        mContext.getString(R.string.project_table),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case PROJECT_ID:
                retCursor = readableDatabase.query(
                        mContext.getString(R.string.project_table),
                        projection,
                        mContext.getString(R.string.project_id) + EQUALS + "'" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            case JOB_LOGS:
                retCursor = readableDatabase.query(
                        mContext.getString(R.string.job_log_table),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case JOBLOG_ID:
                retCursor = readableDatabase.query(
                        mContext.getString(R.string.job_log_table),
                        projection,
                        mContext.getString(R.string.job_log_id) + EQUALS + "'" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("unknown uri: " + uri);
        }

        retCursor.setNotificationUri(mContext.getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (mUriMatcher.match(uri)) {
            case CLIENTS:
                return mContext.getString(R.string.client_content_type);
            case CLIENT_ID:
                return mContext.getString(R.string.client_content_item_type);
            case WORK_POSITION:
                return mContext.getString(R.string.work_position_content_type);
            case WORK_POSITION_ID:
                return mContext.getString(R.string.work_position_content_item_type);
            case PERSONS:
                return mContext.getString(R.string.person_content_type);
            case PERSON_ID:
                return mContext.getString(R.string.person_content_item_type);
            case TASK_TYPES:
                return mContext.getString(R.string.task_type_content_type);
            case TASKTYPE_ID:
                return mContext.getString(R.string.task_type_content_item_type);
            case PROJECTS:
                return mContext.getString(R.string.project_content_type);
            case PROJECT_ID:
                return mContext.getString(R.string.project_content_item_type);
            case JOB_LOGS:
                return mContext.getString(R.string.job_log_content_type);
            case JOBLOG_ID:
                return mContext.getString(R.string.job_log_content_item_type);
            default:
                throw new UnsupportedOperationException("unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        Uri returnUri;

        switch(mUriMatcher.match(uri)) {
            case CLIENTS:
                returnUri = insert(uri, values, R.string.client_table);
                break;
            case WORK_POSITION:
                returnUri = insert(uri, values, R.string.work_position_table);
                break;
            case PERSONS:
                returnUri = insert(uri, values, R.string.person_table);
                break;
            case TASK_TYPES:
                returnUri = insert(uri, values, R.string.task_type_table);
                break;
            case PROJECTS:
                returnUri = insert(uri, values, R.string.project_table);
                break;
            case JOB_LOGS:
                returnUri = insert(uri, values, R.string.job_log_table);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        mContext.getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    /***
     * Method that makes an insert in a SQLite database.
     *
     * @param uri - the uri used by the provider.
     * @param values - the Values to insert.
     * @param tableName - the table name as resource id.
     * @return a uri that notifies the inserted id.
     */
    private Uri insert(@NonNull Uri uri, @NonNull ContentValues values, @StringRes int tableName) {
        SQLiteDatabase writableDatabase = mDatabase.getWritableDatabase();
        Uri returnUri;

        long id = -1;

        if (!writableDatabase.isReadOnly()) {
            id = writableDatabase.insertWithOnConflict(
                    mContext.getString(tableName),
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE
            );
        }

        if (id < 0) {
            throw new SQLException("failed to insert row in Uri: " + uri);
        } else {
            returnUri = ContentUris.withAppendedId(uri, id);
        }

        return returnUri;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        switch(mUriMatcher.match(uri)) {
            case CLIENTS:
                return bulkInsert(uri, values, R.string.client_table);
            case WORK_POSITION:
                return bulkInsert(uri, values, R.string.work_position_table);
            case PERSONS:
                return bulkInsert(uri, values, R.string.person_table);
            case TASK_TYPES:
                return bulkInsert(uri, values, R.string.task_type_table);
            case PROJECTS:
                return bulkInsert(uri, values, R.string.project_table);
            case JOB_LOGS:
                return bulkInsert(uri, values, R.string.job_log_table);

            default: return super.bulkInsert(uri, values);
        }
    }

    /***
     * Method that inserts a big quantity of ContentValues inside a SQLite database.
     *
     * @param uri - the ure used with the content resolver.
     * @param values - the ContentValues array to insert.
     * @param tableName - the table name as a Resource id.
     * @return an int representing the count of inserted rows.
     */
    private int bulkInsert(@NonNull Uri uri,
                           @NonNull ContentValues[] values,
                           @StringRes int tableName) {
        SQLiteDatabase writableDatabase = mDatabase.getWritableDatabase();
        writableDatabase.beginTransaction();
        int returnCount = 0;

        try {
            for (ContentValues value: values) {
                long id = writableDatabase.insertWithOnConflict(
                        mContext.getString(tableName),
                        null,
                        value,
                        SQLiteDatabase.CONFLICT_REPLACE
                );

                if (-1 != id) {
                    returnCount++;
                }
            }
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }

        mContext.getContentResolver().notifyChange(uri, null);

        return returnCount;
    }

    @Override
    public int update(@NonNull Uri uri,
                      ContentValues values,
                      String selection,
                      String[] selectionArgs) {
        SQLiteDatabase writableDatabase = mDatabase.getWritableDatabase();
        int updatedRows;

        switch(mUriMatcher.match(uri)) {
            case CLIENTS:
                updatedRows = writableDatabase.update(
                        mContext.getString(R.string.client_table),
                        values,
                        selection,
                        selectionArgs
                );
                break;
            case WORK_POSITION:
                updatedRows = writableDatabase.update(
                        mContext.getString(R.string.work_position_table),
                        values,
                        selection,
                        selectionArgs
                );
                break;
            case PERSONS:
                updatedRows = writableDatabase.update(
                        mContext.getString(R.string.person_table),
                        values,
                        selection,
                        selectionArgs
                );
                break;
            case TASK_TYPES:
                updatedRows = writableDatabase.update(
                        mContext.getString(R.string.task_type_table),
                        values,
                        selection,
                        selectionArgs
                );
                break;
            case PROJECTS:
                updatedRows = writableDatabase.update(
                        mContext.getString(R.string.project_table),
                        values,
                        selection,
                        selectionArgs
                );
                break;
            case JOB_LOGS:
                updatedRows = writableDatabase.update(
                        mContext.getString(R.string.job_log_table),
                        values,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (0 != updatedRows) {
            mContext.getContentResolver().notifyChange(uri, null);
        }

        return updatedRows;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase writableDatabase = mDatabase.getWritableDatabase();
        int deletedRows;

        switch(mUriMatcher.match(uri)) {
            case CLIENTS:
                deletedRows = writableDatabase.delete(
                        mContext.getString(R.string.client_table),
                        selection,
                        selectionArgs
                );
                break;
            case WORK_POSITION:
                deletedRows = writableDatabase.delete(
                        mContext.getString(R.string.work_position_table),
                        selection,
                        selectionArgs
                );
                break;
            case PERSONS:
                deletedRows = writableDatabase.delete(
                        mContext.getString(R.string.person_table),
                        selection,
                        selectionArgs
                );
                break;
            case TASK_TYPES:
                deletedRows = writableDatabase.delete(
                        mContext.getString(R.string.task_type_table),
                        selection,
                        selectionArgs
                );
                break;
            case PROJECTS:
                deletedRows = writableDatabase.delete(
                        mContext.getString(R.string.project_table),
                        selection,
                        selectionArgs
                );
                break;
            case JOB_LOGS:
                deletedRows = writableDatabase.delete(
                        mContext.getString(R.string.job_log_table),
                        selection,
                        selectionArgs
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        //if selection is null, database.delete() method deletes all rows.
        if (null == selection || 0 != deletedRows) {
            mContext.getContentResolver().notifyChange(uri, null);
        }

        return deletedRows;
    }
}
