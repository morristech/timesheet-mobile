package com.samsistemas.timesheet.screen.settings.interactor;

import com.orm.SugarRecord;
import com.samsistemas.timesheet.utility.ThreadUtility;
import com.samsistemas.timesheet.domain.Session;
import com.samsistemas.timesheet.screen.settings.interactor.base.SettingsInteractor;
import com.samsistemas.timesheet.screen.settings.listener.OnLogoutFinishedListener;

/**
 * @author jonatan.salas
 */
public class SettingsInteractorImpl implements SettingsInteractor {

    @Override
    public void logout(final Long sessionId, final OnLogoutFinishedListener listener) {
        boolean error = false;

        if (sessionId == null) {
            listener.onLogoutError();
            error = true;
        }

        if (!error) {
            final Session session = ThreadUtility.runInBackground(new ThreadUtility.CallBack<Session>() {
                @Override
                public Session execute() {
                    return SugarRecord.findById(Session.class, sessionId);
                }
            });

            if (null != session && session.getActive()) {
                ThreadUtility.runInBackground(new ThreadUtility.CallBack<Void>() {
                    @Override
                    public Void execute() {
                        SugarRecord.delete(session);
                        return null;
                    }
                });

                listener.onLogoutSuccess();
            }
        }
    }
}
