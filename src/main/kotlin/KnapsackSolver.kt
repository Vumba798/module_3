import java.lang.Integer.max
import java.util.*

class KnapsackSolver(private val capacity: Int) {
    data class Item(
        val weight: Int,
        val cost: Int
    )
    private var items = mutableListOf<Item>()
    private var table = mutableListOf(mutableListOf<Int>())



    fun insert(weight: Int, cost: Int) {
        items.add(Item(weight, cost))
    }
    fun start() {
        val rows = items.size
        val columns = capacity
        println("Im ready!")
        //inits first item
        table.add(mutableListOf())
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
                } else {
                    table[i].add(max(table[i - 1][j], items[i].cost + table[i][j + 1 - items[i].weight]))
                }
            }
        }
        this.print()
    }

    fun print() {
        //TODO fix Encapsulation
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
    solver.start()
}