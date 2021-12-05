import java.util.*
import kotlin.system.measureTimeMillis
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main(args: Array<String>) {
    fun make_sieve(src: Sequence<Int>, prime: Int) = src.filter { it % prime != 0 }
    var sieve = sequence {
        var x = 2
        while (true) yield(x++)
    }
    for (i in 1..37) {
        val prime = sieve.first()
        println(sieve.take(i).toList())
        sieve = make_sieve(sieve, prime)
        println("i = $i")
    }
}