import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.thread

class MainClass {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
//            basicSample()
//            runLotOfThreads()
            runLotOfCoroutines()
//            runUsingAsync()


        }

        private fun runUsingAsync() {
            val defferedList = (1..1_000_000).map {
                GlobalScope.async {
                    work(it)
                }
            }

            runBlocking {
                val sumBy = defferedList.sumBy { it.await() }
                println(sumBy)
            }
        }

        suspend fun work(n: Int): Int {
            delay(1000)
            return n
        }

        private fun runLotOfCoroutines() {
            /*This took only 1 sec*/
            println(System.currentTimeMillis())
            val c = AtomicLong()

            for (i in 1..1_000_000L)
                GlobalScope.launch {
                    c.addAndGet(i)
//                    println(Thread.currentThread().name)
                }

            println(c.get())
            println(System.currentTimeMillis())
        }

        private fun runLotOfThreads() {
            /*This took around 50 secs*/
            println(System.currentTimeMillis())
            val c = AtomicLong()

            for (i in 1..1_000_000L)
                thread(start = true) {
                    c.addAndGet(i)
                }

            println(c.get())
            println(System.currentTimeMillis())
        }

        private fun basicSample() {
            println("Kotlin main is running here!")

            GlobalScope.launch {
                delay(3000)
                println("inside coroutine")
            }

            println("main finish")
            //            Thread.sleep(4000)
            runBlocking { delay(4000) }
        }
    }
}