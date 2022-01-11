package tabacowang.me.taipeizoomvp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import tabacowang.me.taipeizoomvp.BuildConfig
import tabacowang.me.taipeizoomvp.api.network.ApiService
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

val networModule = module {

    // Provide Gson
    single<Gson> {
        GsonBuilder().create()
    }

    // Provide HttpLoggingInterceptor
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // Provide OkHttpClient
    single<OkHttpClient>(named("normal")) {
        val defaultTimeout = 60L

        OkHttpClient.Builder()
            .connectTimeout(defaultTimeout, TimeUnit.SECONDS)
            .readTimeout(defaultTimeout, TimeUnit.SECONDS)
            .writeTimeout(defaultTimeout, TimeUnit.SECONDS)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    // Provide unsafe OkHttpClient
    single<OkHttpClient>(named("unsafe")) {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
        })

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier { _, _ -> true }
        builder.build()
    }

    // Provide Retrofit
    single<Retrofit> {
        Retrofit.Builder()
            .client(get<OkHttpClient>(named("normal")))
            .baseUrl(BuildConfig.API_SERVER)
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    // Provide ApiService
    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }
}