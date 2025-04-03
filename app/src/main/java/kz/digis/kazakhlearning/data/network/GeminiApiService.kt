package kz.sdk.tussup.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiApiService {
    @Headers("Content-Type: application/json")
    @POST("v1beta/models/gemini-1.5-flash:generateContent")
    fun generateKeyPhrase(
        @Query("key") apiKey: String,
        @Body body: GeminiRequestBody
    ): Call<GeminiResponse>
}