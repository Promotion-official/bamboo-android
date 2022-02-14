package com.study.presentation

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

object PostBindingAdapter {

    //admin


    @BindingAdapter("app:day")
    @JvmStatic
    fun getDay(text: TextView, day: String) {

        text.text = day.substring(0..10)

    }


    @BindingAdapter("app:imgClicked")
    @JvmStatic
    fun ImageView.leafClicked(clicked: Boolean) {

        if (clicked) {
            this.setBackgroundResource(R.drawable.click_leaf)
        } else {
            this.setBackgroundResource(R.drawable.no_click_leaf)
        }
    }

    @BindingAdapter("app:leafNumber")
    @JvmStatic
    fun TextView.leafNumber(number: Int) {
        this.text = number.toString()
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter("app:getTag")
    @JvmStatic
    fun getTag(text: TextView, tag: String) {

        text.text = "#${tag}"
    }

    @BindingAdapter("app:acceptStatus")
    @JvmStatic
    fun getAcceptStatus(text: TextView, status: Int) {

        text.text = "#${status}번째 알고리즘"
    }

    @BindingAdapter("app:deleteStatus")
    @JvmStatic
    fun getDeleteStatus(text: TextView, status: Int) {

        text.text = "#${status}번째 삭제요청"
    }

    @BindingAdapter("app:rejectStatus")
    @JvmStatic
    fun getRejectStatus(text: TextView, status: Int) {

        text.text = "#${status}번째 거절됨"
    }

    @BindingAdapter("app:pendingStatus")
    @JvmStatic
    fun getPendingStatus(text: TextView, status: Int) {

        text.text = "#${status}번째 대기중"
    }


}