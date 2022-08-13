package com.example.androidlearningproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


data class Person(
    val name: String = "",
    val age: Int = -1
)

data class Car(
    val brand: String = "",
    val year: Int = 2000
)

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    private val personDocument = Firebase.firestore.collection("coroutines")
        .document("persoana")
    private val carDocument = Firebase.firestore.collection("coroutines")
        .document("car")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvData: TextView = findViewById<TextView>(R.id.tvData)

        val peter = Person("Peter", 25)
        lifecycleScope.launch {
            personDocument.set(peter)
        }

        val car = Car("Mercedes", 2020)
        lifecycleScope.launch {
            carDocument.set(car)
        }

        lifecycleScope.launch {
            var time = 1000L
            while (time <= 6000L) {
                delay(1000L)
                Log.d(TAG, "second")
                time += 1000L
            }
        }

        // Task: print person got the car when both person/car jobs are finished
        val personResult = lifecycleScope.async {
            delay(2000L)
            personDocument.get().await().toObject(Person::class.java)
        }

        val carResult = lifecycleScope.async {
            delay(4000L)
            carDocument.get().await().toObject(Car::class.java)
        }

        lifecycleScope.launch {
            Log.d(TAG, "Person ${personResult.await()?.name}")
            Log.d(TAG, "Car ${carResult.await()?.brand}")
            Log.d(TAG, "Person ${personResult.await()?.name} got the car ${carResult.await()?.brand}")
        }
    }

}










