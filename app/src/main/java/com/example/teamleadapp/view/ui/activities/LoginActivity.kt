package com.example.teamleadapp.view.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.teamleadapp.R
import com.example.teamleadapp.data.remote.models.Status
import com.example.teamleadapp.view.ui.LoginViewModel
import com.example.teamleadapp.view.utils.hide
import com.example.teamleadapp.view.utils.show
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        loginButton.setOnClickListener {
            viewModel.checkUser(loginEditText.text.toString(), passwordEditText.text.toString())
        }
        bindViewModels()
    }

    private fun bindViewModels() {
        viewModel.authed.observe(this, Observer {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(applicationContext, "User not registered", Toast.LENGTH_LONG).show()
            }
        })

        viewModel.statusLiveData.observe(this, {
            when (it) {
                Status.SHOW_LOADING -> {
                    progressBar.show()
                    loginButtonIcon.hide()
                    loginButtonTxt.text = ""

                }
                Status.HIDE_LOADING -> {
                    progressBar.hide()
                    loginButtonIcon.show()
                    loginButtonTxt.text = "Log in"
                }
            }
        })
    }


}