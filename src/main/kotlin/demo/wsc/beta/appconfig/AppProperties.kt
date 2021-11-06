package demo.wsc.beta.appconfig

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class AppProperties {


    @Value("\${service.apiKey}")
    lateinit var urlApiKey:String

    @Value("\${client.url}")
    lateinit var urlClient:String


}