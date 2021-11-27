import java.io.BufferedWriter
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.lang.Integer.max
import java.util.*

class KnapsackSolver(private val capacity: Int) {
    data class Item(
        val weight: Int,
        val cost: Int
    )
    private var items = mutableListOf<Item>()
    private var table = mutableListOf(mutableListOf<Int>())
    private var listOfIndexes = mutableListOf<Int>() // we will hold the answer with indexes of items
    private var totalWeight = 0


    fun insert(weight: Int, cost: Int) {
        items.add(Item(weight, cost))
    }
    fun calculate(): Result {
        fillTable()
        getListOfIndexes()
        return formResult()
    }

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

    fun formResult(): Result {
        val totalCost = table.last().last()
        return Result(totalWeight, totalCost, listOfIndexes)
    }
    data class Result(
        val totalWeight: Int,
        val totalCost: Int,
        val indexes: List<Int>
    )
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
    TODO("ITS NOT WORKING (table)")
}