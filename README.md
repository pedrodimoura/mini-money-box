# The Brief:

The Mini Moneybox was developed using MVVM approach and some concepts of Clean Architecture and Clean Coding.

The app is only modularized in directories inside the `app` module. Depending on the case, I prefer to using modularization with Android Modules and Libraries.

There are three main directories on the project: `common`, `features` and `ui`

`common` is the directory that I have used to put some Interfaces, Abstract Classes, Classes, Extensions, etc, that should be shared with all features, e.g. `HttpClient.RetrofitClient`, `Storage.Preferences`, `Storage.Database`, `SessionRepositoryImpl` etc.

`features`: inside this directory you can find the main features: `login` and `account`.

- `login`: this module has all resources, repositories, datasources, etc, related to the login feature.
- `account`: this module has all resources, repositories, datasources, etc, related to the account feature. e.g. products and account list, quick add, etc.

Inside each one I have followed this directory tree:

    features/login
    ├── data
        └── datasource
       		└── remote #LoginRemoteDatasource
        	└── local #LoginLocalDatasource
        └── repository #LoginRepositoryImpl
    ├── domain
        └── usecases #AuthenticateUseCase
        └── models
        └── repository #LoginRepository interface
    ├── presentation
        └── viewmodel #LoginViewModel
        └── fragment #LoginFragment
        └── activity #LoginActivity (application and navigation entrypoint)

You can find more directories than these listed above, but, usually, these are the base directories I have been using lately.

Between Screen and ViewModel I have used `UIState` and `UIAction` to manage how the UI should handle data.

For example, when the view needs to switch view visibility from Visible to Invisible a `UIState` is emitted through `LiveData` to the Activity/Fragment creating a copy from itself just changing the specific state that needs to be changed.

Example:
```
fun doStuff() {
	setState { state -> state.hideLoading() }
	viewModelScope.launch {
		runCatching {
			useCase.invoke()
		}.onSuccess { result ->
			sendAction { UIAction.DoStuff(result) }
		}.onFailure { thr ->
			sendAction { UIAction.ShowFailureMessage(thr.message) }
		}
	}
	setState { state -> state.hideLoading() }
}

[...]

data class MyState(private val isLoading: Boolean = false) : UIState {
	fun showLoading() = this.copy(isLoading = true)
	fun hideLoading() = this.copy(isLoading = false)
}
```

`UIAction` dispatches to the UI an action e.g. show user information on a View.

Example:
```
fun doStuff() {
	setState { state -> state.hideLoading() }
	viewModelScope.launch {
		runCatching {
			useCase.invoke()
		}.onSuccess { result ->
			sendAction { UIAction.DoStuff(result) }
		}.onFailure { thr ->
			sendAction { UIAction.ShowFailureMessage(thr.message) }
		}
	}
	setState { state -> state.hideLoading() }
}

[...]

@AndroidEntryPoint
class MyUI : Fragment() {
	// view binding and viewmodel injection here....
	override fun onResume() {
		onAction(viewModel) { action ->
			when (action) {
				is UIAction.ShowUserInformationOnUI -> showUserInformation(action.userInfo)
				is UIAction.OpenErrorScreen -> openErrorScreen(action.errorMessage)
			}
		}
	}
}
```
For dependency injection, I have used Dagger Hilt. In the past 2 years, all projects that I have been working on were built using Koin, which is basically a Service Locator, or pure Dagger. I like both, but I am really enjoying how Google has improved Dagger's usability.

For network request I am using Retrofit + OkHttpClient + Gson.

To manage token expiration, WorkManager.

For unit tests: JUnit4 + MockK + MockWebServer. I also have experience using jUnit 5. I did not use jUnit 5 on this project for convenience only.
For Automated Tests: Espresso + MockWebServer + a little robot pattern to help me testing UI.