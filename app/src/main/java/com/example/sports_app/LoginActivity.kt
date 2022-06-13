package com.example.sports_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * Logging in with email and password over google firebase
 * Authors: Gerwald Gindrawady, Dominik Bregovic, Lukas Linzer
 * Last Changed: 13.06.2022
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
//        toolbar.inflateMenu(R.menu.main_menu)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true);
//        supportActionBar?.setHomeButtonEnabled(true);


        val loginButton = findViewById<Button>(R.id.btn_login_login)
        val pass = findViewById<TextView>(R.id.et_login_password)
        val email = findViewById<TextView>(R.id.et_login_email)

        val toRegister = findViewById<TextView>(R.id.tv_login_register)
        toRegister.setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        val toForgotPass = findViewById<TextView>(R.id.tv_login_forgotpassword)
        toForgotPass.setOnClickListener{
            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
        }



        loginButton.setOnClickListener{
            when{
                TextUtils.isEmpty(email.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter mail",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(pass.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter password",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    val emailText = email.text.toString().trim() { it <= ' '}
                    val passText = pass.text.toString().trim() { it <= ' '}
                    println(emailText)
                    println(passText)

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(emailText, passText)
                        .addOnCompleteListener { task ->

                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(
                                    this@LoginActivity,
                                    "You are registered successfully ",
                                    Toast.LENGTH_SHORT
                                ).show()

                                /**
                                 *Switch from register to main//
                                 */
                                val intent =
                                    Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                                intent.putExtra("email_id", emailText)
                                startActivity(intent)
                                finish()
                            } else{
                                Toast.makeText(
                                    this@LoginActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                }
            }
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.tb_menu_logout){
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}