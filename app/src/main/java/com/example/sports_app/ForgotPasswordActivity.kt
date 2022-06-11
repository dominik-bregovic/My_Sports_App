package com.example.sports_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fotgot_password)
        val toRegister = findViewById<TextView>(R.id.tv_forgotpassword_login)
        val resetPassButton = findViewById<Button>(R.id.btn_forgotpassword_submit)

        toRegister.setOnClickListener{
            startActivity(Intent(this@ForgotPasswordActivity, LoginActivity::class.java))
        }

        resetPassButton.setOnClickListener{
            val email = findViewById<TextView>(R.id.et_forgotpassowrd_email).text.toString().trim() { it <= ' '}
            if (email.isEmpty()){
                Toast.makeText(
                    this@ForgotPasswordActivity,
                    "Please enter email",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener{ task ->
                        if (task.isSuccessful){
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                                "Email sent successfully to reset your password",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }else{
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                               task.exception!!.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }
            }
        }
    }
}