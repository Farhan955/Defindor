package com.idealogics.eatlaa.Utils

import android.content.Context
import android.util.Base64
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.techoship.defindor.Models.Profile
import com.techoship.defindor.Models.SignupResponse

class SharedPref(var context: Context) {
    fun save(key: String?, value: String?) {
        val editor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit()
        editor.putString(key, value)
        editor.commit()
    }

    operator fun get(key: String?): String? {
        val sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, "")
    }

    fun saveImg(key: String?, image: ByteArray?) {
        val img_str = Base64.encodeToString(image, Base64.DEFAULT)
        val sh = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = sh.edit()
        editor.putString(key, img_str)
        editor.commit()
    }

    fun getImg(key: String?): ByteArray {
        val sharedPreferences =
            context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val s = sharedPreferences.getString(key, "")
        // String text = new String(data, StandardCharsets.UTF_8);
        return Base64.decode(s, Base64.DEFAULT)
    }

    fun clear() {
        val settings = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        settings.edit().clear().commit()
    }

    fun saveBoolen(key: String?, value: Boolean) {
        val prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String?, defValue: Boolean): Boolean {
        val prefs =
            context.getSharedPreferences("data", Context.MODE_PRIVATE)
        return prefs.getBoolean(key, defValue)
    }

    fun saveInt(key: String?, value: Int) {
        val prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(key: String?): Int {
        val prefs =
            context.getSharedPreferences("data", Context.MODE_PRIVATE)
        return prefs.getInt(key, -1)
    }

    fun getList(key: String?): ArrayList<String> {
        val prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString(key, null)
        val type = object : TypeToken<ArrayList<String?>?>() {}.type
        return gson.fromJson(json, type)
    }

    fun isLoggedIn(): Boolean {
        return getBoolean("isLoggedIn", false)
    }

    fun isLoggedIn(isLoggedIn: Boolean) {
        saveBoolen("isLoggedIn", isLoggedIn)
    }


    fun saveProfile(profile: Profile) {
        save("profile", Gson().toJson(profile))
    }

    fun saveSignUpResponse(response: SignupResponse) {
        save("signUpResponse", Gson().toJson(response))
    }

    fun getProfile(): Profile {
        val data = get("profile")
        return Gson().fromJson(data, Profile::class.java)
    }
    fun getSignUpResponse(): SignupResponse {
        val data = get("signUpResponse")
        return Gson().fromJson(data, SignupResponse::class.java)
    }


}