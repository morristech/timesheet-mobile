package com.samsistemas.timesheet.factory;

import com.samsistemas.timesheet.controller.ClientController;
import com.samsistemas.timesheet.controller.JobLogController;
import com.samsistemas.timesheet.controller.LoginController;
import com.samsistemas.timesheet.controller.PersonController;
import com.samsistemas.timesheet.controller.ProjectController;
import com.samsistemas.timesheet.controller.SessionController;
import com.samsistemas.timesheet.controller.TaskTypeController;
import com.samsistemas.timesheet.controller.base.BaseController;
import com.samsistemas.timesheet.controller.base.BaseLoginController;
import com.samsistemas.timesheet.controller.base.BaseSessionController;
import com.samsistemas.timesheet.model.Client;
import com.samsistemas.timesheet.model.JobLog;
import com.samsistemas.timesheet.model.Person;
import com.samsistemas.timesheet.model.Project;
import com.samsistemas.timesheet.model.TaskType;

/**
 * Class used to get controller instances.
 *
 * @author jonatan.salas
 */
public class ControllerFactory {
    private static BaseController<Client> clientBaseController = null;
    private static BaseController<JobLog> jobLogBaseController = null;
    private static BaseController<Person> personBaseController = null;
    private static BaseController<Project> projectBaseController = null;
    private static BaseController<TaskType> taskTypeBaseController = null;
    private static BaseLoginController loginController = null;
    private static BaseSessionController sessionController = null;

    /**
     * Method that gets a singleton instance.
     *
     * @return a singleton object.
     */
    public static synchronized BaseController<Client> getClientController() {
        if(null == clientBaseController)
            clientBaseController = new ClientController();
        return clientBaseController;
    }

    /**
     * Method that gets a singleton instance.
     *
     * @return a singleton object.
     */
    public static synchronized BaseController<JobLog> getJobLogController() {
        if(null == jobLogBaseController)
            jobLogBaseController = new JobLogController();
        return jobLogBaseController;
    }

    /**
     * Method that gets a singleton instance.
     *
     * @return a singleton object.
     */
    public static synchronized BaseController<Person> getPersonController() {
        if(null == personBaseController)
            personBaseController = new PersonController();
        return personBaseController;
    }

    /**
     * Method that gets a singleton instance.
     *
     * @return a singleton object.
     */
    public static synchronized BaseController<Project> getProjectController() {
        if(null == projectBaseController)
            projectBaseController = new ProjectController();
        return projectBaseController;
    }

    /**
     * Method that gets a singleton instance.
     *
     * @return a singleton object.
     */
    public static synchronized BaseController<TaskType> getTaskTypeController() {
        if(null == taskTypeBaseController)
            taskTypeBaseController = new TaskTypeController();
        return taskTypeBaseController;
    }

    /**
     * Method that gets a singleton instance.
     *
     * @return a singleton object.
     */
    public static synchronized BaseLoginController getLoginController() {
        if(null == loginController)
            loginController = new LoginController();
        return loginController;
    }

    /**
     * Method that gets a singleton instance.
     *
     * @return a singleton object.
     */
    public static synchronized BaseSessionController getSessionController() {
        if(null == sessionController)
            sessionController = new SessionController();
        return sessionController;
    }
}
