package com.ngonzano.delivery.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.ngonzano.delivery.R

class MainActivity : AppCompatActivity() {

    var imageViewGoToRegister: ImageView? = null
    var editTextEmail: EditText? = null
    var editTextPassword: EditText? = null
    var buttonLogin: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageViewGoToRegister = findViewById(R.id.imageview_go_to_register)
        editTextEmail = findViewById(R.id.edittext_email)
        editTextPassword = findViewById(R.id.edittext_password)
        buttonLogin = findViewById(R.id.btn_login)

        buttonLogin?.setOnClickListener{login()}
        imageViewGoToRegister?.setOnClickListener{gotoRegister()}
    }
    private fun login(){
        val email = editTextEmail?.text.toString()
        val password = editTextPassword?.text.toString()

        if(isValidForm(email, password)) {
            Toast.makeText(this, "El formulario es valido", Toast.LENGTH_LONG).show()
        }
        else {
            Toast.makeText(this, "El formulario no es valido", Toast.LENGTH_LONG).show()
        }

        Log.d("MainActivity", "El email es: $email")
        Log.d("MainActivity", "El password es: $password")
    }

    fun String.isEmailValid(): Boolean{
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
    private fun isValidForm(email: String, password: String): Boolean {
        if (email.isBlank()){
            return false
        }
        if (password.isBlank()){
            return false
        }
        if (!email.isEmailValid()){
            return false
        }
        return true
    }

    private fun gotoRegister(){
        val i = Intent(this, RegisterActivity::class.java)
        startActivity(i)
    }
}