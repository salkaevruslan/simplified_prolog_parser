import java.io.File

fun main(args: Array<String>) {
    val filename: String? = args[0]
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