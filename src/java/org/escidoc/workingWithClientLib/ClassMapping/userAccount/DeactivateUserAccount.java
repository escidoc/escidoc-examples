package org.escidoc.workingWithClientLib.ClassMapping.userAccount;

import de.escidoc.core.client.Authentication;
import de.escidoc.core.client.UserAccountHandlerClient;
import de.escidoc.core.client.exceptions.EscidocClientException;
import de.escidoc.core.resources.aa.useraccount.UserAccount;
import de.escidoc.core.resources.aa.useraccount.UserAccountProperties;
import de.escidoc.core.resources.common.TaskParam;
import de.escidoc.core.test.client.Constants;
import de.escidoc.core.test.client.EscidocClientTestBase;

/**
 * Example how an user account is made deactive. An internal user account is
 * created and these afterwards deactivated.
 * 
 * @author SWA
 * 
 */
public class DeactivateUserAccount {

    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
            Authentication auth =
                new Authentication(EscidocClientTestBase.DEFAULT_SERVICE_URL,
                    Constants.SYSTEM_ADMIN_USER,
                    Constants.SYSTEM_ADMIN_PASSWORD);

            // login
            UserAccountHandlerClient uac = new UserAccountHandlerClient();
            uac.setServiceAddress(EscidocClientTestBase.DEFAULT_SERVICE_URL);
            uac.setHandle(auth.getHandle());

            // create
            UserAccount ua = uac.create(prepareUserAccount());

            // deactivate
            // prepare task param for deactivation of user account
            TaskParam taskParam = new TaskParam();
            taskParam.setLastModificationDate(ua.getLastModificationDate());
            uac.deactivate(ua.getObjid(), taskParam);

            ua = uac.retrieve(ua.getObjid());
            System.out.println("UserAccount with objid '" + ua.getObjid()
                + "' created. Account active=" + ua.getProperties().isActive()
                + ".");
        }
        catch (EscidocClientException e) {
            e.printStackTrace();
        }

    }

    /**
     * Prepare an UserAccount object (not created at infrastructure).
     * 
     * @return UserAccount
     */
    private static UserAccount prepareUserAccount() {

        UserAccount ua = new UserAccount();

        // user properties
        UserAccountProperties properties = new UserAccountProperties();
        String login = "login" + System.currentTimeMillis();
        properties.setName("Name " + login);
        properties.setLoginName(login);

        ua.setProperties(properties);

        return ua;
    }
}