package ru.netology.nmedia.ui.login

import android.app.Application
import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.dto.MediaUpload
import ru.netology.nmedia.model.FeedModelState
import ru.netology.nmedia.model.PhotoModel
import ru.netology.nmedia.util.SingleLiveEvent
import javax.inject.Inject

private val noPhoto = PhotoModel()

data class SignOutInfo(
    val name    : String,
    val userName: String,
    val password: String,
    val confirmPassword : String
)

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SignOutViewModel@Inject constructor(
    private val auth: AppAuth
) : ViewModel() {
    @Inject
    lateinit var repository: RegisterRepositoryInterface

    private val _photo = MutableLiveData(noPhoto)
    val photo: LiveData<PhotoModel>
        get() = _photo

    private val _singOutData = MutableLiveData(
        SignOutInfo("","","",""))

    val singOutData: LiveData<SignOutInfo>
        get() = _singOutData

    private val _registerState = SingleLiveEvent<RegisterState>()
    val registerState: LiveData<RegisterState>
        get() = _registerState

    private val _loginState = SingleLiveEvent<LoginState>()
    val loginState: LiveData<LoginState>
        get() = _loginState

    fun changePhoto(uri: Uri?) {
        _photo.value = PhotoModel(uri)
    }

    fun register(name    : String,
                 userName: String,
                 password: String)
    {
        viewModelScope.launch {
            try {
                val result = when (_photo.value) {
                    noPhoto -> repository.registerNoPhoto(userName, password, name)
                    else -> _photo.value?.uri?.let { uri ->
                        repository.registerWithPhoto(
                            userName, password, name, MediaUpload(uri.toFile()))
                    }
                }
                _registerState.value = RegisterState()
                if (result != null) {
                    auth.setAuth(result.id, result.token)
                }
            } catch (e: Exception) {
                _registerState.value = RegisterState(error = true)
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val result = repository.login(username, password)
                _loginState.value = LoginState(isLogin = true)
                auth.setAuth(result.id, result.token)
            } catch (e: Exception) {
                _loginState.value = LoginState(error = true)
            }
        }
    }

}