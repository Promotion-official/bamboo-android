package com.study.bamboo.data.network.models.user

interface UserPostDTOBase {
    val content: String
    val createdAt: Long
    val id: String
    val number: Int
    val status: String
    val tag: String
    val title: String
}