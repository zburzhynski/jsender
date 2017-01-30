package com.zburzhynski.jsender;

import com.zburzhynski.jsender.api.dto.Email;
import com.zburzhynski.jsender.impl.service.EmailSender;
import org.junit.Test;

import javax.mail.MessagingException;

/**
 * Verifies EmailSender functionality.
 * <p/>
 * Date: 30.01.2017
 *
 * @author Nikita Shevtsov
 */
public class AuthorityServiceIntegrationTest {

    @Test
    public void testGetAll() throws MessagingException {
        String smtpHost = "localhost";
        String smtpPort = "8080";
        String userName = "";
        String password = "";
        EmailSender emailSender = new EmailSender(smtpHost, smtpPort, userName, password);
        emailSender.send(new Email());
    }

}
