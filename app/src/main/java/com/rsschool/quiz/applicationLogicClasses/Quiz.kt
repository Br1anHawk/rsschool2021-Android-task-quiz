package com.rsschool.quiz.applicationLogicClasses

import android.os.Parcel
import android.os.Parcelable

class Quiz() : Parcelable {
    private val questionsFromDB = mutableListOf<Question>()
    private val countOfQuestionsInQuiz = 10
    private val listOfQuestionsForUser = mutableListOf<Question>()

    constructor(parcel: Parcel) : this() {
    }

    init {
        addQuestionInDB("What year was Heinz established?", //1
            listOf(
                "1865",
                "1861",
                "1859",
                "1869",
                "1867",
                "1875",
                "1900"),
            3)
        addQuestionInDB("What is the capital of Iceland?",  //2
            listOf(
                "Banjul",
                "Reykjavík",
                "Amman",
                "Nairobi",
                "Dublin",
                "Chisinau"),
            1)
        addQuestionInDB("Which planet is closest to the sun?",  //3
            listOf(
                "Venus",
                "Uranus",
                "Mars",
                "Earth",
                "Mercury"),
            4)
        addQuestionInDB("How many minutes in a game of rugby league?",  //4
            listOf(
                "50 minutes",
                "60 minutes",
                "70 minutes",
                "80 minutes",
                "90 minutes",
                "100 minutes",
                "120 minutes"),
            3)
        addQuestionInDB("How many elements are there in the periodic table?",   //5
            listOf(
                "114 elements",
                "115 elements",
                "116 elements",
                "117 elements",
                "118 elements",
                "119 elements",
                "120 elements",
                "121 elements",
                "122 elements"),
            4)
        addQuestionInDB("Where was the mojito cocktail created?",   //6
            listOf(
                "Maldives",
                "Colombia",
                "Cyprus",
                "Cuba",
                "USA",
                "Chile",
                "Monaco"),
            3)
        addQuestionInDB("How many sides does a heptadecagon have?", //7
            listOf(
                "Fourteen",
                "Fifteen",
                "Sixteen",
                "Seventeen",
                "Eighteen"),
            3)
        addQuestionInDB("How fast can an ostrich run?", //8
            listOf(
                "30 km/h",
                "35 km/h",
                "45 km/h",
                "50 km/h",
                "60 km/h",
                "65 km/h",
                "80 km/h",
                "90 km/h"),
            5)
        addQuestionInDB("Which Caribbean island has the greatest area?",    //9
            listOf(
                "Puerto Rico",
                "Jamaica",
                "Hispanola",
                "Cuba",
                "Antigua and Barbuda",
                "Dominican Republic",
                "Belize",
                "Costa Rica",
                "Dominica"),
            3)
        addQuestionInDB("What year was the very first model of the iPhone released?",   //10
            listOf(
                "2005",
                "2006",
                "2007",
                "2008",
                "2009",
                "2010",
                "2011"),
            2)
        addQuestionInDB("How many molecules of oxygen does ozone have?",    //11
            listOf(
                "2",
                "3",
                "4",
                "5",
                "6"),
            1)
        addQuestionInDB("Which company owns Bugatti, Lamborghini. Audi, Porsche, and Ducati?",  //12
            listOf(
                "Bugatti",
                "Audi",
                "Porsche",
                "Volkswagen",
                "BMW"),
            3)
        addQuestionInDB("Which one of these characters is not friends with Harry Potter?",  //13
            listOf(
                "Ron Weasley",
                "Neville Longbottom",
                "Draco Malfoy",
                "Hermione Granger",
                "Rubeus Hagrid"),
            2)
        addQuestionInDB("What is the color of Donald Duck’s bowtie?",  //14
            listOf(
                "Red",
                "Yellow",
                "Blue",
                "White",
                "Black"),
            0)
        addQuestionInDB("In Pirates of the Caribbean, what was Captain Jack Sparrow’s ship’s name?",  //15
            listOf(
                "The Marauder",
                "The Black Pearl",
                "The Black Python",
                "The Slytherin",
                "The Battleship"),
            1)
        addQuestionInDB("What is the rarest blood type?",  //16
            listOf(
                "0",
                "A",
                "B",
                "AB-Negative",
                "AB-Positive"),
            3)
        addQuestionInDB("How many bones are there in the human body?",  //17
            listOf(
                "206",
                "205",
                "201",
                "209",
                "211"),
            0)
        addQuestionInDB("How many hearts does an octopus have?",  //18
            listOf(
                "1",
                "2",
                "3",
                "4",
                "5"),
            2)
        addQuestionInDB("\"Fe\" is the chemical symbol for…",  //19
            listOf(
                "Zinc",
                "Hydrogen",
                "Fluorine",
                "Iron",
                "Silicium"),
            3)
        shuffleAndSelectQuestionsForUser()
    }

    private fun addQuestionInDB(questionText: String, answerOptionsList: List<String>, numberOfRightAnswer: Int) {
        val answerOptions = mutableListOf<AnswerOption>()
        for ((index, answerOptionText) in answerOptionsList.withIndex()) {
            answerOptions.add(index, AnswerOption(index, answerOptionText))
        }
        val question = Question(questionText, answerOptions, numberOfRightAnswer, questionsFromDB.size)
        questionsFromDB.add(question)
    }

    fun getCountQuestions(): Int {
        return listOfQuestionsForUser.size
    }

    fun getQuestions(): MutableList<Question> {
        return listOfQuestionsForUser
    }

    fun getCountOfRightAnswers(): Int {
        var countOfRightAnswers = 0
        listOfQuestionsForUser.forEach {
            if (it.numberOfCheckedAnswerOption == getQuestionFromDbById(it.id).numberOfCheckedAnswerOption) {
                countOfRightAnswers++
            }
        }
        return countOfRightAnswers
    }

    fun shuffleAndSelectQuestionsForUser() {
        questionsFromDB.shuffle()
        questionsFromDB.forEach { it.answerOptions.shuffle() }
        listOfQuestionsForUser.clear()
        for ((index, question) in questionsFromDB.withIndex()) {
            if (index == countOfQuestionsInQuiz) {
                break
            }
            val tempQuestion = question.copy()
            tempQuestion.numberOfCheckedAnswerOption = null
            listOfQuestionsForUser.add(tempQuestion)
        }
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)
    fun getQuizResultReportFormattedInText(): String {
        val formattedReport = StringBuilder()
        val countOfRightQuestions = getCountOfRightAnswers()
        val countOfQuestions = getCountQuestions()
        val percentOfRightQuestions = countOfRightQuestions.toDouble() / countOfQuestions * 100
        formattedReport.appendLine("Your quiz result is $countOfRightQuestions out of $countOfQuestions (${percentOfRightQuestions.format(1)}%).")
        formattedReport.appendLine()
        for ((index, question) in listOfQuestionsForUser.withIndex()) {
            formattedReport.appendLine("${index + 1}) ${question.questionText}")
            var userAnswerText = ""
            for (option in question.answerOptions) {
                if (option.id == question.numberOfCheckedAnswerOption) {
                    userAnswerText = option.answerOptionText
                    break
                }
            }
            formattedReport.appendLine("Your answer: $userAnswerText.")
            val questionFromDb = getQuestionFromDbById(question.id)
            if (question.numberOfCheckedAnswerOption == questionFromDb.numberOfCheckedAnswerOption) {
                formattedReport.appendLine("Right!")
            } else {
                var rightAnswerText = ""
                for (option in questionFromDb.answerOptions) {
                    if (option.id == questionFromDb.numberOfCheckedAnswerOption as Int) {
                        rightAnswerText = option.answerOptionText
                        break
                    }
                }
                formattedReport.appendLine("Wrong! - right answer: $rightAnswerText.")
            }
            formattedReport.appendLine()
        }
        return formattedReport.toString()
    }

    private fun getQuestionFromDbById(id: Int): Question {
        for (question in questionsFromDB) {
            if (question.id == id) {
                return question
            }
        }
        return questionsFromDB[0]
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Quiz> {
        override fun createFromParcel(parcel: Parcel): Quiz {
            return Quiz(parcel)
        }

        override fun newArray(size: Int): Array<Quiz?> {
            return arrayOfNulls(size)
        }
    }
}