package com.samsistemas.timesheet.common.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.samsistemas.timesheet.common.controller.ControllerImpl;
import com.samsistemas.timesheet.common.controller.base.Controller;
import com.samsistemas.timesheet.common.model.JobLog;

import java.util.List;

/**
 * @author jonatan.salas
 */
public class JobLogsLoader extends AsyncTaskLoader<List<JobLog>> {
    private static final String LOG_TAG = JobLogsLoader.class.getSimpleName();
    private static final Class<JobLog> clazz = JobLog.class;
    private final Object lock = new Object();
    private Controller<JobLog> controller;

    public JobLogsLoader(Context context) {
        super(context);
        this.controller = new ControllerImpl<>();
    }

    @Override
    public List<JobLog> loadInBackground() {
        List<JobLog> jobLogList = null;

        if (controller.getCount(clazz) == 0) {
            try {
                synchronized (lock) {
                    lock.wait(3000);
                    jobLogList = controller.listAll(clazz);
                }
            } catch (InterruptedException ex) {
                Log.e(LOG_TAG, ex.getMessage(), ex.getCause());
            }
        } else {
            synchronized (lock) {
                jobLogList = controller.listAll(clazz);
                lock.notify();
            }
        }

        return jobLogList;
    }
}