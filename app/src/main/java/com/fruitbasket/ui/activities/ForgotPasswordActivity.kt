package com.fruitbasket.ui.activities

import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.fruitbasket.R
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class ForgotPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setupActionBar()

        val btn_submit = findViewById<Button>(R.id.btn_submit)
        btn_submit.setOnClickListener {

            val et_email = findViewById<EditText>(R.id.et_email)
            val email: String = et_email.text.toString().trim { it <= ' ' }

            if (email.isEmpty()) {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
            } else {

                showProgressDialog(resources.getString(R.string.please_wait))

                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->

                        hideProgressDialog()

                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                                resources.getString(R.string.email_sent_success),
                                Toast.LENGTH_LONG
                            ).show()

                            finish()
                        } else {
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }
            }
        }
    }

    private fun setupActionBar() {

        val toolbar_forgot_password_activity = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_forgot_password_activity)
        setSupportActionBar(toolbar_forgot_password_activity)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_forgot_password_activity.setNavigationOnClickListener { onBackPressed() }
    }
}