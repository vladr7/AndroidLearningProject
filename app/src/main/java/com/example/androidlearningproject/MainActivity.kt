package com.example.androidlearningproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        // Task: print person got the car when both person/car jobs are finished
        lifecycleScope.launch {
            delay(2500L)
            printGotTheCar(tvData)
        }
    }

    private suspend fun printGotTheCar(tvData: TextView) {
        val personResult = personDocument.get().await().toObject(Person::class.java)
        val carResult = carDocument.get().await().toObject(Car::class.java)
        tvData.text = "Person ${personResult?.name} got the car ${carResult?.brand}"
    }
}










