
//import java.io.File
//import java.util.Scanner
//
//fun displayQuiz() {
//    val scanner = Scanner(System.`in`)
//    val questions = loadQuestions("questions.txt")
//    var score = 0
//
//    println("Welcome to the Quiz!")
//    println("---------------------")
//
//    for ((index, q) in questions.withIndex()) {
//        println("\nQuestion ${index + 1}: ${q.question}")
//        q.options.forEachIndexed { i, option -> println("${'A' + i}. $option") }
//
//        print("Enter your answer (A/B/C/D): ")
//        val userAnswer = scanner.nextLine().uppercase()
//
//        if (userAnswer == q.correctAnswer.uppercase()) {
//            println("Correct!")
//            score++
//        } else {
//            println("Wrong! Correct answer: ${q.correctAnswer}")
//        }
//    }
//
//    println("\nQuiz Finished!")
//    println("Your final score: $score / ${questions.size}")
//}
//
//
//fun loadQuestions(filename: String): List<Question> {
//    val lines = File(filename).readLines().filter { it.isNotBlank() }
//    val questions = mutableListOf<Question>()
//
//    var i = 0
//    while (i < lines.size) {
//        val questionText = lines[i++]
//        val options = mutableListOf<String>()
//        repeat(4) { options.add(lines[i++]) }  // disply the 4 options of mcq
//        val correctAnswer = lines[i++]
//
//        questions.add(Question(questionText, options, correctAnswer))
//    }
//
//    return questions
//}



