import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val tmp = "hi he"
    println(tmp.substring(3, tmp.length))
}
 fun getGCD(): Int { // returns greater common divisor of all items' weights
    var items = mutableListOf(8,4)
    var tmpGcd = items[0]
    for (i in 1 until items.size) {
        tmpGcd = gcd(tmpGcd, items[i])
    }
    return tmpGcd
}
fun gcd(value1: Int, value2: Int): Int {
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
