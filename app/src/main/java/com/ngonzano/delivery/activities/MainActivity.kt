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
import com.google.gson.Gson
import com.ngonzano.delivery.R
import com.ngonzano.delivery.activities.client.ClientHomeActivity
import com.ngonzano.delivery.models.ResponseHttp
import com.ngonzano.delivery.models.User
import com.ngonzano.delivery.providers.UsersProvider
import com.ngonzano.delivery.utils.SharedPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var imageViewGoToRegister: ImageView? = null
    var editTextEmail: EditText? = null
    var editTextPassword: EditText? = null
    var buttonLogin: Button? = null

    var usersProvider = UsersProvider() // se comunica con el backend

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageViewGoToRegister = findViewById(R.id.imageview_go_to_register)
        editTextEmail = findViewById(R.id.edittext_email)
        editTextPassword = findViewById(R.id.edittext_password)
        buttonLogin = findViewById(R.id.btn_login)

        buttonLogin?.setOnClickListener{login()}
        imageViewGoToRegister?.setOnClickListener{gotoRegister()}

        getUserFromSession() // para que no vuelva ir al login directo
    }

    private fun login(){
        val email = editTextEmail?.text.toString()
        val password = editTextPassword?.text.toString()

        if(isValidForm(email, password)) {
            usersProvider.login(email, password)?.enqueue(object: Callback<ResponseHttp>{
                override fun onResponse(
                    call: Call<ResponseHttp>,
                    response: Response<ResponseHttp>
                ) {
                    Log.d("MainActivity", "Response: ${response.body()}")
                    if (response.body()?.Success == true){
                        Toast.makeText(this@MainActivity, response.body()?.Message, Toast.LENGTH_LONG).show()

                        saveUserInSession(response.body()?.Data.toString()) // almacenando el usuario
                        goToClientHome()//llama al metodo | nos envia a la pantalla
                    }
                    else {
                        Toast.makeText(this@MainActivity, "Los Datos no son correctos.", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<ResponseHttp>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Hubo un error: ${t.message}", Toast.LENGTH_LONG).show()
                    Log.d("MainActivity", "Response: ${t.message}")
                }
            })
        }
        else {
            Toast.makeText(this, "El formulario no es valido", Toast.LENGTH_LONG).show()
        }

        Log.d("MainActivity", "El email es: $email")
        //Log.d("MainActivity", "El password es: $password")
    }

    private fun goToClientHome(){
        val i = Intent(this, ClientHomeActivity::class.java) //guarda el usuario en session
        startActivity(i)//inicializar
    }

    //Se va almacenar el usuario en session
    private fun saveUserInSession(data: String){
        val sharedPref = SharedPref(this) //pide un activity y es this por que esta dentro de un activity
        val gson = Gson()
        val user = gson.fromJson(data, User::class.java) // almacena toda la informacion del usuario
        sharedPref.save("user", user) //"user" es el nombre del key puede ser cualquier nombre su valor sera el objeto user
    }

    fun String.isEmailValid(): Boolean{
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun getUserFromSession(){ // para que no vuelva ir al login
        val sharedPref = SharedPref(this)
        val gson = Gson()
        //"user" es la misma que se uso en el metodo saveUserInSession del MainActivity
        if (!sharedPref.getData("user").isNullOrBlank()){//si el usuario existe en session
            //val user  = gson.fromJson(sharedPref.getData("user"),User::class.java)// obtiene el usuario
            goToClientHome()
        }
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