package com.example.crud

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UpdateActivity : AppCompatActivity() {
	private var lastId = ""
	private val firestore = Firebase.firestore

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_update)
		supportActionBar!!.hide()

		val products = ArrayList<String>()

		val nameEditText = findViewById<EditText>(R.id.productNameEditText)
		val priceEditText = findViewById<EditText>(R.id.productPriceEditText)
		val itemsSpinner = findViewById<Spinner>(R.id.itemsSpinner)
		val updateButton = findViewById<Button>(R.id.updateButton)
		val backButton = findViewById<Button>(R.id.backButton)

		updateButton.setOnClickListener {
			val productsCollection = firestore.collection("product")
			val data = hashMapOf(
				"name" to nameEditText.text.toString(),
				"price" to priceEditText.text.toString().toDouble()
			)
			productsCollection.document(lastId).set(data)
				.addOnSuccessListener {
					Toast.makeText(this, "Product has been updated successfully", Toast.LENGTH_SHORT).show()
					goBack()
				}
		}

		firestore.collection("product").get()
			.addOnSuccessListener { result ->
				for (product in result) {
					products.add(product.id)

					val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, products)
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
					itemsSpinner.adapter = adapter
				}
			}

		itemsSpinner.onItemSelectedListener = object : OnItemSelectedListener {
			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
				lastId = parent!!.getItemAtPosition(position).toString()
				grabDocumentData(lastId)
			}

			override fun onNothingSelected(parent: AdapterView<*>?) {}
		}
	}

	private fun goBack() {
		val intent = Intent(this, MainActivity::class.java)
		startActivity(intent)
		finish()
	}

	fun grabDocumentData(id: String) {
		val nameEditText = findViewById<EditText>(R.id.productNameEditText)
		val priceEditText = findViewById<EditText>(R.id.productPriceEditText)

		val firestoreProducts = firestore.collection("product")
		firestoreProducts.document(id).get()
			.addOnSuccessListener { result ->
				val productName = result.data?.get("name").toString()
				val productPrice = result.data?.get("price").toString()

				nameEditText.setText(productName)
				priceEditText.setText(productPrice)
			}
	}
}