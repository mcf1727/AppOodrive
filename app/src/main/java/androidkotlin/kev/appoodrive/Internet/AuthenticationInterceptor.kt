package androidkotlin.kev.appoodrive.Internet

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthenticationInterceptor(user: String, password: String): Interceptor {

    //        val request = HttpPost(URL_SECURED_BY_BASIC_AUTHENTICATION)
//        val auth: String = USER_ID.toString() + ":" + USER_PASSWORD
//        val encodedAuth: ByteArray = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8))
//        val authHeader = "Basic " + String(encodedAuth)
//        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader)
//        val client: HttpClient = HttpClientBuilder.create().build()
//        val response: HttpResponse = client.execute(request)

    private val credentials: String = Credentials.basic(user, password)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("Authorization", credentials).build()
        return chain.proceed(authenticatedRequest)
    }
}