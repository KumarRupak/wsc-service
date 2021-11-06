/*;==========================================
; Title:  Service class for ESCServices
; Author: Rupak Kumar
; Date:   20 Sep 2021
;==========================================*/
package demo.wsc.beta.service.RestServices

import demo.wsc.beta.exceptions.*
import demo.wsc.beta.model.CustomerTransactions
import demo.wsc.beta.model.transport.*
import javax.mail.MessagingException

 interface WSCService {
    @Throws(MessagingException::class)
    fun organisationRegister(details: OrganisationRegister): RegisterStatus

    @Throws(InvalidToken::class,WSCExceptionAccountDeactivated::class)
    fun getTransactionDetails(token: String): MutableList<CustomerTransactions>

}