package com.bangkit.storyappdicoding.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.storyappdicoding.R
import com.bangkit.storyappdicoding.databinding.ActivityLoginBinding
import com.bangkit.storyappdicoding.network.api.ApiConfig
import com.bangkit.storyappdicoding.ui.main.MainActivity
import com.bangkit.storyappdicoding.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("app-data", MODE_PRIVATE)

        val auth = sharedPreferences.getString("authToken", null)
        if(auth != null) {
            ApiConfig.setAuth(auth)
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        supportActionBar?.hide()
        setupAction()
        playAnimation()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditTextLayout.text.toString()
            val password = binding.passwordEditTextLayout.text.toString()

            loginViewModel.getLoginInfo(email, password)

            loginViewModel.errorMessage.observe(this) { errorMessage ->
                if(errorMessage != null) {
                    val message = if(errorMessage == LoginViewModel.INVALID_CREDENTIALS) R.string.invalid_credentials else R.string.failure
                    Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
                    loginViewModel.clearErrorMessage()
                }
            }

            loginViewModel.login.observe(this) {
                if(!it.error) {
                    val editor = sharedPreferences.edit()
                    editor.putString("authToken", it.loginResult.token)
                    editor.putString("name", it.loginResult.name)
                    editor.putString("userId", it.loginResult.userId)
                    editor.apply()

                    Toast.makeText(this@LoginActivity, "You are signed in!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else
                    Toast.makeText(this, getString(R.string.invalid_credentials), Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvRegisterNow.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val message = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
        val donthaveacc = ObjectAnimator.ofFloat(binding.tvDontHaveAccount, View.ALPHA, 1f).setDuration(500)
        val registernow = ObjectAnimator.ofFloat(binding.tvRegisterNow, View.ALPHA, 1f).setDuration(500)


        AnimatorSet().apply {
            playSequentially(
                title,
                message,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login,
                donthaveacc,
                registernow,)
            startDelay = 500
        }.start()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}