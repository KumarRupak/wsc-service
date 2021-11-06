/*;==========================================
; Title:  Service class for email
; Author: Rupak Kumar
; Date:   17 Sep 2021
;==========================================*/
package demo.wsc.beta.service.mail


import org.springframework.beans.factory.annotation.Autowired
import kotlin.Throws
import org.springframework.stereotype.Service
import java.lang.Exception
import java.util.*
import javax.mail.*
import javax.mail.internet.MimeMessage
import javax.mail.internet.InternetAddress

@Service
 class ServiceMailProvider : ServiceMail {

    val WebSmartCredit = "WebSmartCredit"


    companion object {
        private const val sender = "noreply.wscredit@gmail.com"
        private const val password = "rupakkum"
    }

    private lateinit var properties: Properties
    val property: Properties
        get() {
            properties = Properties()
            properties["mail.smtp.auth"] = "true"
            properties["mail.smtp.starttls.enable"] = "true"
            properties["mail.smtp.host"] = "smtp.gmail.com"
            properties["mail.smtp.port"] = "587"
            return properties
        }


    @Throws(MessagingException::class)
    override fun sendPredctionResult(reciver: String?, predction: Int) {
        properties = property
        val properties = properties
        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(sender, password)
            }
        })
        val message = MessageSendPredction(session, sender, reciver, predction)
        try {
            Transport.send(message)
        } catch (e: Exception) {
            e.message
        }
    }

    @Throws(MessagingException::class)
    override fun sendToken(reciver: String?, token: String?) {
        properties = property
        val properties = properties
        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(sender, password)
            }
        })
        val message = MessageSendToken(session, sender, reciver, token)
        try {
            Transport.send(message)
        } catch (e: Exception) {
            e.message
        }
    }


    private fun MessageSendToken(session: Session, sender: String, reciver: String?, token: String?): Message? {
        val message: Message = MimeMessage(session)
        return try {
            message.setFrom(InternetAddress(sender))
            message.setRecipient(Message.RecipientType.TO, InternetAddress(reciver))
            message.subject = "Notification ${WebSmartCredit}"
            message.setText(
                "Dear User \n\nYoure secure key for access the service is :  $token" +
                        "\n\n Your service will be activated with in 24Hrs" +
                        "\n\n Don't share the key with any one. \n\nThanks team WSC"
            )
            message
        } catch (e: Exception) {
            e.message
            null
        }
    }


    private fun MessageSendPredction(session: Session, sender: String, reciver: String?, predction: Int): Message? {
        val message: Message = MimeMessage(session)
        return try {
            message.setFrom(InternetAddress(sender))
            message.setRecipient(Message.RecipientType.TO, InternetAddress(reciver))
            message.subject = "Notification ${WebSmartCredit}"
            if (predction == 1) message.setText(
                """
    Dear customer 
    
    Congratulation you are elegible for the credit. 
    
    Thank You
    
    Team WSC
    """.trimIndent()
            ) else message.setText(
                """Dear customer 

Thanks for your interest 

 Unfortunatly you are not elegible for the credit. 

Thank You

Team WSC"""
            )
            message
        } catch (e: Exception) {
            e.message
            null
        }
    }


}