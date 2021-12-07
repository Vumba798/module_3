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
                        if (!symbol.isDigit()) throw IllegalArgumentException("$symbol is not a digit")
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
                when (stack.pop()) {
                    Operation.INC -> it.write("inc\n")
                    Operation.DEC -> it.write("dec\n")
                    Operation.DBL -> it.write("dbl\n")
                }
            }
        }
    }

    fun testBloomFilter(number: Int) {
        val scanner = Scanner(File("tests/BloomFilter/input/$number.txt"))
        val writer = File("tests/BloomFilter/myOutput/$number.txt").outputStream()
        val bf = BloomFilter()
        var n: Int = 0
        var p: Float = 0f
        if (scanner.hasNextLine()) {
            while (scanner.hasNextLine()) {
                try {
                    // parsing set command
                    val line = scanner.nextLine()
                    if (line.isNotEmpty()) {
                        var lengthOfCommand = 0
                        if (line.substring(0, 4) != "set ") throw NoSuchMethodException("unknown command $line")
                        // parsing arguments of set command
                        var indexOfSecondSpace = 0
                        // parsing firstArgument
                        for (i in 4 until line.length) {
                            if (line[i] == ' ') {
                                indexOfSecondSpace = i
                                break
                            }
                            if (!line[i].isDigit()) throw IllegalArgumentException("symbol ${line[i]} in $line is not a digit")
                        }
                        if (indexOfSecondSpace < 5 || indexOfSecondSpace > line.length - 3) {
                            // 6 is because of shortest Int number # "set 1 xxxxx", where second space has 5th index
                            // 3 is because of shortest Float number # set xxxxx 0.0" where second space has length - 3 index
                            throw IllegalArgumentException("error")
                        }

                        // parsing second argument
                        if (line[indexOfSecondSpace + 1] != '0' || line[indexOfSecondSpace + 2] != '.') {
                            throw IllegalArgumentException("bad arguments in $line")
                        }
                        for (i in indexOfSecondSpace + 3 until line.length) {
                            if (!line[i].isDigit()) throw IllegalArgumentException("symbol ${line[i]} in $line is not a digit")
                        }

                        n = line.substring(4, indexOfSecondSpace).toInt()
                        p = line.substring(indexOfSecondSpace + 1, line.length).toFloat()

                        // using set command
                        val setResult: Pair<Int, Int> = bf.set(n, p)
                        writer.write("${setResult.first} ${setResult.second}\n".toByteArray())
                        break
                    } else {
                        throw NoSuchMethodException("error")
                    }
                } catch (exception: Exception) {
                    writer.write("error\n".toByteArray())
                }
            }
            while (scanner.hasNext()) {
                try {
                    val line = scanner.nextLine()
                    val commandAndKey = parseLine(line)
                    if (line.isNotEmpty()) {
                        when (commandAndKey.first) {
                            "add" -> {
                                bf.add(commandAndKey.second!!)
                            }
                            "search" -> {
                                val isFound = bf.search(commandAndKey.second!!)
                                if (isFound) writer.write("1\n".toByteArray())
                                else writer.write("0\n".toByteArray())
                            }
                            "print" -> {
                                if (commandAndKey.second != null) throw NoSuchMethodException("error")
                                bf.print(writer)
                            }
                            else -> {
                                throw NoSuchMethodException("error")
                            }
                        }
                    }
                } catch (exception: Exception) {
                    writer.write("error\n".toByteArray())
                }
            }
        }
    }

    @Test
    fun testOutput() {
        for (i in 1..22) {
            println("test #$i")
            testBloomFilter(i)
            val sc1 = Scanner(File("tests/BloomFilter/myOutput/$i.txt"))
            val sc2 = Scanner(File("tests/BloomFilter/output/$i.txt"))
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