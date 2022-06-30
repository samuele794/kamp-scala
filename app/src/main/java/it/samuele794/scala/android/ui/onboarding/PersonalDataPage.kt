package it.samuele794.scala.android.ui.onboarding

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptionsBuilder
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import it.samuele794.scala.android.R
import it.samuele794.scala.android.ui.destinations.BodyDataPageDestination
import it.samuele794.scala.android.ui.destinations.TrainerLocationOperationPageDestination
import it.samuele794.scala.android.ui.navigation.OnBoardingNavGraph
import it.samuele794.scala.android.ui.theme.ScalaAppTheme
import it.samuele794.scala.model.AccountType
import it.samuele794.scala.model.maps.Place
import it.samuele794.scala.resources.SharedRes
import it.samuele794.scala.viewmodel.onboarding.OnBoardingVMI
import it.samuele794.scala.viewmodel.onboarding.OnBoardingViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

@OptIn(ExperimentalMaterialApi::class)
@OnBoardingNavGraph(start = true)
@Destination
@Composable
fun PersonalDataPage(
    navigator: DestinationsNavigator,
    onBoardingViewModel: OnBoardingVMI
) {

    val context = LocalContext.current

    val uiState by onBoardingViewModel.uiState.collectAsState()
    val nextEnabled = onBoardingViewModel.personalDataNextEnabled()
    var dropExpanded by remember { mutableStateOf(false) }

    val datePickerDialog = DatePickerDialog(context)
        .apply {
            datePicker.maxDate = onBoardingViewModel.birthdayMinDate.toEpochMilliseconds()
            setOnDateSetListener { _, year, month, date ->
                onBoardingViewModel.updateBirthDate(LocalDate(year, Month(month + 1), date))
            }
        }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {

        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
            expanded = dropExpanded,
            onExpandedChange = { dropExpanded = !dropExpanded }) {

            //TODO ADD STYLE FOR FAKE DISABLED TEXTFIELD
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                enabled = false,
                value = stringResource(id = uiState.accountType.accountName.resourceId),
                onValueChange = { },
                label = { Text(stringResource(id = SharedRes.strings.account_type.resourceId)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropExpanded)
                }
            )

            ExposedDropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = dropExpanded,
                onDismissRequest = { dropExpanded = false }) {
                onBoardingViewModel.getAccountTypes().forEach { accountType ->
                    val name = stringResource(id = accountType.accountName.resourceId)

                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onBoardingViewModel.updateAccountType(accountType)
                            dropExpanded = false
                        }
                    ) {
                        Text(text = name)
                    }
                }
            }
        }


        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.name,
            onValueChange = {
                onBoardingViewModel.updateName(it)
            },
            label = { Text(text = stringResource(id = R.string.user_name)) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.surname,
            onValueChange = {
                onBoardingViewModel.updateSurname(it)
            },
            label = { Text(text = stringResource(id = R.string.user_surname)) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    datePickerDialog.show()
                },
            value = uiState.getFormattedBirthDate(),
            onValueChange = {},
            label = { Text(text = stringResource(id = R.string.select_birth_day)) },
            trailingIcon = {
                Icon(imageVector = Icons.Outlined.CalendarToday, contentDescription = "")
            },
            readOnly = true,
            enabled = false
        )



        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                enabled = nextEnabled,
                onClick = {
                    when (uiState.accountType) {
                        AccountType.USER -> {
                            navigator.navigate(BodyDataPageDestination)
                        }

                        AccountType.TRAINER -> {
                            navigator.navigate(TrainerLocationOperationPageDestination)
                        }
                        else -> Unit
                    }
                }
            ) { Text(text = stringResource(id = R.string.next)) }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PersonalDataPagePreview() {
    ScalaAppTheme {
        PersonalDataPage(
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
            onBoardingViewModel = object : OnBoardingVMI {
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
            }
        )
    }
}