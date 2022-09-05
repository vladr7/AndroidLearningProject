package com.example.androidlearningproject

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runBlocking {
            val num1 = async { getValue() }
            val num2 = async { getValue() }
            println("result of num1 + num2 is ${num1.await() + num2.await()}")
        }
        println("vlad: outside")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME
    @RequiresApi(Build.VERSION_CODES.O)
    val time = { formatter.format(LocalDateTime.now()) }

    suspend fun getValue(): Double {
        println("vlad: entering getValue() at ${time()}")
        delay(3000)
        println("vlad: leaving getValue() at ${time()}")
        return Math.random()
    }


}