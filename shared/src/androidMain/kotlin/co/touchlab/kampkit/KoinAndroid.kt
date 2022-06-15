package co.touchlab.kampkit

import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {

    single<Settings> {
        AndroidSettings(get())
    }

    single {
        OkHttp.create()
    }
}
