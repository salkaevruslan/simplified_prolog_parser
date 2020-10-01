import kotlin.math.min

class Parser constructor(private var str: String, private var silent: Boolean = true) {

    private var pos: Int = 0
    private var nextToLastSuccessPos: Int = 0

    fun parse(): Boolean {
        skipWhitespace()
        if (parseProgram()) {
            return true
        }
        if (!silent)
            printErrorInfoByPos(nextToLastSuccessPos)
        return false
    }

    private fun parseProgram(): Boolean {
        skipWhitespace()
        val initPos = pos
        if (pos == str.length) {
            nextToLastSuccessPos = pos
            return true
        }
        if (!parseRelation()) {
            pos = initPos
            return false
        }
        if (parseProgram()) {
            nextToLastSuccessPos = pos
            return true
        }
        pos = initPos
        return false
    }

    private fun parseRelation(): Boolean {
        skipWhitespace()
        val initPos = pos
        if (!parseID() || !parseSymbol('.')) {
            pos = initPos
            if (!parseID() || !parseCork() || !parseDisjunction() || !parseSymbol('.')) {
                pos = initPos
                return false
            }
        }
        nextToLastSuccessPos = pos
        return true
    }

    private fun parseDisjunction(): Boolean {
        skipWhitespace()
        val initPos = pos
        if (!parseConjunction() || !parseSymbol(';') || !parseDisjunction()) {
            pos = initPos
            if (!parseConjunction()) {
                pos = initPos
                return false
            }
        }
        nextToLastSuccessPos = pos
        return true
    }

    private fun parseConjunction(): Boolean {
        skipWhitespace()
        val initPos = pos
        if (!parseLiteral() || !parseSymbol(',') || !parseConjunction()) {
            pos = initPos
            if (!parseLiteral()) {
                pos = initPos
                return false
            }
        }
        nextToLastSuccessPos = pos
        return true
    }

    private fun parseLiteral(): Boolean {
        skipWhitespace()
        val initPos = pos
        if (!parseID()) {
            pos = initPos
            if (!parseSymbol('(') || !parseDisjunction() || !parseSymbol(')')) {
                pos = initPos
                return false
            }
        }
        nextToLastSuccessPos = pos
        return true
    }

    private fun parseSymbol(c: Char): Boolean {
        skipWhitespace()
        val initPos = pos
        if (pos < str.length) {
            if (str[pos] == c) {
                ++pos
                nextToLastSuccessPos = pos
                return true
            }
        }
        pos = initPos
        return false
    }

    private fun parseCork(): Boolean {
        skipWhitespace()
        val initPos = pos
        if (pos + 2 <= str.length && str.subSequence(pos, pos + 2) == ":-") {
            pos += 2
            nextToLastSuccessPos = pos
            return true
        }
        pos = initPos
        return false
    }

    private fun parseID(): Boolean {
        skipWhitespace()
        val initPos = pos
        val idRegex = "[a-zA-Z_][a-zA-Z0-9_]*".toRegex()
        val match = idRegex.find(str, pos)
        if (match != null && match.range.first == pos) {
            pos = match.range.last + 1
            nextToLastSuccessPos = pos
            return true
        }
        pos = initPos
        return false
    }

    private fun printErrorInfoByPos(p: Int) {
        var lineNum = 1
        var linePos = 1
        for (i in 0..min(str.length - 1, p - 1)) {
            if (str[i] == '\n') {
                linePos = 1
                ++lineNum
            } else {
                ++linePos
            }
        }
        println("Syntax error: line $lineNum, pos $linePos")
    }

    private fun skipWhitespace() {
        val whitespaces = arrayOf(' ', '\n', '\t', '\r')
        while (pos < str.length && whitespaces.contains(str[pos]))
            ++pos
    }
}