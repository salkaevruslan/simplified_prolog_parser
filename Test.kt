import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val testsPath = Paths.get(Paths.get("").toAbsolutePath().toString(), "/tests")
    val paths = Files.walk(testsPath).filter { item -> item.toString().endsWith(".txt") }
    var done: Boolean = true
    paths.forEach {
        val str = File(it.toAbsolutePath().toString()).readText()
        val parser = Parser(str, true)
        var result: Boolean = parser.parse()
        var isTestCorrect: Boolean = it.toAbsolutePath().toString().endsWith("_c.txt")
        if (result.xor(isTestCorrect)) {
            done = false
            println("Test failed. File:" + it.toAbsolutePath().fileName.toString())
        } else {
            println("Test passed. File:" + it.toAbsolutePath().fileName.toString())
        }
    }

    if (done)
        println("Tests passed successfully")
    else
        println("Tests failed")

}