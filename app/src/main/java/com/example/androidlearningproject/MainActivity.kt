package com.example.androidlearningproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val handler = CoroutineExceptionHandler{ _, throwable ->
            Log.d(TAG, "Caught exception: $throwable")
        }

        CoroutineScope(Dispatchers.Main).launch(handler) {
            supervisorScope {
                launch {
                    delay(300L)
                    throw Exception("Coroutine 1 failed")
                }
                launch {
                    delay(400L)
                    Log.d(TAG, "Coroutine 2 finished")
                }
            }
        }

    }


}










