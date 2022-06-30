package co.touchlab.kampkit

//import org.koin.test.check.checkModules
import org.koin.core.context.stopKoin
import kotlin.test.AfterTest
import kotlin.test.Test

class KoinTest {
    @Test
    fun checkAllModules() {
//        initKoinIos(
//            userDefaults = NSUserDefaults.standardUserDefaults,
//            appInfo = TestAppInfo,
//            doOnStartup = { }
//        ).checkModules {
//            withParameters<Logger> { parametersOf("TestTag") }
//        }
    }

    @AfterTest
    fun breakdown() {
        stopKoin()
    }
}
