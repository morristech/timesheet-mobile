package com.samsistemas.timesheet.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.samsistemas.timesheet.controller.base.BaseController;
import com.samsistemas.timesheet.data.R;
import com.samsistemas.timesheet.factory.MapperFactory;
import com.samsistemas.timesheet.helper.UriHelper;
import com.samsistemas.timesheet.entity.ProjectEntity;
import com.samsistemas.timesheet.mapper.base.EntityMapper;
import com.samsistemas.timesheet.util.CursorUtil;

import java.util.List;

/**
 * Class the implements ProjectController interface.
 *
 * @author jonatan.salas
 */
public class ProjectController implements BaseController<ProjectEntity> {
    protected EntityMapper<ProjectEntity, Cursor> projectMapper;

    public ProjectController() {
        this.projectMapper = MapperFactory.getProjectMapper();
    }

    @Override
    public boolean insert(@NonNull Context context, @NonNull ProjectEntity projectEntity) {
        final Uri projectUri = UriHelper.buildProjectUri(context);
        final ContentValues projectValues = projectMapper.asContentValues(projectEntity);
        final Uri resultUri = context.getContentResolver().insert(projectUri, projectValues);
        return (null != resultUri);
    }

    @Override
    public boolean bulkInsert(@NonNull Context context, @NonNull List<ProjectEntity> projectEntities) {
        final Uri projectUri = UriHelper.buildProjectUri(context);
        ContentValues[] projectValues = new ContentValues[projectEntities.size()];

        for(int i = 0; i < projectEntities.size(); i++) {
            projectValues[i] = projectMapper.asContentValues(projectEntities.get(i));
        }

        int count = context.getContentResolver().bulkInsert(projectUri, projectValues);

        return (count != 0);
    }

    @Override
    public ProjectEntity get(@NonNull Context context, long id) {
        final Uri projectUri = UriHelper.buildProjectUriWithId(context, id);
        Cursor projectCursor = context.getContentResolver().query(projectUri, null, null, null, null);

        try {
            return projectMapper.asEntity(projectCursor);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(projectCursor!= null && !projectCursor.isClosed()){
                projectCursor.close();
            }
        }

        return new ProjectEntity();
    }

    @Override
    public List<ProjectEntity> listAll(@NonNull Context context) {
        final Uri projectsUri = UriHelper.buildProjectUri(context);
        Cursor projectsCursor = context.getContentResolver().query(projectsUri, null, null, null, null);

        return CursorUtil.asEntityList(projectsCursor, projectMapper);
    }

    @Override
    public boolean update(@NonNull Context context, @NonNull ProjectEntity projectEntity) {
        final Uri projectUri = UriHelper.buildProjectUri(context);
        final ContentValues projectValues = projectMapper.asContentValues(projectEntity);
        final String whereClause = context.getString(R.string.project_id) + " =? ";
        final String[] whereArgs = new String[] { String.valueOf(projectEntity.getProjectId()) };

        int updatedRows = context.getContentResolver().update(projectUri, projectValues, whereClause, whereArgs);

        return (0 != updatedRows);
    }

    @Override
    public boolean delete(@NonNull Context context, long id) {
        final Uri projectUri = UriHelper.buildProjectUri(context);
        final String selection = context.getString(R.string.project_id) + " =? ";
        final String[] selectionArgs = new String[] { String.valueOf(id) };

        int deletedRows = context.getContentResolver().delete(projectUri, selection, selectionArgs);

        return (0 != deletedRows);
    }
}