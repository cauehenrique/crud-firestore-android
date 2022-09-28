package com.example.crud.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crud.R
import com.example.crud.model.Product
import com.google.firebase.firestore.FirebaseFirestore

class ProductAdapter(
	private val context: Context,
	private val products: MutableList<Product>,
	private val firestore: FirebaseFirestore
) :
	RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
	inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val name = itemView.findViewById<TextView>(R.id.productName)
		val price = itemView.findViewById<TextView>(R.id.productPrice)

		val editButton = itemView.findViewById<Button>(R.id.editButton)
		val deleteButton = itemView.findViewById<Button>(R.id.deleteButton)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
		val item = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false)
		return ProductViewHolder(item)
	}

	override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
		holder.name.text = products[position].name
		holder.price.text = products[position].price.toString()
//
//		holder.editButton.setOnClickListener {
//			firestore.collection("product").document(products[position].id).delete()
//				.addOnCompleteListener { task ->
//					if (task.isSuccessful) products.removeAt(position)
//				}
//		}
	}

	override fun getItemCount(): Int = products.size
}