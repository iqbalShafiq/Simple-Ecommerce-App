package space.iqbalsyafiq.ecommerceapp.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_layout.view.*
import space.iqbalsyafiq.ecommerceapp.databinding.ItemLayoutBinding
import space.iqbalsyafiq.ecommerceapp.model.item.ItemMessage
import space.iqbalsyafiq.ecommerceapp.view.fragment.DashboardFragment

class ListItemAdapter(
    private val context: Context,
    private val items: ArrayList<ItemMessage>,
    private val fragment: DashboardFragment
) : RecyclerView.Adapter<ListItemAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private lateinit var binding: ItemLayoutBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemLayoutBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )

        return ViewHolder(binding.root)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        // set view
        with(holder.itemView) {
            tvItemName.text = item.itemName
            tvItemPrice.text = "Rp${item.itemPrice}"

            // go to detail
            btnDetail.setOnClickListener {
                fragment.goToDetail(item)
            }

            // add to cart
            btnAddCart.setOnClickListener {
                fragment.addItemToCart(item.itemCode!!)
            }
        }
    }

    override fun getItemCount(): Int = items.size

}