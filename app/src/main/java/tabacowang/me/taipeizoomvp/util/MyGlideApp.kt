package tabacowang.me.taipeizoomvp.util

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.module.LibraryGlideModule
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import java.io.InputStream

@GlideModule
class MyGlideApp: LibraryGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val unsafeOkhttpClient: OkHttpClient by inject(OkHttpClient::class.java, qualifier = named("unsafe"))
        registry.replace(
            GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(unsafeOkhttpClient)
        )
    }
}

@GlideModule
class MyGlideAppModule: AppGlideModule() {
    /**
     *     Failed to find GeneratedAppGlideModule.
     *     You should include an annotationProcessor
     *     compile dependency on com.github.bumptech.glide:compiler
     *     in your application and a @GlideModule annotated
     *     AppGlideModule implementation or LibraryGlideModules will be silently ignored.
     *     為了解決這個異常狀態，特地新件一個工具類，只要繼承了AppGlideModule，在加載圖片的時候就會自動被使用到
     */
}
