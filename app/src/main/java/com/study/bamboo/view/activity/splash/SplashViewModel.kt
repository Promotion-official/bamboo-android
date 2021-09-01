package com.study.bamboo.view.activity.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.bamboo.data.network.models.user.GetVerifyDTO
import com.study.bamboo.data.network.models.user.postcreate.PostCreateResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    //verify 값 받아오기
    val getVerifyResponse: LiveData<GetVerifyDTO> get() = _getVerifyResponse
    private val _getVerifyResponse = MutableLiveData<GetVerifyDTO>()


    //verify 호출로 id와 질문 가져오기
    fun callGetVerifyAPI() = viewModelScope.launch {
        userRepository.getVerify().let { response ->
            if (response.isSuccessful)
                _getVerifyResponse.value = response.body()
            else
                Log.d("로그", "response.errorBody() : ${response.errorBody()}")
        }

    }

}