package com.study.bamboo.view.fragment.admin

import com.study.bamboo.data.network.user.AdminApi
import javax.inject.Inject


class AdminRepository @Inject constructor(
    private val adminApi: AdminApi
) {
    suspend fun deletePost( token:String,reason: String, id: String) =
        adminApi.deletePost(token ,arg = id, reason)

    suspend fun patchPost(token: String, id: String, status:  HashMap<String, String>) =
        adminApi.patchPost(token, id, status)

    suspend fun acceptPatchPost(
        token: String,
        id: String,
        bodyMap: HashMap<String, String>,

    ) =
        adminApi.acceptPatchPost(token, id, bodyMap)

    suspend fun transferAdminLogin(password: HashMap<String, String>) = adminApi.transferAdminLogin(password)
}