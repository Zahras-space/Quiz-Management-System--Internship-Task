import kotlin.system.exitProcess
import kotlinx.coroutines.*

data class Question(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)
val questions = mutableListOf<Question>()

fun main()= runBlocking {
    val mainQuiz = MainQuiz()
    launch {
        val loadedQuestions = mainQuiz.loadQuestions()
        questions.addAll(loadedQuestions)
    }.join() // wait until coroutine completes

    val app= App()
    app.MainDisplay()
}

class App {
    fun MainDisplay() {
        val teacher = Teacher()
        val student = Student()
        println("=====================================")
        println("  PentaByteX Quiz Management System  ")
        println("=====================================")
        println("1. Teacher Login")
        println("2. Student Login")
        println("3. Exit")
        print("Enter your choice: ")
        val choice = readLine()

        when (choice) {
            "1" -> teacher.login()
            "2" -> student.login()
        }
    }

}

class Authentication {
    fun verifyCredentials(email: String, password: String, credentialsArray: Array<Pair<String, String>>): Boolean {
        for (credential in credentialsArray) {                    
            if (credential.first == email && credential.second == password) {   
                return true
            }
        }
        return false 
    }
    
    fun teacherVerify(email: String, password: String): Boolean {
        val teacherCredentials = arrayOf(
            Pair("teacher@example.com", "123"),
            Pair("ali@comsats.edu", "ali123")
        )
        val check= verifyCredentials(email, password, teacherCredentials)
        return check
    }
    fun studentVerify(email: String, password: String): Boolean {
        val studentCredentials = arrayOf(
            Pair("student@example.com", "password"),
            Pair("john.doe@learn.org", "learnpass"),
            Pair("jane.student@academy.net", "kotlinfun")
        )
        val check= verifyCredentials(email, password, studentCredentials)
        return check
    }
    
}

class MainQuiz{
     fun displayQuiz() {
        var score = 0

        println("--------Welcome to the Quiz!--------")
        for ((index, q) in questions.withIndex()) {
            println("\nQuestion ${index + 1}: ${q.question}")
            q.options.forEachIndexed { i, option -> println("${'A' + i}. $option") }

            print("Enter your answer (A/B/C/D): ")
            val userAnswer = readLine()?.isNullOrEmpty()?:"Null"        //Elvis Operator
            if (userAnswer == q.correctAnswer.uppercase()) {
                println("Correct!")
                score++
            }
            else {
                println("Wrong! Correct answer: ${q.correctAnswer}")
            }
        }

        println("\nYou have Cmopleted the Quiz!")
        println("Your final score: $score / ${questions.size}")
    }

    fun loadQuestions(): List<Question> {
        val questionList = listOf(
            mapOf(
                "question" to "Which keyword is used to declare a variable in Kotlin?",
                "options" to listOf("var", "val", "let", "const"),
                "answer" to "A"
            ),
            mapOf(
                "question" to "Which function is the entry point of a Kotlin program?",
                "options" to listOf("start()", "run()", "main()", "init()"),
                "answer" to "C"
            ),
            mapOf(
                "question" to "Which of these is NOT a Kotlin data type?",
                "options" to listOf("Int", "String", "Boolean", "Character"),
                "answer" to "D"
            ),
            mapOf(
                "question" to "What does JVM stand for?",
                "options" to listOf("Java Virtual Machine", "Java Visual Model", "Joint Virtual Module", "Java Variable Memory"),
                "answer" to "D"
        )

        )

        // Converts teh map data into Question objects
       return questionList.map { q ->
           Question(
               q["question"] as String,
               q["options"] as List<String>,
               q["answer"] as String
           )
       }
    }

    fun addQuestions(){
        println("Enter your Question")
        val new_question= readLine()!!
        println("Enter the 4 Options:")
        val options = mutableListOf<String>()
        for (i in 1..4) {
            print("Option ${'A' + (i - 1)}: ")
            options.add(readLine()!!.trim())
        }

        var correctAnswer: String?
        while (true) {
            println("Enter the Correct Answer (A/B/C/D):")
            correctAnswer = readLine()?.trim()?.uppercase()
            if (correctAnswer in listOf("A", "B", "C", "D")) break
            println("Invalid choice. Please enter A, B, C, or D.")
        }
        questions.add(
            Question(
                question = new_question,
                options = options,
                correctAnswer = correctAnswer!!
            )

        )
        println("Question Added Successfully")
        return
        }

    fun editQuestions(){
        println("Enter the question no. to edit")
        var edit_Index: Int
        while (true) {
            edit_Index = readLine()!!.toIntOrNull() ?: -1
            if (edit_Index in 1..questions.size) break
            println("Invalid number. Please enter between 1 and ${questions.size}:")
        }
        println("Enter your Edited Question:")
        val newQuestionText = readLine()!!.trim()

        println("Enter the new 4 Options:")
        val options = mutableListOf<String>()
        for (i in 1..4) {
            print("Option ${'A' + (i - 1)}: ")
            options.add(readLine()!!.trim())
        }

        var correctAnswer: String
        while (true) {
            println("Enter the Correct Answer (A/B/C/D):")
            correctAnswer = readLine()?.trim()?.uppercase() ?: ""
            if (correctAnswer in listOf("A", "B", "C", "D")) break
            println("Invalid choice. Please enter A, B, C, or D.")
        }

        // Replacing the old question with the new one
        questions[edit_Index - 1] = Question(
            question = newQuestionText,
            options = options,
            correctAnswer = correctAnswer
        )
    return
    }

    fun deleteQuestions(){
    println("Enter the question no. to delete")
        val remove_question= readLine()!!.toInt()
        while(remove_question<=0 && remove_question > questions.size+1){
            println("Enter a Valid Question No.")
            val remove_question= readLine()!!.toInt()
        }
        questions.removeAt(remove_question-1)
        return
    }

    fun displayQuestions(){
        println("\n--- All Questions in Quiz ---")
        questions.forEachIndexed { index, q ->
            println("\nQuestion ${index + 1}: ${q.question}")
            q.options.forEachIndexed { i, option ->
                println("${'A' + i}. $option")
            }
            println("Correct Answer: ${q.correctAnswer}")
        }
        println("\nTotal Questions: ${questions.size}")
    return
    }
}

abstract class User(){
    abstract var password : String
    abstract var email : String
    abstract fun login()
    abstract fun mainmenu()
}

                                        //TEACHER PORTAL//
class Teacher(): User() {
    val authentication = Authentication()
    val questions = MainQuiz()
    override lateinit var password: String
    override lateinit var email: String

    override
    fun login() {
        println("\n\n==================")
        println(" TEACHER LOGIN    ")
        println("==================")
        println("Enter your email:")
        email = readLine()!!
        println("Enter your password:")
        password = readLine()!!
        val check = authentication.teacherVerify(email, password)
        when (check) {
            true -> mainmenu()
            false -> {
                println("Wrong Credentials! Enter again ")
                login()
            }
        }

    }

    override fun mainmenu()=runBlocking{
        val options = arrayOf(
            "Add Question", "Edit Question", "Delete Question", "Display Questions","Exit"
        )
        while (true) {
            println("\n--- Teacher Menu ---")
            options.forEachIndexed { i, s -> println("${i + 1}. $s") }
            print("Choose: ")
            when (readLine()?.trim()) {
                "1" -> questions.addQuestions()
                "2" -> questions.editQuestions()
                "3" -> questions.deleteQuestions()
                "4" -> questions.displayQuestions()
                "5" -> exitProcess(1)
                else -> println("Invalid choice")
            }
        }
    }

}

                                   //   STUDENT PORTAL //
class Student(): User() {
    val authentication = Authentication()
    val questions = MainQuiz()
    override lateinit var password: String
    override lateinit var email: String

    override
    fun login() {
        println("\n\n==================")
        println(" STUDENT LOGIN    ")
        println("==================")
        println("Enter your email:")
        email = readLine()!!
        println("Enter your password:")
        password = readLine()!!
        val check = authentication.teacherVerify(email, password)
        when (check) {
            true -> mainmenu()
            false -> {
                println("Wrong Credentials! Enter again ")
                login()
            }
        }

    }
    override
    fun mainmenu() {
        val options = arrayOf( // Arrays + Scopes (apply)
            "Play Quiz", "Exit"
        )
        while (true) { // Loops
            println("\n--- Student Menu ---")
            options.forEachIndexed { i, s -> println("${i + 1}. $s") }
            print("Choose: ")
            when (readLine()?.trim()) {
                "1" -> questions.displayQuiz()
                "2" -> exitProcess(1)
            }
        }
    }

}




