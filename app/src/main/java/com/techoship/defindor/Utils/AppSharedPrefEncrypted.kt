package com.techoship.defindor.Utils

import android.content.Context
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys


class AppSharedPrefEncrypted(private val context: Context) {
    @RequiresApi(Build.VERSION_CODES.M)
    var masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)


    fun save(key: String?, value: String?) {
        // val sh: SharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        var sh = EncryptedSharedPreferences.create(
            "app_shared_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        val editor = sh.edit()
        editor.putString(key, value)
        editor.apply()
    }

    operator fun get(key: String?): String? {
        // val sh: SharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        var sh = EncryptedSharedPreferences.create(
            "app_shared_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        return sh.getString(key, "")
    }

    fun saveImg(image: ByteArray?) {
        val img_str = Base64.encodeToString(image, 0)
        //val sh: SharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        var sh = EncryptedSharedPreferences.create(
            "app_shared_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        val editor = sh.edit()
        editor.putString("profile", img_str)
        editor.apply()
    }

    fun clear() {
        var settings = EncryptedSharedPreferences.create(
            "app_shared_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        //val settings: SharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        settings.edit().clear().apply()
    }
}