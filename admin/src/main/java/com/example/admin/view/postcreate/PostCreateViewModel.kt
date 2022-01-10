package com.study.bamboo.view.activity.postcreate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.bamboo.data.network.models.user.GetVerifyDTO
import com.study.bamboo.data.network.models.user.postcreate.PostCreateRequest
import com.study.bamboo.data.network.models.user.postcreate.PostCreateResponse
import com.study.bamboo.data.network.models.user.postcreate.Verifier
import com.study.bamboo.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PostCreateViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {


    //게시물 업로드 후 리스폰스 받아오기
    val postCreateResponse: LiveData<PostCreateResponse?> get() = _postCreateResponse
    private val _postCreateResponse = MutableLiveData<PostCreateResponse?>()

    //게시물 업로드 후 리스폰스 받아오기
    val postCreateSuccess: LiveData<Boolean?> get() = _postCreateSuccess
    private val _postCreateSuccess = MutableLiveData<Boolean?>()

    val choiceTag: LiveData<String> get() = _choiceTag
    private val _choiceTag = MutableLiveData<String>()

    val getVerifyResponse: LiveData<Response<GetVerifyDTO?>?> get() = _getVerifyResponse
    private val _getVerifyResponse = MutableLiveData<Response<GetVerifyDTO?>?>()

    init {

    }


    //스피너로 tag변경할때 tag 값 변경
    fun setChoiceTag(tag: String) {
        _choiceTag.value = tag
    }


    //게시물 올리기
    fun callPostCreateAPI(
        title: String,
        content: String,
        tag: String,
        questionId: String,
        answer: String
    ) = viewModelScope.launch {
        val verifier = Verifier(questionId, answer)
        val postCreateRequest = PostCreateRequest(
            title,
            content,
            tag,
            verifier
        )
        userRepository.transferPostCreate(postCreateRequest).let { response ->
            if (response.isSuccessful)
                _postCreateResponse.value = response.body()
        }
    }

    fun setPostCreateSuccess(postCreateSuccess: Boolean?) {
        _postCreateSuccess.value = postCreateSuccess
    }

    fun setGetVerifyResponse(getVerifyResponse: Response<GetVerifyDTO?>?) {
        _getVerifyResponse.value = getVerifyResponse
    }

    fun setPostCreateResponse(postCreateResponse: PostCreateResponse?) {
        _postCreateResponse.value = postCreateResponse
    }

    fun callGetVerify() = viewModelScope.launch {
        userRepository.getVerify().let { response ->
            if (response.isSuccessful) {
                _getVerifyResponse.value = response
            }
        }
    }
}