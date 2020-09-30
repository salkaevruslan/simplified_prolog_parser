import java.io.File

fun main() {
    val filename: String? = readLine()
    if (filename != null && File(filename).exists()) {
        val str = File(filename).readText()
        val parser = Parser(str)
        if (parser.parse()) {
            println("Correct")
        } else {
            println("Incorrect")
        }
    } else {
        println("Wrong file name")
    }
}