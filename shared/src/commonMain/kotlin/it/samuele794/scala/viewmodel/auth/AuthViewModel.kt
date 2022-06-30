package it.samuele794.scala.viewmodel.auth

import co.touchlab.kermit.Logger
import it.samuele794.scala.viewmodel.base.ViewModel
import kotlinx.coroutines.launch

class AuthViewModel(
    nativeAuth: NativeAuth,
    private val logger: Logger
) : ViewModel() {

//    val isLoggedFlow = Firebase.auth.authStateChanged

    init {
        viewModelScope.launch {
//            nativeAuth.credentialFlow.collect {
//                startFirebaseAuth(it)
//            }
        }
    }

    private suspend fun startFirebaseAuth(/*credential: AuthCredential*/) {
//        withContext(Dispatchers.Default) {
//            runCatching {
//                Firebase.auth.signInWithCredential(credential).user!!
//            }
//        }
    }

    suspend fun loginByEmailPass(email: String, password: String) {
//        withContext(Dispatchers.Default) {
//            val userResult = runCatching {
//                Firebase.auth.signInWithEmailAndPassword(email, password).user!!
//            }
//
//            if (userResult.isFailure) {
//                logger.i("Login By Email Failed, Create Account")
//                Firebase.auth.createUserWithEmailAndPassword(email, password)
//
//                return@withContext
//            }
//
//            logger.i("Login By Email Success")
//
//        }
    }
}

expect class NativeAuth {
//    val credentialFlow: Flow<AuthCredential>
}