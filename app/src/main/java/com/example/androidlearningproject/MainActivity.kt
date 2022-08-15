package com.example.androidlearningproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import kotlin.system.measureTimeMillis


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
        val mercedes = Car("Mercedes", 2020)

        lifecycleScope.launch {
            var time = 1000L
            while (time <= 8000L) {
                delay(1000L)
                Log.d(TAG, "${time/1000L}s")
                time += 1000L
            }
        }

        // sequentially (takes 4-5 seconds)
        lifecycleScope.launch(Dispatchers.IO) {
            val time = measureTimeMillis{
                setPerson(peter)
                setCar(mercedes)
                val person = getPerson()
                val car = getCar()
                Log.d(TAG, "${person.name} got the car ${car.brand}")
                withContext(Dispatchers.Main) {
                    tvData.text = "${person.name} got the car ${car.brand}"
                }
            }
            Log.d(TAG, "Requests took $time ms")
        }

        // async
        lifecycleScope.launch(Dispatchers.IO) {
            val time = measureTimeMillis {

            }
        }

    }

    suspend fun getPerson(): Person {
        delay(1000L)
        return personDocument.get().await().toObject(Person::class.java) ?: Person()
    }

    suspend fun getCar(): Car {
        delay(1000L)
        return carDocument.get().await().toObject(Car::class.java) ?: Car()
    }

    suspend fun setPerson(person: Person) {
        delay(1000L)
        personDocument.set(person).await()
    }

    suspend fun setCar(car: Car) {
        delay(1000L)
        carDocument.set(car).await()
    }
}










