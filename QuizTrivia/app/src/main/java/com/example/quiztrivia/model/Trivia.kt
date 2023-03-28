package com.example.quiztrivia.model

import com.google.gson.annotations.SerializedName

data class TriviaResponse(
    @SerializedName("results") val results: List<Trivia>
)
data class Trivia (
    @SerializedName("category") val category: String,
    @SerializedName("difficulty") val difficulty: String,
    @SerializedName("question") val question: String,
    @SerializedName("correct_answer") val correctAnswer: String,
    @SerializedName("incorrect_answers") val incorrectAnswers: List<String>
)