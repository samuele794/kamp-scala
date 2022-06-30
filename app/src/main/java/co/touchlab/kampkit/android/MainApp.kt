package co.touchlab.kampkit.android

import android.app.Application
import it.samuele794.scala.AppInfo
import it.samuele794.scala.android.BuildConfig

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
//        initKoin(
//            module {
//                single<Context> { this@MainApp }
//                // viewModel { BreedViewModel(get(), get { parametersOf("BreedViewModel") }) }
//                single<SharedPreferences> {
//                    get<Context>().getSharedPreferences("KAMPSTARTER_SETTINGS", Context.MODE_PRIVATE)
//                }
//                single<AppInfo> { AndroidAppInfo }
//                single {
//                    { Log.i("Startup", "Hello from Android/Kotlin!") }
//                }
//            }
//        )
    }
}

object AndroidAppInfo : AppInfo {
    override val appId: String = BuildConfig.APPLICATION_ID
}
