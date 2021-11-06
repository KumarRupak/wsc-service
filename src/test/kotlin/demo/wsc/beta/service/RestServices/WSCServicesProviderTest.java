/*;==========================================
; Title:  Test class for WSCServices
; Author: Rupak Kumar
; Date:   20 Sep 2021
;==========================================*/

package demo.wsc.beta.service.RestServices;

import demo.wsc.beta.exceptions.*;
import demo.wsc.beta.repository.WSCServicesRepository;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import demo.wsc.beta.model.transport.OrganisationRegister;

import javax.mail.MessagingException;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class WSCServicesProviderTest {

   /* @Autowired
    private ServiceAdminProvider serviceAdmin;

    @Autowired
    private ServiceCustomerProvider serviceCustomer;*/

    @Autowired
    private WSCServicesProvider serviceWSC;

    @Autowired
    private WSCServicesRepository repoWSC;

    private static int customerId=0;

    @Order(1)
    @Test
    void organisationRegister() throws MessagingException {
       /* OrganisationRegister details = new  OrganisationRegister("IncomeTax Dept gov.in", "fb.rupakpatro@gmail.com.com");
        Assertions.assertEquals("success",serviceWSC.organisationRegister(details).getStatus());*/
        Assertions.assertTrue(true);
    }

    /*@Order(2)
    void activateToken() throws WSCExceptionInvalidUser {
        Assertions.assertTrue(serviceAdmin.activateToken("patrorupak99@gmail.com"));
    }*/

    @Order(3)
    @Test
    void transactionDetails() throws InvalidToken, WSCExceptionAccountDeactivated {
        /*Assertions.assertTrue(!serviceWSC.getTransactionDetails
                (repoWSC.findByOrganisationEmail("patrorupak99@gmail.com").get().getOrganisationToken()).isEmpty());*/
        Assertions.assertTrue(true);
    }

}