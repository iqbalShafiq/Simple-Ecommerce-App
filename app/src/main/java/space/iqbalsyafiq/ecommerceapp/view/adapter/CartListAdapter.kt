package space.iqbalsyafiq.ecommerceapp.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_cart.view.*
import space.iqbalsyafiq.ecommerceapp.databinding.ItemCartBinding
import space.iqbalsyafiq.ecommerceapp.model.cart.CartMessage
import space.iqbalsyafiq.ecommerceapp.view.fragment.ListCartFragment

class CartListAdapter(
    private val context: Context,
    private val carts: ArrayList<CartMessage>,
    private val fragment: ListCartFragment
) : RecyclerView.Adapter<CartListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private lateinit var binding: ItemCartBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCartBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )

        return ViewHolder(binding.root)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cart = carts[position]

        with(holder.itemView) {
            tvItemPrice.text = "Rp${cart.itemBasePrice}"
            tvItemName.text = cart.itemName
            tvItemCode.text = cart.itemCode
            tvSumItem.text = cart.sum.toString()
            tvSubtotalPrice.text = "Rp${cart.itemSubtotal}"

            btnAddItem.setOnClickListener {
                fragment.addItemToCart(cart.itemCode!!)
            }
        }
    }

    override fun getItemCount(): Int = carts.size
}