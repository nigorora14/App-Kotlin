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
import com.ngonzano.delivery.models.ResponseHttp
import com.ngonzano.delivery.models.User
import com.ngonzano.delivery.providers.UsersProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    val TAG = "RegisterActivity"

    var imageViewGoToLogin: ImageView? = null
    var editTextName: EditText? = null
    var editTextLastname: EditText? = null
    var editTextEmail: EditText? = null
    var editTextPhone: EditText? = null
    var editTextPassword: EditText? = null
    var editTextConfirmPassword: EditText? = null
    var buttonRegister: Button? = null

    var usersProvider = UsersProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        imageViewGoToLogin = findViewById(R.id.imageview_go_to_login)
        editTextName = findViewById(R.id.edittext_name)
        editTextLastname = findViewById(R.id.edittext_lastname)
        editTextEmail = findViewById(R.id.edittext_email)
        editTextPhone = findViewById(R.id.edittext_phone)
        editTextPassword = findViewById(R.id.edittext_password)
        editTextConfirmPassword = findViewById(R.id.edittext_confirm_password)
        buttonRegister = findViewById(R.id.btn_register)

        imageViewGoToLogin?.setOnClickListener{
            goToLogin()
        }
        buttonRegister?.setOnClickListener{
            register()
        }
    }
    private fun register(){
        val name = editTextName?.text.toString()
        val lastname = editTextLastname?.text.toString()
        val email = editTextEmail?.text.toString()
        val phone = editTextPhone?.text.toString()
        val password = editTextPassword?.text.toString()
        val confirmPassword = editTextConfirmPassword?.text.toString()

        if (isValidForm(name,lastname,email,phone,password,confirmPassword)){
            //Toast.makeText(this, "El formulario es valido", Toast.LENGTH_LONG).show()
            val user = User(
                Name = name,
                Lastname = lastname,
                Email = email,
                Phone = phone,
                Password = password
            )
            usersProvider.register(user)?.enqueue(object: Callback<ResponseHttp>{
                override fun onResponse(
                    call: Call<ResponseHttp>,
                    response: Response<ResponseHttp>
                ) {
                    Toast.makeText(this@RegisterActivity, response.body()?.Message,Toast.LENGTH_LONG).show()
                    Log.d(TAG, "Response: ${response}")
                    Log.d(TAG, "Body: ${response.body()}") // trae la respuesta en json success, message, data

                }

                override fun onFailure(call: Call<ResponseHttp>, t: Throwable) {
                   Log.d(TAG,"Se produjo un error ${t.message}")
                   Toast.makeText(this@RegisterActivity, "Se produjo un error ${t.message}", Toast.LENGTH_LONG).show()
                }

            })
        }
        else {
            Toast.makeText(this, "El formulario no es valido", Toast.LENGTH_LONG).show()
        }

         Log.d(TAG, "El nombre es: $name")
         Log.d(TAG, "El Apellido es: $lastname")
         Log.d(TAG, "El Email es: $email")
         Log.d(TAG, "El Telefono es: $phone")
         Log.d(TAG, "El Contraseña es: $password")
         Log.d(TAG, "El confirm password es: $confirmPassword")
    }
    fun String.isEmailValid(): Boolean{
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
    private fun isValidForm(
        name: String,
        lastname: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String ): Boolean {
        if (name.isBlank()){
            Toast.makeText(this, "Ingrese un nombre",Toast.LENGTH_LONG).show()
            return false
        }
        if (lastname.isBlank()){
            Toast.makeText(this, "Ingrese un Apellido",Toast.LENGTH_LONG).show()
            return false
        }
        if (email.isBlank()){
            Toast.makeText(this, "Ingrese un email",Toast.LENGTH_LONG).show()
            return false
        }
        if (phone.isBlank()){
            Toast.makeText(this, "Ingrese un Telefono",Toast.LENGTH_LONG).show()
            return false
        }
        if (password.isBlank()){
            Toast.makeText(this, "Ingrese un Password",Toast.LENGTH_LONG).show()
            return false
        }
        if (confirmPassword.isBlank()){
            Toast.makeText(this, "Ingrese la confirmacion del Password",Toast.LENGTH_LONG).show()
            return false
        }
        if (password != confirmPassword){
            Toast.makeText(this, "las contraseñas no coinciden",Toast.LENGTH_LONG).show()
            return false
        }
        if (!email.isEmailValid()){
            Toast.makeText(this, "formato del correo no valido",Toast.LENGTH_LONG).show()
            return false
        }
        if (password.length <= 5 ){
            Toast.makeText(this, "El password debe contener 6 digitos a más",Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun goToLogin(){
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}