import kotlinx.coroutines.*

fun main() {
//    exampleAsyncAwait()

//    exampleAsyncAwaitInSync()

    exampleWithContext()
}

suspend fun calculateHardThings(num: Int): Int {
    delay(1000)
    return num * 10
}

/**
 * All async coroutines run parallel
 * */

fun exampleAsyncAwait() = runBlocking {
    val startTime = System.currentTimeMillis()

    val def1 = async {
        calculateHardThings(9)
    }
    val def2 = async {
        calculateHardThings(2)
    }
    val def3 = async {
        calculateHardThings(5)
    }

    val sum = def1.await() + def2.await() + def3.await()

    val endTime = System.currentTimeMillis()

    println("Sum - ${sum}")
    println("Time taken - ${endTime - startTime}")
}


/**
 * All async coroutines run in sync
 * */

fun exampleAsyncAwaitInSync() = runBlocking {
    val startTime = System.currentTimeMillis()

    val def1 = async { calculateHardThings(9) }.await()
    val def2 = async { calculateHardThings(2) }.await()
    val def3 = async { calculateHardThings(5) }.await()

    val sum = def1 + def2 + def3

    val endTime = System.currentTimeMillis()

    println("Sum - ${sum}")
    println("Time taken - ${endTime - startTime}")
}

/**
 * All async coroutines run in sync
 * */

fun exampleWithContext() = runBlocking {
    val startTime = System.currentTimeMillis()

    val def1 = withContext(Dispatchers.Default){ calculateHardThings(9) }
    val def2 = withContext(Dispatchers.Default){ calculateHardThings(2) }
    val def3 = withContext(Dispatchers.Default){ calculateHardThings(5) }

    val sum = def1 + def2 + def3

    val endTime = System.currentTimeMillis()

    println("Sum - ${sum}")
    println("Time taken - ${endTime - startTime}")
}