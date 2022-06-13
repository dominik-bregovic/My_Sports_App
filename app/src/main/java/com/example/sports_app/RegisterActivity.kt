package com.example.sports_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val button = findViewById<Button>(R.id.btn_register_register)
        val first = findViewById<TextView>(R.id.et_register_firstname)
        val last = findViewById<TextView>(R.id.et_register_lastname)
        val email = findViewById<TextView>(R.id.et_register_email)
        val pass = findViewById<TextView>(R.id.et_register_password)
        val confirmPass = findViewById<TextView>(R.id.et_register_confirmpassword)
        val toRegister = findViewById<TextView>(R.id.tv_register_login)
        toRegister.setOnClickListener{
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }

        button.setOnClickListener{
            when{
                TextUtils.isEmpty(email.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter mail",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(pass.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter password",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(first.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter firstname",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(last.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter lastname",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(pass.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter password",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(confirmPass.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter confirmation",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val emailText = email.text.toString().trim() { it <= ' '}
                    val passText = pass.text.toString().trim() { it <= ' '}
                    println(emailText)
                    println(passText)

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailText, passText)
                        .addOnCompleteListener { task ->

                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(
                                    this@RegisterActivity,
                                    "You are registered successfully ",
                                    Toast.LENGTH_SHORT
                                ).show()

                                /**
                                 *Switch from register to main//
                                 */
                                val intent =
                                    Intent(this@RegisterActivity, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("email_id", emailText)
                                startActivity(intent)
                                finish()
                            } else{
                                Toast.makeText(
                                    this@RegisterActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }


                }
            }


        }
    }
}