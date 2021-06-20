package com.rsschool.quiz.applicationLogicClasses

class Question(
    val questionText: String,
    val answerOptions: MutableList<AnswerOption>,
    var numberOfCheckedAnswerOption: Int? = null,
    var id: Int = 0
) {
    fun copy(): Question {
        return Question(questionText, answerOptions, numberOfCheckedAnswerOption, id)
    }
}