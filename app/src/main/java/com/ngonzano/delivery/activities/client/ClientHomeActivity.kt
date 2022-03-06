package com.ngonzano.delivery.activities.client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.gson.Gson
import com.ngonzano.delivery.R
import com.ngonzano.delivery.activities.MainActivity
import com.ngonzano.delivery.models.User
import com.ngonzano.delivery.utils.SharedPref

class ClientHomeActivity : AppCompatActivity() {
    private val TAG = "ClientHomeActivity"
    var buttonLogout: Button? = null //instanciar el boton
    var sharedPref: SharedPref? = null //instanciar el boton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_home)

        sharedPref = SharedPref(this) //para que sea global

        buttonLogout = findViewById(R.id.btn_logout)
        buttonLogout?.setOnClickListener{logout()} // cuando se da click al boton cerrar session

        getUserFromSession() //llamando al metodo
    }
    private fun logout(){
        sharedPref?.remove("user")//elimina la session
        //para que nos envie al login
        val i = Intent(this, MainActivity::class.java)
        startActivity(i) //para lanzar el intent
        //fin
    }

    //metodo para obtener usuarios de session
    private fun getUserFromSession(){
        val gson = Gson() //constante

        //"user" es la misma que se uso en el metodo saveUserInSession del MainActivity
        if (!sharedPref?.getData("user").isNullOrBlank()){//si el usuario existe en session
            val user  = gson.fromJson(sharedPref?.getData("user"),User::class.java)// obtiene el usuario
            Log.d(TAG,"Usuario: $user")
        }
    }
}