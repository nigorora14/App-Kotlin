//para almacenar la session del usuario
package com.ngonzano.delivery.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import java.lang.Exception

class SharedPref (activity: Activity){//siempre recibe un parametro
    private var prefs: SharedPreferences? = null // se crea la variable pref que inicializa con null

    init {
        // con esto iniciaria la variable sharedpreference
        prefs = activity.getSharedPreferences("como.ngonzano.delivery", Context.MODE_PRIVATE)
    }
    //metodo que permita guardar la informacion en session en el dispositivo
    fun save(key: String, objeto: Any){
        try {
            val gson= Gson()// creas una constante
            /*crea un objeto de tipo json | se va convertir el objeto a un objeto de tipo json es para los objetos
            que cuentan con atributos ejm: llave y valor
            */
            val json = gson.toJson(objeto)
            with(prefs?.edit()){
                this?.putString(key, json)
                this?.commit()
            }
        }catch (e: Exception){
            Log.d("Error","Err: ${e.message}")
        }
    }
    //metodo para obtener la data | el : String signiofica que va retornar un string
    fun getData(key: String): String? {
        val data = prefs?.getString(key,"") // el "" es el objeto cuyo valor por defecto es vacio
        return data
    }
    //para cerrar session
    fun remove(key: String){
        prefs?.edit()?.remove(key)?.apply()
    }
}