package com.example.presentation.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.base.base.base.BaseActivity
import com.example.data.utils.DataStoreManager
import com.example.presentation.R
import com.example.presentation.databinding.FragmentSignInBinding
import com.example.presentation.view.admin.AdminActivity
import com.example.presentation.view.admin.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

//todo
// base 모듈 만들어서 login, splash  빼고 옮겨야함
@AndroidEntryPoint
class SignInFragment : BaseActivity<FragmentSignInBinding>(R.layout.fragment_sign_in) {
    @Inject
    lateinit var dataStore: DataStoreManager
    private val getGoogleLogin =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            Log.d("TAG", "${result.resultCode}: ")
                val intent: Intent = result.data!!
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(intent)
                try {
                    val account = task.getResult(ApiException::class.java)
                    viewModel.postLogin(account.idToken.toString())
                } catch (e: ApiException) {
                    Log.d("TAG", "onCreate: ${e.message}")
                    throw e
                }


        }
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.fragment = this@SignInFragment

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.client_id))
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this@SignInFragment, gso)

        with(viewModel) {
            isLoading.observe(this@SignInFragment) {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE

                }
            }
            isLoginResult.observe(this@SignInFragment) {
                if (it?.isAdmin == true) {
                    startActivity(Intent(this@SignInFragment, MainActivity::class.java))
                    saveToken(it.accessToken)
                } else {
                    startActivity(Intent(this@SignInFragment, AdminActivity::class.java))
                }
            }
            isFailure.observe(this@SignInFragment) {
                Toast.makeText(this@SignInFragment, it, Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun saveToken(token: String) = lifecycleScope.launch { dataStore.saveToken(token) }

    fun startGoogleLogin() {
        getGoogleLogin.launch(mGoogleSignInClient!!.signInIntent)
    }

}