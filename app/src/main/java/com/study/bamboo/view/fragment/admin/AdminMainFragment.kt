package com.study.bamboo.view.fragment.admin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope

import androidx.recyclerview.widget.LinearLayoutManager
import com.study.bamboo.R
import com.study.bamboo.adapter.AdminHomeItemAdapter
import com.study.bamboo.adapter.Situation
import com.study.bamboo.databinding.FragmentAdminMainBinding
import com.study.bamboo.utils.ViewModel.signInViewModel
import com.study.bamboo.view.activity.signin.SignInViewModel
import com.study.bamboo.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AdminMainFragment : BaseFragment<FragmentAdminMainBinding>(R.layout.fragment_admin_main) {

    companion object {
        const val TAG = "AdminMainFragment"
    }


    private val viewModel: AdminViewModel by viewModels()
    private val tokenViewModel: SignInViewModel by viewModels()

    private val acceptAdapter: AdminHomeItemAdapter by lazy {

        AdminHomeItemAdapter(Situation.ACCEPT)

    }
    private val deleteAdapter: AdminHomeItemAdapter by lazy {

        AdminHomeItemAdapter(Situation.DELETE)

    }
    private val rejectAdapter: AdminHomeItemAdapter by lazy {

        AdminHomeItemAdapter(Situation.REJECT)

    }
    private val waitingAdapter: AdminHomeItemAdapter by lazy {

        AdminHomeItemAdapter(Situation.WAITING)

    }

    override fun FragmentAdminMainBinding.onCreateView() {
        setItemAdapter(acceptAdapter)
        spinnerContact()
        //spinner
        binding.activitySpinner.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.AdminItemList,
            R.layout.admin_spinner_item
        )

        tokenViewModel.callAdminLoginAPI("#promotion")


        //처음 수락 상태 게시물 가져오기
        setItemAdapter(acceptAdapter)
        signInViewModel.adminLoginResponse.value?.let {
            observeNetwork(
                it,
                20,
                "60b8407473d81a1b4cc591a5",
                "ACCEPTED"
            )
        }

        viewModel.getPostData.observe(viewLifecycleOwner, {

            acceptAdapter.setItemList(it!!)


            waitingAdapter.setItemList(it!!)
            deleteAdapter.setItemList(it!!)
            rejectAdapter.setItemList(it!!)

        })
    }

    override fun FragmentAdminMainBinding.onViewCreated() {


    }

    private fun spinnerContact() {
        var token = ""
        binding.activitySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    tokenViewModel.adminLoginResponse.observe(requireActivity(), {
                        Log.d(TAG, "onItemSelected: $token")
                        if (token.isEmpty())
                            token = it
                    })

                    when (position) {
                        0 -> {

                            setItemAdapter(acceptAdapter)
                            observeNetwork(token, 20, "60b8407473d81a1b4cc591a5", "ACCEPTED")

                        }
                        1 -> {

                            setItemAdapter(waitingAdapter)
                            observeNetwork(token, 20, "60b8407473d81a1b4cc591a5", "PENDING")

                        }
                        2 -> {

                            setItemAdapter(rejectAdapter)
                            observeNetwork(token, 20, "60b8407473d81a1b4cc591a5", "REJECTED")

                        }
                        3 -> {

                            setItemAdapter(deleteAdapter)
                            observeNetwork(token, 20, "60b8407473d81a1b4cc591a5", "DELETED")

                        }
                        else -> {
                        }
                    }
                }
            }

    }

    fun observeNetwork(token: String, count: Int, cursor: String, status: String) {
        when (status) {
            "ACCEPTED" -> lifecycleScope.launch {
                viewModel.getAcceptedPost(count, cursor, status)
            }
            "PENDING" -> lifecycleScope.launch {
                viewModel.getPendingPost(token, count, cursor, status)
            }
            "REJECTED" -> lifecycleScope.launch {
                viewModel.getRejectedPost(token, count, cursor, status)
            }
            "DELETED" -> lifecycleScope.launch {
                viewModel.getDeletedPost(token, count, cursor, status)
            }

        }

    }


    fun setItemAdapter(adapter: AdminHomeItemAdapter) {
        binding.postRecyclerView.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

}
