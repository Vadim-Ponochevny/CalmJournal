package com.vpnch.calmjournalapp

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseClass: AppEntryUseCases
) : ViewModel() {

    var splashCondition by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf(Route.AppStartNavigation.route)
        private set

    init {
        appEntryUseClass.readAppEntry().onEach { shouldStartFromHomeScreen ->
            if(shouldStartFromHomeScreen) {
                startDestination = Route.NewsNavigation.route
            } else {
                startDestination = Route.AppStartNavigation.route
            }
            delay(300)
            splashCondition = false
        }.launchIn(viewModelScope)

    }
}