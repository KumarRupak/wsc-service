/*;==========================================
; Title:  End Points for WebSmartCredit
; Author: Rupak Kumar
; Date:   3 Oct 2021
;==========================================*/

package demo.wsc.beta.controller.wscservice

import demo.wsc.beta.algorithms.utility.Validator
import demo.wsc.beta.exceptions.InvalidToken
import demo.wsc.beta.exceptions.WSCExceptionAccountDeactivated
import demo.wsc.beta.exceptions.WSCExceptionInvalidDetails
import demo.wsc.beta.model.transport.OrganisationRegister
import demo.wsc.beta.service.RestServices.WSCServicesProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.mail.MessagingException
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("api/service")
class WebSmartCreditEndPoints {

    @Autowired
    lateinit var serviceWSC: WSCServicesProvider

    /**
     *Welcome
     */
    @GetMapping("/")
    fun welcome():String{
        return "------------------------------------------ WSC Service ----------------------------------------"
    }
    /**
     * End point for register the organization
     *
     * @param 'Organization details-JSON'
     * @return - Response enitity
     */
    @PostMapping(
        "/register",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Throws(MessagingException::class)
    fun organisationRegister(
        @RequestBody details: OrganisationRegister,
        response: HttpServletResponse
    ): ResponseEntity<Any> {
        if (!details.organisationName.equals(null) && Validator.isValidEmailID(details.organisationEmail!!) == true) {
            return ResponseEntity(serviceWSC.organisationRegister(details), HttpStatus.OK)
        } else {
            throw WSCExceptionInvalidDetails()
        }
    }

    /**
     * End point for get all transaction having more then 50000 per day
     *
     * @param 'Secure key'
     * @return - Response enitity
     */
    @GetMapping("/transaction", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Throws(InvalidToken::class, WSCExceptionAccountDeactivated::class)
    fun getTransactionDetails(token: String): ResponseEntity<Any> {
        if (token.length > 16) {
            return ResponseEntity(serviceWSC.getTransactionDetails(token), HttpStatus.OK)
        } else {
            throw InvalidToken()
        }
    }

}