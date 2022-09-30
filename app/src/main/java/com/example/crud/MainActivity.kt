package com.example.crud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {
	private val firestore = Firebase.firestore

	override fun onCreate(savedInstanceState: Bundle?) {
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		supportActionBar!!.hide()

		val itemsTextView = findViewById<TextView>(R.id.itemsTextView)
		val createButton = findViewById<Button>(R.id.createButton)
		val updateButton = findViewById<Button>(R.id.updateButton)
		val deleteButton = findViewById<Button>(R.id.deleteButton)

		createButton.setOnClickListener {
			val intent = Intent(this, CreateActivity::class.java)
			startActivity(intent)
			finish()
		}

		updateButton.setOnClickListener {
			val intent = Intent(this, UpdateActivity::class.java)
			startActivity(intent)
			finish()
		}

		deleteButton.setOnClickListener {
			val intent = Intent(this, DeleteActivity::class.java)
			startActivity(intent)
			finish()
		}

		firestore.collection("product").get()
			.addOnSuccessListener { result ->
				val numberFormat = NumberFormat.getCurrencyInstance()
				numberFormat.maximumFractionDigits = 2
				numberFormat.currency = Currency.getInstance("BRL")

				itemsTextView.text = ""

				for (product in result) {
					val id = product.id
					val name = product.data["name"].toString()
					val price = product.data["price"].toString().toFloat()
					val priceToBrl = numberFormat.format(price)

					itemsTextView.text = itemsTextView.text.toString() + "$name - $priceToBrl\n"
				}
			}
			.addOnFailureListener { exception ->
				Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT)
			}
	}
}