/*;========= =================================
; Title:  Service class for WSCServices
; Author: Rupak Kumar
; Date:   20 Sep 2021
;==========================================*/
package demo.wsc.beta.service.RestServices


import demo.wsc.beta.algorithms.PasswordEncode.Encoder.Companion.encode
import demo.wsc.beta.algorithms.RandomString.GenerateToken
import demo.wsc.beta.appconfig.AppProperties
import org.springframework.beans.factory.annotation.Autowired
import demo.wsc.beta.service.mail.ServiceMailProvider
import kotlin.Throws
import javax.mail.MessagingException
import demo.wsc.beta.model.WSCServices
import demo.wsc.beta.exceptions.InvalidToken
import demo.wsc.beta.exceptions.WSCExceptionAccountDeactivated
import demo.wsc.beta.model.CustomerTransactions
import demo.wsc.beta.model.transport.*
import demo.wsc.beta.repository.*
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Slf4j
@Service
 class WSCServicesProvider : WSCService {
    @Autowired
    private lateinit var serviceMail: ServiceMailProvider

    @Autowired
    private lateinit var repoWscService: WSCServicesRepository

    @Autowired
    private lateinit var repoCusTrans: CustomerTransactionsRepository

    @Autowired
    lateinit var appProperties: AppProperties


    val log = LoggerFactory.getLogger(ServiceMailProvider::class.java)


    /**
     * Adding the organizations details to repository and return a api key
     *
     * @param 'Organization details'
     * @return - status
     */
    @Throws(MessagingException::class)
    override fun organisationRegister(details: OrganisationRegister): RegisterStatus {
        if (details.organisationName != null && details.organisationEmail != null) {
            if (!repoWscService.findByOrganisationEmail(details.organisationEmail).isPresent) {
                val organisation = WSCServices()
                val token = encode(GenerateToken.getToken())
                serviceMail.sendToken(details.organisationEmail, token)
                organisation.organisationToken = token
                organisation.organisationName = details.organisationName
                organisation.organisationEmail = details.organisationEmail
                organisation.accountFlag = 0
                repoWscService.save(organisation)
                return RegisterStatus(appProperties.urlApiKey+token)
            } else {
                return RegisterStatus("NA")
            }
        }
        return RegisterStatus("NA")
    }

    /**
     * Get all transactions from repository based on the provided Api Key by the user
     *
     * @param 'Api Key'
     * @return - List of transactions
     */
    @Throws(InvalidToken::class, WSCExceptionAccountDeactivated::class)
    override fun getTransactionDetails(token: String): MutableList<CustomerTransactions> {
        if (repoWscService.findById(token).isPresent) {
            if (repoWscService.findById(token).get().accountFlag == 1) {
                log.info("Transaction destails hasbeen access by ${repoWscService.findById(token).get().organisationName}")
                return repoCusTrans.findAll().stream().filter({ i ->  i?.amount!!.toLong() >= 50000 }).collect(
                    Collectors.toList())
            } else {
                throw WSCExceptionAccountDeactivated()
            }
        } else {
            throw InvalidToken()
        }
    }

}