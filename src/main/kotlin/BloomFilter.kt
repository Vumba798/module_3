import java.lang.Math.pow
import kotlin.math.ln
import kotlin.math.log2
import kotlin.math.roundToInt

class BloomFilter (
    private val n: Int,
    private val P: Float
    ) {
    private var primeNumbers = mutableListOf<Int>()
    private var m = (-n * log2(P) / ln(2.0)).roundToInt()
    private var hashSize =
    private val M = 2147483647 // 31-st Mersenne prime

    private fun hash(index: Int, key: Int) {

    }

    private fun getPrimeNumbers(amount: Int) {
        val sieveSequence = sequence {
            var value = 2
            while (true) {
                yield(value++)
            }
        }
        for (i in 0 until amount) {
            val primeNumber = sieveSequence.first()
            primeNumbers.add(primeNumber)
            sieveSequence
                .filter {
                    it.rem(primeNumber) != 0
                }
        }
    }
}

fun main() {
}