package com.example.crud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateActivity : AppCompatActivity() {
	private val firestore = Firebase.firestore

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_create)
		supportActionBar!!.hide()

		val nameEditText = findViewById<EditText>(R.id.productNameEditText)
		val priceEditText = findViewById<EditText>(R.id.productPriceEditText)
		val createButton = findViewById<Button>(R.id.createButton)
		val backButton = findViewById<Button>(R.id.backButton)

		createButton.setOnClickListener {
			val name = nameEditText.text.toString()
			val price = priceEditText.text.toString().toDouble()

			val product = hashMapOf("name" to name, "price" to price)
			firestore.collection("product").add(product)
				.addOnSuccessListener {
					Toast.makeText(this, "Product has been created successfully", Toast.LENGTH_SHORT).show()
					goBack()
				}
				.addOnFailureListener { exception ->
					Toast.makeText(this, "Something went wrong! - $exception", Toast.LENGTH_SHORT).show()
				}
		}

		backButton.setOnClickListener { goBack() }
	}

	private fun goBack() {
		val intent = Intent(this, MainActivity::class.java)
		startActivity(intent)
		finish()
	}
}