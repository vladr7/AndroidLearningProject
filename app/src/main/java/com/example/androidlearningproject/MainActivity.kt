package com.example.androidlearningproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val job = GlobalScope.launch(Dispatchers.Default) {
            println("vlad Starting long running calculation...")
            withTimeout(3000L) {
                for(i in 30..40){
                    if (isActive) {
                        println("vlad Result for i = $i: ${fib(i)}")
                    }
                }
            }

            println("vlad Ending long running calculation...")
        }
    }

    fun fib(n: Int): Long{
        return if(n == 0) 0
        else if(n == 1) 1
        else fib(n - 1) + fib(n - 2)
    }
}