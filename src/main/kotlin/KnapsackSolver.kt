import java.io.BufferedWriter
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.lang.Integer.max
import java.util.*

class KnapsackSolver(private var capacity: Int) {
    data class Item(
        var weight: Int,
        val cost: Int
    )
    data class TakenItems(var indexes: BitSet) {
        var cost = 0
    }
    private var items = mutableListOf<Item>()
    private var dp = mutableListOf<TakenItems>()
    private var listOfIndexes = mutableListOf<Int>() // we will hold the answer with indexes of items
    private var totalWeight = 0
    private var weightGcd = 0


    fun insert(weight: Int, cost: Int) {
        items.add(Item(weight, cost))
    }
    fun calculate(): Result {
        modifyWeights()
        fillTable()
        return formResult()
    }
    private fun modifyWeights() { // divides capacity and items' weight on greater common divisor
        weightGcd = getGcdOfAll()
        capacity /= weightGcd
        for (item in items) {
            item.weight /= weightGcd
        }
    }

    private fun getGcdOfAll(): Int { // returns gcd of capacity and all items' weights
        var tmpGcd = capacity
        for (i in 0 until items.size) {
            tmpGcd = gcd(tmpGcd, items[i].weight)
        }
        return tmpGcd
    }

    private fun gcd(value1: Int, value2: Int): Int {
        var a = value1
        var b = value2
        while (a > 0 && b > 0) {
            if (a > b) {
                a = a.rem(b)
            } else {
                b = b.rem(a)
            }
        }
        return (a + b)
    }

    private fun fillTable() {
        for (i in 0..capacity) {
            dp.add(TakenItems(BitSet(items.size)))
        }
        for (i in 0 until items.size) {
            for (j in capacity downTo 0) {
                if (items[i].weight == j) {
                    if (items[i].cost > dp[j].cost) {
                        dp[j].cost = items[i].cost
                        dp[j].indexes.clear()
                        dp[j].indexes.set(i)
                    }
                } else if (items[i].weight < j) {
                    if (items[i].cost + dp[j - items[i].weight].cost > dp[j].cost) {
                        dp[j].indexes.clear()
                        dp[j].indexes.or(dp[j - items[i].weight].indexes)
                        dp[j].indexes.set(i)
                        dp[j].cost = items[i].cost + dp[j - items[i].weight].cost
                    }
                }  else break
            }
        }
    }


    /*
    private fun fillTable() {
        val rows = items.size
        val columns = capacity
        println("Im ready!")
        //inits first item
        for (j in 0 until columns) {
            // fills first item
            if (items[0].weight <= j + 1) {
                table[0].add(items[0].cost)
            } else {
                table[0].add(0)
            }
        }
        for (i in 1 until rows) {
            //todo works bad
            table.add(mutableListOf())
            for (j in 0 until columns) {
                if (items[i].weight > j + 1) {
                    table[i].add(table[i - 1][j])
                } else if (items[i].weight == j + 1) {
                    table[i].add(max(table[i - 1][j], items[i].cost))
                } else {
                    table[i].add(max(table[i - 1][j], items[i].cost + table[i - 1][j - items[i].weight]))
                }
            }
        }

    }
    */

    /*
    private fun getListOfIndexes() {
        val rows = items.size
        val columns = capacity

        var i = rows - 1
        var j = columns - 1
        while (i >= 0 && j >= 0 && table[i][j] > 0) {
            if (i == 0 && table[i][j] > 0) {
                listOfIndexes.add(0, i)
                items[i].weight
                break
            }
            if (table[i][j] == table[i - 1][j]) {
                i--
            } else {
                listOfIndexes.add(0, i)
                totalWeight += items[i].weight
                j -= items[i].weight
                i--
            }
        }
    }
*/
    fun formResult(): Result {
        val totalCost = dp.last().cost
        val totalWeight = dp.lastIndex * weightGcd

        var indexes = mutableListOf<Int>()
        var tmpIndex = dp.last().indexes.nextSetBit(0)
        while (tmpIndex != -1) {
            indexes.add(tmpIndex + 1)
            tmpIndex = dp.last().indexes.nextSetBit(tmpIndex + 1)
        }
        return Result(totalWeight, totalCost, indexes)
    }
    data class Result(
        val totalWeight: Int,
        val totalCost: Int,
        val indexes: List<Int>
    )
    /*
    fun print(out: OutputStream) {
        // TODO remove
        val writer = BufferedWriter(OutputStreamWriter(out))
        var rows = items.size
        var columns = capacity
        for (i in 0 until rows) {
            for (j in 0 until columns) {
                print(table[i][j])
                print(" ")
            }
            println()
        }
    }
     */
}

fun main() {
    val scanner = Scanner(System.`in`)
    val capacity = scanner.nextInt()
    val solver = KnapsackSolver(capacity)
    while (scanner.hasNext()) {
        val weight = scanner.nextInt()
        val cost = scanner.nextInt()
        solver.insert(weight, cost)
    }
    val answer = solver.calculate()
    print(answer.totalWeight)
    print(" ")
    println(answer.totalCost)
    for (index in answer.indexes) {
        println(index)
    }
}