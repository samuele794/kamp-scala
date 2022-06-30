package it.samuele794.scala.android.ui.onboarding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptionsBuilder
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import dev.icerock.moko.resources.compose.stringResource
import it.samuele794.scala.android.ui.destinations.OnBoardSavePageDestination
import it.samuele794.scala.android.ui.destinations.TrainerLocationSearchPageDestination
import it.samuele794.scala.android.ui.navigation.OnBoardingNavGraph
import it.samuele794.scala.android.ui.theme.ScalaAppTheme
import it.samuele794.scala.model.AccountType
import it.samuele794.scala.model.maps.Place
import it.samuele794.scala.resources.SharedRes
import it.samuele794.scala.utils.toLatLng
import it.samuele794.scala.viewmodel.onboarding.OnBoardingVMI
import it.samuele794.scala.viewmodel.onboarding.OnBoardingViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalMaterialApi::class)
@OnBoardingNavGraph
@Destination
@Composable
fun TrainerLocationOperationPage(
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<TrainerLocationSearchPageDestination, Place>,
    onBoardingVMI: OnBoardingVMI
) {
    val uiState by onBoardingVMI.uiState.collectAsState()
    var showCasePlace by remember {
        mutableStateOf<Place?>(null)
    }

    val state = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    ) {
        if (it == ModalBottomSheetValue.Hidden) {
            showCasePlace = null
        }
        true
    }

    val coroutineScope = rememberCoroutineScope()

    resultRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Value -> {
                onBoardingVMI.addTrainerPlace(result.value)
            }
            else -> Unit
        }
    }

    ModalBottomSheetLayout(
        sheetState = state,
        sheetContent = {
            PlaceBottomSheet(showCasePlace)
        }
    ) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    navigator.navigate(TrainerLocationSearchPageDestination())
                }) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                }
            },
            bottomBar = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    if (uiState.trainerPlaces.isNotEmpty()) {
                        Button(
                            modifier = Modifier.align(Alignment.Center),
                            onClick = {
                                navigator.navigate(OnBoardSavePageDestination) {
                                    popUpTo(OnBoardSavePageDestination) { inclusive = true }
                                }
                            }
                        ) {
                            Text(text = stringResource(SharedRes.strings.next))
                        }
                    }
                }
            }
        ) {
            PlaceList(
                trainerPlaces = uiState.trainerPlaces,
                itemClicked = {
                    coroutineScope.launch {
                        showCasePlace = it
                        state.show()
                    }
                },
                onRemoveClicked = {
                    onBoardingVMI.removeTrainerPlace(it)
                }
            )
        }
    }
}

@Composable
fun PlaceBottomSheet(showCasePlace: Place?) {
    Box(modifier = Modifier.fillMaxHeight(0.2f)) {
        if (showCasePlace != null) {
            GoogleMap(
                cameraPositionState = CameraPositionState(
                    CameraPosition.fromLatLngZoom(
                        showCasePlace.latLng.toLatLng(),
                        12f
                    )
                ),
                uiSettings = MapUiSettings(
                    compassEnabled = false,
                    indoorLevelPickerEnabled = false,
                    mapToolbarEnabled = false,
                    myLocationButtonEnabled = false,
                    rotationGesturesEnabled = false,
                    scrollGesturesEnabled = false,
                    scrollGesturesEnabledDuringRotateOrZoom = false,
                    tiltGesturesEnabled = false,
                    zoomControlsEnabled = false,
                    zoomGesturesEnabled = false,
                )
            ) {
                Marker(
                    state = rememberMarkerState(position = showCasePlace.latLng.toLatLng()),
                    title = showCasePlace.placeName
                )
            }
        }
    }
}

@Composable
fun PlaceList(
    trainerPlaces: List<Place>,
    itemClicked: (Place) -> Unit,
    onRemoveClicked: (Place) -> Unit
) {
    LazyColumn {
        items(trainerPlaces) {
            Row(
                modifier = Modifier
                    .clickable {
                        itemClicked(it)
                    }
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    modifier = Modifier.weight(0.9f),
                    text = it.placeName
                )
                Icon(
                    modifier = Modifier
                        .weight(0.1F)
                        .clickable {
                            onRemoveClicked(it)
                        },
                    imageVector = Icons.Filled.Clear,
                    contentDescription = ""
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TrainerLocationOperationPagePreview() {
    ScalaAppTheme {
        TrainerLocationOperationPage(
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
            resultRecipient = object :
                ResultRecipient<TrainerLocationSearchPageDestination, Place> {

                @Composable
                override fun onNavResult(listener: (NavResult<Place>) -> Unit) = Unit

                @Composable
                override fun onResult(listener: (Place) -> Unit) = Unit
            },
            onBoardingVMI = object : OnBoardingVMI {
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

                override fun saveAccount() {
                    TODO("Not yet implemented")
                }
            }
        )
    }
}