package com.example.androidlearningproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


data class Person(
    val name: String = "",
    val age: Int = -1
)

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tutorialDocument = Firebase.firestore.collection("coroutines")
            .document("tutorial")
        val peter = Person("Peter", 25)

        GlobalScope.launch(Dispatchers.IO) {
            delay(3000L)
            tutorialDocument.set(peter).await()
            val person = tutorialDocument.get().await().toObject(Person::class.java)
            withContext(Dispatchers.Main) {
                val tv = findViewById<TextView>(R.id.tvData)
                tv.text = person.toString()
            }
        }
    }
}