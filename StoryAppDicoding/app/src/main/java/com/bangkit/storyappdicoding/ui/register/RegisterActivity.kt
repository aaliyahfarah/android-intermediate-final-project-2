package com.bangkit.storyappdicoding.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.storyappdicoding.R
import com.bangkit.storyappdicoding.databinding.ActivityRegisterBinding
import com.bangkit.storyappdicoding.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private val registerViewModel by viewModels<RegisterViewModel>()
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupAction()
        playAnimation()
    }

    private fun setupAction() {
        binding.registerButton.setOnClickListener {
            val name = binding.nameEditTextLayout.text.toString()
            val email = binding.emailEditTextLayout.text.toString()
            val password = binding.passwordEditTextLayout.text.toString()
            registerViewModel.getRegisterResponse(name, email, password)

            registerViewModel.register.observe(this) {
                if(!it.error) {
                    Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }

            registerViewModel.errorMessage.observe(this) { errorMessage ->
                if(errorMessage != null) {

                    val message = if(errorMessage == RegisterViewModel.LOGIN_FAILURE) {
                        getString(R.string.failure)
                    } else {
                        errorMessage
                    }
                    Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
                    registerViewModel.clearErrorMessage()
                }
            }
        }

        binding.tvsigninnow.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        registerViewModel.isLoading.observe(this) {
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
        val nameTextView = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(500)
        val nameEditTextLayout = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(500)
        val haveacc = ObjectAnimator.ofFloat(binding.tvHaveAnAccount, View.ALPHA, 1f).setDuration(500)
        val loginnow = ObjectAnimator.ofFloat(binding.tvsigninnow, View.ALPHA, 1f).setDuration(500)


        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                register,
                haveacc,
                loginnow,
            )
            startDelay = 500
        }.start()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}