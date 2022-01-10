package com.example.data.network.user


import com.example.data.base.BaseDataResponse
import com.example.data.base.BaseResponse
import com.example.data.model.user.response.GetVerifyResponse
import com.example.data.model.user.response.AlgorithmCreateResponse
import com.example.data.model.user.request.AlgorithmCreateRequest
import com.example.data.model.user.request.EmojiRequest
import com.example.data.model.user.request.ReportRequest
import com.example.data.model.user.response.ReportResponse
import com.example.data.model.user.response.SignResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface UserApi {


    // 게시물 생성
    @POST("algorithm/")
    fun algorithmCreate(
        @Body request: AlgorithmCreateRequest
    ): Single<BaseDataResponse<AlgorithmCreateResponse>>


    @GET("verify")
    fun getVerify(): Single<BaseDataResponse<GetVerifyResponse>>


    @PATCH("post/{id}/report")
    fun patchReport(
        @Path("id") id: String,
        @Body body: ReportRequest,
    ): Single<BaseDataResponse<ReportResponse>>


    @POST("login")
    fun postLogin(
        @Header("Authorization") Authorization: String,
    ): Single<BaseDataResponse<SignResponse>>


    @DELETE("logout")
    fun deleteLogOut(
        @Header("Authorization") authorization: String
    ): Single<BaseResponse>


    // 이모지
    fun postEmoji(
        @Header("Authorization") authorization: String,
        @Body body: EmojiRequest
    ): Single<BaseResponse>

    fun deleteEmoji(
        @Header("Authorization") authorization: String,
        @Body body: EmojiRequest
    ): Single<BaseResponse>


}