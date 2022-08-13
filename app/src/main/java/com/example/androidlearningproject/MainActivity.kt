package com.example.androidlearningproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
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
        // Also add those to suspend functions
//        lifecycleScope.launch {
//            Log.d(TAG, "Person ${.await()?.name}")
//            Log.d(TAG, "Car ${carResult.await()?.brand}")
//            Log.d(TAG, "Person ${personResult.await()?.name} got the car ${carResult.await()?.brand}")
//        }
        lifecycleScope.launch {
            Log.d(TAG, "Person ${getPersonFromFirestore()}")
        }

        lifecycleScope.launch {
            Log.d(TAG, "Car ${getCarFromFirestore()}")
        }

//        val personResult = lifecycleScope.async {
//            delay(2000L)
//            personDocument.get().await().toObject(Person::class.java) ?: Person()
//        }
//
//        val carResult = lifecycleScope.async {
//            delay(4000L)
//            carDocument.get().await().toObject(Car::class.java) ?: Car()
//        }

        // make it async
        lifecycleScope.launch {
            val personResult = getPersonFromFirestore()
            val carResult = getCarFromFirestore()
            Log.d(TAG, "Person ${personResult.name} got the car ${carResult.brand}")
        }

    }

    private suspend fun getPersonFromFirestore(): Person {
        delay(2000L)
        return personDocument.get().await().toObject(Person::class.java) ?: Person()
    }

    private suspend fun getCarFromFirestore(): Car {
        delay(4000L)
        return carDocument.get().await().toObject(Car::class.java) ?: Car()
    }
}










