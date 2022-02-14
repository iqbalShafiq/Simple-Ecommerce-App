package space.iqbalsyafiq.ecommerceapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_list_cart.*
import space.iqbalsyafiq.ecommerceapp.databinding.FragmentListCartBinding
import space.iqbalsyafiq.ecommerceapp.model.cart.CartMessage
import space.iqbalsyafiq.ecommerceapp.model.cart.CartRequest
import space.iqbalsyafiq.ecommerceapp.view.adapter.CartListAdapter
import space.iqbalsyafiq.ecommerceapp.viewmodel.CartViewModel

class ListCartFragment : Fragment() {

    private lateinit var binding: FragmentListCartBinding
    private lateinit var adapter: CartListAdapter
    private val args by navArgs<ListCartFragmentArgs>()
    private val viewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListCartBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get cart list from remote
        viewModel.getCartList(args.token)

        // set view events
        with(binding) {
            swipeRefresh.setOnRefreshListener {
                viewModel.getCartList(args.token)
                swipeRefresh.isRefreshing = false
            }

            ivBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }

        // observe live data
        observeLiveData()
    }

    fun addItemToCart(itemCode: String) {
        viewModel.addItemToCart(args.token, CartRequest(itemCode))
    }

    private fun observeLiveData() {
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                with(binding) {
                    progressLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
                    rvCartList.visibility = if (!isLoading) View.VISIBLE else View.GONE
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { isError ->
            isError?.let {
                with(binding) {
                    tvErrorMessage.visibility = if (isError) View.VISIBLE else View.GONE
                    rvCartList.visibility = if (!isError) View.VISIBLE else View.GONE
                }
            }
        }

        viewModel.carts.observe(viewLifecycleOwner) { cartList ->
            cartList?.let {
                if (cartList.isEmpty()) {
                    tvEmptyCart.visibility = View.VISIBLE
                } else {
                    adapter = CartListAdapter(
                        requireContext(),
                        cartList as ArrayList<CartMessage>,
                        this
                    )
                    rvCartList.adapter = adapter
                }
            }
        }

        viewModel.addItem.observe(viewLifecycleOwner) { isAdded ->
            isAdded?.let {
                Toast.makeText(
                    requireContext(),
                    "Qty of the item has been added",
                    Toast.LENGTH_SHORT
                )
                    .show()
                viewModel.getCartList(args.token)
            }
        }
    }
}