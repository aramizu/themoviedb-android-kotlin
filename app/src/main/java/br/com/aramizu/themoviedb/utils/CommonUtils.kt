package br.com.aramizu.themoviedb.utils

import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import org.json.JSONObject
import org.json.JSONException
import org.json.JSONArray
import android.os.Build
import android.annotation.TargetApi
import android.content.Intent
import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.provider.Settings
import br.com.aramizu.themoviedb.R
import br.com.aramizu.themoviedb.config.ApplicationConstants
import java.io.File
import java.io.IOException
import java.lang.reflect.Type
import java.nio.charset.Charset
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object CommonUtils {

    fun showLoadingDialog(context: Context): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.show()
        if (progressDialog.window != null) {
            progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(true)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog
    }

    @SuppressLint("all")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)
    }

    @Throws(IOException::class)
    fun loadJSONFromAsset(context: Context, jsonFileName: String): String {

        val manager = context.getAssets()
        val input = manager.open(jsonFileName)

        val size = input.available()
        val buffer = ByteArray(size)
        input.read(buffer)
        input.close()

        return String(buffer, Charset.forName("UTF-8"))
    }

    fun instantiateEmailSendBillCodeIntent(code: String): Intent {
        val body = StringBuilder("Caro Cliente,\n\n")
        body.append("Conforme solicitado no Internet Banking PAN, segue o código para pagamento do seu boleto de cartão de crédito.\n\n")
        body.append(code)
        body.append("\n\nObrigado por utilizar nossos serviços.")

        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Banco PAN - Código Para Pagamento")
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body.toString())

        return emailIntent
    }

    fun instantiateEmailWithAttachmentUriIntent(tempUri: String): Intent {
        val body = StringBuilder("Caro Cliente,\n\n")
        body.append("Conforme solicitado no Internet Banking PAN, segue anexo o boleto para pagamento do seu financiamento.")
        body.append("\n\nObrigado por utilizar nossos serviços.")

        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Banco PAN - Solicitação de boleto")
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body.toString())
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(File(tempUri)))

        return emailIntent
    }

    @TargetApi(Build.VERSION_CODES.N)
    fun getCurrentLocale(context: Context): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.getResources().getConfiguration().getLocales().get(0)
        } else {

            context.getResources().getConfiguration().locale
        }
    }

    fun parseStringToJsonArray(s: String): JSONArray? {
        try {
            return JSONArray(s)
        } catch (e: JSONException) {
            e.printStackTrace()
            return null
        }

    }

    fun parseStringToJsonObject(s: String): JSONObject? {
        try {
            return JSONObject(s)
        } catch (e: JSONException) {
            e.printStackTrace()
            return null
        }

    }

    @Throws(JSONException::class)
    fun parseJsonToArray(jsonArray: JSONArray, definedClass: Class<*>): List<*> {
        val list = ArrayList<Object>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val c = parseJsonToObject(jsonObject, definedClass) as Object
            list.add(c)
        }

        return list
    }

    fun parseJsonToObject(jsonObject: JSONObject, definedClass: Class<*>): Any {

        val serializer = object : JsonSerializer<Date> {
            override fun serialize(src: Date?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement? {
                return if (src == null) null else JsonPrimitive(src!!.getTime())
            }
        }

        val deserializer = object : JsonDeserializer<Date> {
            @Throws(JsonParseException::class)
            override fun deserialize(json: JsonElement, typeOfT: Type,
                            context: JsonDeserializationContext): Date? {
                var dateFormat: SimpleDateFormat
                val dateString = json.toString().replace("\"".toRegex(), "")
                var date: Date
                try {
                    dateFormat = SimpleDateFormat(ApplicationConstants.TIME_ZONE_DATE_FORMAT_)
                    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
                    date = dateFormat.parse(dateString)
                    dateFormat.setTimeZone(TimeZone.getDefault())
                    return dateFormat.parse(dateFormat.format(date))
                } catch (ex1: ParseException) {
                    try {
                        dateFormat = SimpleDateFormat(ApplicationConstants.TIME_ZONE_DATE_FORMAT__)
                        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
                        date = dateFormat.parse(dateString)
                        dateFormat.setTimeZone(TimeZone.getDefault())
                        return dateFormat.parse(dateFormat.format(date))
                    } catch (ex2: ParseException) {
                        try {
                            dateFormat = SimpleDateFormat(ApplicationConstants.TIME_ZONE_DATE_FORMAT___)
                            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
                            date = dateFormat.parse(dateString)
                            dateFormat.setTimeZone(TimeZone.getDefault())
                            return dateFormat.parse(dateFormat.format(date))
                        } catch (ex3: ParseException) {
                            try {
                                dateFormat = SimpleDateFormat(ApplicationConstants.TIME_ZONE_DATE_FORMAT____)
                                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
                                date = dateFormat.parse(dateString)
                                dateFormat.setTimeZone(TimeZone.getDefault())
                                return dateFormat.parse(dateFormat.format(date))
                            } catch (ex4: ParseException) {
                                return null
                            }

                        }

                    }

                }

            }
        }


        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(Date::class.java, serializer)
                .registerTypeAdapter(Date::class.java, deserializer)
        val gson = gsonBuilder.create()
        return gson.fromJson<Any>(jsonObject.toString(), definedClass)
    }
}
