package it.samuele794.scala.android.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptionsBuilder
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import it.samuele794.scala.android.R
import it.samuele794.scala.android.ui.destinations.OnBoardSavePageDestination
import it.samuele794.scala.android.ui.navigation.OnBoardingNavGraph
import it.samuele794.scala.android.ui.theme.ScalaAppTheme
import it.samuele794.scala.model.AccountType
import it.samuele794.scala.model.maps.Place
import it.samuele794.scala.viewmodel.onboarding.OnBoardingVMI
import it.samuele794.scala.viewmodel.onboarding.OnBoardingViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

@OnBoardingNavGraph
@Destination
@Composable
fun BodyDataPage(
    navigator: DestinationsNavigator,
    onBoardingViewModel: OnBoardingVMI
) {

    val uiState by onBoardingViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.height?.toString() ?: "",
            label = {
                Text(text = stringResource(id = R.string.height))
            },
            onValueChange = {
                onBoardingViewModel.updateHeight(it)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            trailingIcon = {
                Text(text = stringResource(id = R.string.label_cm))
            },
            singleLine = true
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.weight ?: "",
            label = {
                Text(text = stringResource(id = R.string.weight))
            },
            onValueChange = {
                onBoardingViewModel.updateWeight(it)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            trailingIcon = {
                Text(text = stringResource(id = R.string.label_kg))
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    navigator.navigate(OnBoardSavePageDestination)
                }
            ) { Text(text = stringResource(id = R.string.next)) }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BodyDataPagePreview() {
    ScalaAppTheme {
        BodyDataPage(
            navigator = object : DestinationsNavigator {
                override fun clearBackStack(route: String): Boolean = true

                override fun navigate(
                    route: String,
                    onlyIfResumed: Boolean,
                    builder: NavOptionsBuilder.() -> Unit
                ) = Unit

                override fun navigateUp(): Boolean = true

                override fun popBackStack(): Boolean = true

                override fun popBackStack(
                    route: String,
                    inclusive: Boolean,
                    saveState: Boolean
                ): Boolean = true
            },
            object : OnBoardingVMI {
                override val uiState: StateFlow<OnBoardingViewModel.UserDataUI>
                    get() = MutableStateFlow(OnBoardingViewModel.UserDataUI())

                override val accountCreateState: Flow<Boolean>
                    get() = MutableStateFlow(false)

                override fun updateName(name: String) = Unit

                override fun updateSurname(surname: String) = Unit

                override fun updateHeight(height: String) = Unit

                override fun updateWeight(weight: String) = Unit

                override fun personalDataNextEnabled(): Boolean = true

                override fun getAccountTypes(): Array<AccountType> = AccountType.values()
                override fun updateAccountType(accountType: AccountType) = Unit

                override fun updateBirthDate(localDate: LocalDate) = Unit
                override fun addTrainerPlace(place: Place) = Unit

                override fun removeTrainerPlace(place: Place) = Unit

                override fun saveAccount() = Unit
            })
    }
}