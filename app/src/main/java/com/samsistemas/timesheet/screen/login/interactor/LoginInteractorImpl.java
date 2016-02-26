package com.samsistemas.timesheet.screen.login.interactor;

import com.samsistemas.timesheet.utility.ThreadUtility;
import com.samsistemas.timesheet.domain.Person;
import com.samsistemas.timesheet.domain.Session;
import com.samsistemas.timesheet.domain.WorkPosition;

import com.samsistemas.timesheet.screen.login.interactor.base.LoginInteractor;
import com.samsistemas.timesheet.screen.login.listener.OnCreateSessionListener;
import com.samsistemas.timesheet.screen.login.listener.OnLoginFinishedListener;
import com.samsistemas.timesheet.screen.login.validation.EmailValidator;
import com.samsistemas.timesheet.screen.login.validation.PasswordValidator;

/**
 * @author jonatan.salas
 */
public class LoginInteractorImpl implements LoginInteractor {

    @Override
    public void login(final String username, final  String password,
                      final OnLoginFinishedListener listener,
                      final  OnCreateSessionListener sessionListener) {
        boolean error = false;

        if (!EmailValidator.newInstance().validate(username)) {
            listener.onUsernameError();
            error = true;
        }

        if (!PasswordValidator.newInstance().validate(password)) {
            listener.onPasswordError();
            error = true;
        }

        if (!error) {
            createUserSessionIfNotExits(sessionListener);
            listener.onLoginSuccess();
        }
    }

    @Override
    public void createUserSessionIfNotExits(OnCreateSessionListener sessionListener) {
        if (null != sessionListener) {
            final WorkPosition workPosition = ThreadUtility.runInBackground(new ThreadUtility.CallBack<WorkPosition>() {
                @Override
                public WorkPosition execute() {
                    WorkPosition workPosition = new WorkPosition();
                    workPosition.setDescription("Android Developer")
                            .setServerId(1L);

                    WorkPosition.save(workPosition);
                    return workPosition;
                }
            });

            final Person person = ThreadUtility.runInBackground(new ThreadUtility.CallBack<Person>() {
                @Override
                public Person execute() {
                    Person person = new Person();
                    person.setEnabled(true)
                            .setName("Jonatan")
                            .setServerId(1L)
                            .setLastName("Salas")
                            .setPassword("nbbbbbbb")
                            .setPicture(null)
                            .setUsername("jonisaa")
                            .setWorkHours(6)
                            .setWorkPosition(workPosition);

                    Person.save(person);

                    return person;
                }
            });

            final Long id = ThreadUtility.runInBackground(new ThreadUtility.CallBack<Long>() {
                @Override
                public Long execute() {
                    Session session = new Session();
                    session.setActive(true);
                    session.setPerson(person);

                    return Session.save(session);
                }
            });

            sessionListener.onSessionCreate(id);
        }
    }
}
