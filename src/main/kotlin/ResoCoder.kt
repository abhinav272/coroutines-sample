import kotlinx.coroutines.*

/**
 * https://resocoder.com/2018/10/06/kotlin-coroutines-tutorial-stable-version-async-await-withcontext-launch/
 * */

fun main() {
//    exampleBlocking()

//    exampleBlockingDispatcher()

//    exampleGlobalLaunch()

//    exampleGlobalLaunchWaiting()

    exampleCoroutineLaunchWaiting()
}

suspend fun printDelayed(msg: String) {
    delay(1000)
    println(msg)
}

fun exampleBlocking() = runBlocking {
    println("One")
    printDelayed("Two")
    println("Three")
}

fun exampleBlockingDispatcher() {
    runBlocking(Dispatchers.Default){
        println("One - ${Thread.currentThread().name}")
        printDelayed("Two - ${Thread.currentThread().name}")
    }

    println("Three - ${Thread.currentThread().name}")
}

fun exampleGlobalLaunch() = runBlocking {
    println("One - ${Thread.currentThread().name}")

    GlobalScope.launch {
        printDelayed("Two - ${Thread.currentThread().name}")
    }

    println("Three - ${Thread.currentThread().name}")

    delay(3000)
}

/**
 * Waiting for exact time to finish launch
 * This is launching in Global Scope
 * */
fun exampleGlobalLaunchWaiting() = runBlocking {
    println("One - ${Thread.currentThread().name}")

    val job = GlobalScope.launch {
        printDelayed("Two - ${Thread.currentThread().name}")
    }

    println("Three - ${Thread.currentThread().name}")
    job.join()
}

/**
 * Waiting for exact time to finish launch
 * This is launching in Local Coroutine Scope
 * In this example job.join() is not required
 * All will be done on main thread
 * */
fun exampleCoroutineLaunchWaiting() = runBlocking {
    println("One - ${Thread.currentThread().name}")

    launch {
        printDelayed("Two - ${Thread.currentThread().name}")
    }

    launch(Dispatchers.IO) {
        printDelayed("Two & half - ${Thread.currentThread().name}")
    }

    println("Three - ${Thread.currentThread().name}")
}