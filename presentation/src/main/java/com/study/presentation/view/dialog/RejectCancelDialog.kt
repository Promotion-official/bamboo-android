package com.study.presentation.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.study.presentation.adapter.AdminAdapter.Companion.ACCEPTED
import com.study.presentation.adapter.AdminAdapter.Companion.REJECTED
import com.study.presentation.view.admin.AdminViewModel
import com.study.domain.model.admin.request.SetStatusEntity
import com.study.presentation.databinding.RejectCancelDialogBinding
import com.study.presentation.utils.setNavResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RejectCancelDialog : DialogFragment() {
    private var _binding: RejectCancelDialogBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<RejectCancelDialogArgs>()
    private val viewModel: AdminViewModel by viewModels()


    override fun onResume() {
        super.onResume()
        dialogCorner()
        initDialog()
    }

    private var token = ""

    fun dialogCorner() {
        //다이얼로그 백그라운드 삭제 -> 모서리 둥글게
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
    }

    private fun initDialog() {
        //디바이스 크기 확인후 커스텀 다이어로그 팝업 크기 조정
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
//        val deviceWidth = deviceSizeX
//        Log.d("로그", "acceptDialog : $deviceWidth")
//        params?.width = (deviceWidth * 0.9).toInt()


        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RejectCancelDialogBinding.inflate(inflater, container, false)





        binding.rejectCancelBtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE

            rejectDialog()
        }





        return binding.root
    }

    private fun rejectDialog() {

        viewModel.patchPost(
            token,
            args.auth,
            SetStatusEntity(ACCEPTED),

            )
        viewModel.isSuccess.observe(viewLifecycleOwner) {
            val denied = it?.isEmpty() == true
            binding.progressBar.isVisible = denied
            if (denied) return@observe

            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()

            setNavResult(data = REJECTED)
            findNavController().popBackStack()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "RejectCancelDialog"
    }
}