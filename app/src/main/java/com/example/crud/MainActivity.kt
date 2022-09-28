package com.example.crud

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crud.adapters.ProductAdapter
import com.example.crud.model.Product
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
	private val firestore = Firebase.firestore

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		supportActionBar!!.hide()

		val productsView = findViewById<RecyclerView>(R.id.productsRecyclerView)
		productsView.layoutManager = LinearLayoutManager(this)
		productsView.setHasFixedSize(true)

		val products: MutableList<Product> = mutableListOf()
		productsView.adapter = ProductAdapter(this, products, firestore)

		firestore.collection("product").get()
			.addOnSuccessListener { result ->
				for (product in result) {
					findViewById<TextView>(R.id.errorText).text = product.data["name"].toString()

					val id = product.id
					val name = product.data["name"].toString()
					val price = product.data["price"].toString().toFloat()

					val productInstance = Product(id, name, price)
					products.add(productInstance)
				}
			}
			.addOnFailureListener { exception ->
				findViewById<TextView>(R.id.errorText).text = exception.toString()
			}

//		val helloWorldTextView = findViewById<TextView>(R.id.helloWorld)
//		db.collection("product").get()
//			.addOnSuccessListener { result ->
//				for (product in result) {
//					helloWorldTextView.text = "${product.id}"
//				}
//			}
//
	}
}