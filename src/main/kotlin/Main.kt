import okhttp3.OkHttpClient
import okhttp3.Request
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

fun getUnsafeOkHttpClient(): OkHttpClient {
    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
    })

    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, java.security.SecureRandom())

    return OkHttpClient.Builder()
        .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
        .hostnameVerifier { _, _ -> true }
        .build()
}

fun fetchDataFromApi(url: String): String {
    val client = getUnsafeOkHttpClient()

    val request = Request.Builder()
        .url(url)
        .build()

    val response = client.newCall(request).execute()

    if (!response.isSuccessful) throw Exception("Unexpected code ${response.code}")

    return response.body?.string() ?: ""
}

fun main() {
    val apiUrl = "https://jsonkeeper.com/b/MX0A"  // Replace with your actual endpoint
    try {
        val result = fetchDataFromApi(apiUrl)
        println("API Response: $result")
    } catch (e: Exception) {
        println("Error fetching data: ${e.message}")
    }
}