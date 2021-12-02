import org.junit.jupiter.api.Test
import java.io.*
import java.util.*
import kotlin.test.assertEquals

class Test {
    fun testKnapSackSolver(number: Int) {
        val scanner = Scanner(File("tests/KnapsackSolver/input/$number.txt"))
        val writer = File("tests/KnapsackSolver/myOutput/$number.txt").bufferedWriter().use {
            var str = ""
            while (scanner.hasNextLine()) {
                try {
                    str = scanner.nextLine()
                    if (str.isEmpty()) continue
                    for (symbol in str) {
                        if (!symbol.isDigit()) throw IllegalArgumentException()
                    }
                    break
                } catch (exception: Exception) {
                    it.write("error\n")
                }
            }
            val capacity = str.toInt()
            val solver = KnapsackSolver(capacity)

            try {
                while (scanner.hasNext()) {
                    try {
                        val str = scanner.nextLine()
                        if (str.isEmpty()) continue
                        val parsedValues = parseInput(str)
                        val weight = parsedValues.first
                        val cost = parsedValues.second
                        solver.insert(weight, cost)
                    } catch (exception: Exception) {
                        it.write("error\n")
                    }
                }
                val answer = solver.calculate()
                it.write("${answer.totalWeight} ${answer.totalCost}\n")
                for (index in answer.indexes) {
                    it.write(index.toString())
                    it.write("\n")
                }
            } catch (exception: Exception) {
                println("An error has occurred: ${exception.message}")
            }
        }
    }
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
       for (i in 1..16) {
            println("test #$i")
            testKnapSackSolver(i)
            val sc1 = Scanner(File("tests/KnapsackSolver/myOutput/$i.txt"))
            val sc2 = Scanner(File("tests/KnapsackSolver/output/$i.txt"))
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