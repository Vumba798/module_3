import org.junit.jupiter.api.Test
import java.io.*
import java.util.*
import kotlin.test.assertEquals

class Test {
    fun testCrazyRich(number: Int) {
        val scanner = Scanner(File("tests/CrazyRich/input/$number.txt"))
        val money = scanner.nextLong()
        val cr = CrazyRich(money)
        val stack = cr.getAnswer()
        val writer = File("tests/CrazyRich/myOutput/$number.txt").bufferedWriter().use {
            while (stack.size > 0) {
                when(stack.pop()) {
                    Operation.INC -> it.write("inc\n")
                    Operation.DEC -> it.write("dec\n")
                    Operation.DBL -> it.write("dbl\n")
                }
            }
        }
    }

    @Test
    fun testOutput() {
        for (i in 1..102) {
            println("test #$i")
            testCrazyRich(i)
            val sc1 = Scanner(File("tests/CrazyRich/myOutput/$i.txt"))
            val sc2 = Scanner(File("tests/CrazyRich/output/$i.txt"))
            var numberOfLine = 1
            while (sc2.hasNext()) {
                val myOutput = sc1.nextLine()
                val trueOutput = sc2.nextLine()
                //println("Line: $numberOfLine")
                assertEquals(trueOutput, myOutput)
                numberOfLine++
            }
        }
    }
}