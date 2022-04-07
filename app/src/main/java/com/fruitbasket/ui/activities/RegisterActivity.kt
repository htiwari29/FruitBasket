package com.fruitbasket.ui.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatCheckBox
import com.fruitbasket.R
import com.fruitbasket.firestore.FirestoreClass
import com.fruitbasket.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@Suppress("DEPRECATION")
class RegisterActivity : BaseActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setupActionBar()

        val btn_register = findViewById<Button>(R.id.btn_register)
        btn_register.setOnClickListener {
            registerUser()
        }

        val tv_login = findViewById<TextView>(R.id.tv_login)
        tv_login.setOnClickListener{
            onBackPressed()
        }
    }
    private fun setupActionBar() {

        val toolbar_register_activity = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_register_activity)
        setSupportActionBar(toolbar_register_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        toolbar_register_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun validateRegisterDetails(): Boolean {
        val et_first_name = findViewById<EditText>(R.id.et_first_name)
        val et_last_name = findViewById<EditText>(R.id.et_last_name)
        val et_email = findViewById<EditText>(R.id.et_email)
        val et_password = findViewById<EditText>(R.id.et_password)
        val et_confirm_password = findViewById<EditText>(R.id.et_confirm_password)
        val cb_terms_and_condition = findViewById<AppCompatCheckBox>(R.id.cb_terms_and_condition)
        return when {
            TextUtils.isEmpty(et_first_name.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }

            TextUtils.isEmpty(et_last_name.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }

            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) || !et_email.text.toString().matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))-> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) || et_password.text.toString().length < 6-> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            TextUtils.isEmpty(et_confirm_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_confirm_password), true)
                false
            }

            et_password.text.toString().trim { it <= ' ' } != et_confirm_password.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_password_and_confirm_password_mismatch), true)
                false
            }
            !cb_terms_and_condition.isChecked -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_agree_terms_and_condition), true)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun registerUser() {
        val et_email = findViewById<EditText>(R.id.et_email)
        val et_password = findViewById<EditText>(R.id.et_password)
        val et_first_name = findViewById<EditText>(R.id.et_first_name)
        val et_last_name = findViewById<EditText>(R.id.et_last_name)
        if (validateRegisterDetails()) {

            showProgressDialog(resources.getString(R.string.please_wait))

            val email: String = et_email.text.toString().trim { it <= ' ' }
            val password: String = et_password.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!

                        val user = User(
                            firebaseUser.uid,
                            et_first_name.text.toString().trim { it <= ' ' },
                            et_last_name.text.toString().trim { it <= ' ' },
                            et_email.text.toString().trim { it <= ' ' }
                        )
                        FirestoreClass().registerUser(this@RegisterActivity, user)

                    } else {
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }
    fun userRegistrationSuccess() {

        hideProgressDialog()

        Toast.makeText(
            this@RegisterActivity,
            resources.getString(R.string.register_success),
            Toast.LENGTH_SHORT
        ).show()

        FirebaseAuth.getInstance().signOut()
        finish()
    }
}

