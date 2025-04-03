package kz.sdk.tussup.network

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class GeminiApiHelper(private val apiKey: String) {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://generativelanguage.googleapis.com/")  // Base URL for the API
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient()) // Add OkHttp client if needed
            .build()
    }

    private val geminiService: GeminiApiService by lazy {
        retrofit.create(GeminiApiService::class.java)
    }

    fun askKazakhExpert(question: String, callback: (String?) -> Unit) {
        val requestBody = GeminiRequestBody(
            contents = listOf(
                Content(
                    parts = listOf(
                        Part("Ты казахский лингвист и эксперт. Отвечай ясно и просто на вопрос: $question")
                    )
                )
            )
        )

        val call = geminiService.generateKeyPhrase(apiKey, requestBody)
        call.enqueue(object : Callback<GeminiResponse> {
            override fun onResponse(call: Call<GeminiResponse>, response: Response<GeminiResponse>) {
                if (response.isSuccessful) {
                    val reply = response.body()?.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                    callback(reply)
                } else {
                    callback("Ответ не получен.")
                }
            }

            override fun onFailure(call: Call<GeminiResponse>, t: Throwable) {
                callback("Не смогли соединится с сервером")
            }
        })
    }

}