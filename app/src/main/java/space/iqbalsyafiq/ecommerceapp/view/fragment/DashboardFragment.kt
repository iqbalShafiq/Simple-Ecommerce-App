package space.iqbalsyafiq.ecommerceapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_dashboard.*
import space.iqbalsyafiq.ecommerceapp.databinding.FragmentDashboardBinding
import space.iqbalsyafiq.ecommerceapp.model.cart.CartRequest
import space.iqbalsyafiq.ecommerceapp.model.item.ItemMessage
import space.iqbalsyafiq.ecommerceapp.model.item.ItemRequest
import space.iqbalsyafiq.ecommerceapp.view.adapter.ListItemAdapter
import space.iqbalsyafiq.ecommerceapp.viewmodel.DashboardViewModel

class DashboardFragment : Fragment() {

    companion object {
        const val TAG = "DashboardFrag"
    }

    private lateinit var binding: FragmentDashboardBinding
    private val args by navArgs<DashboardFragmentArgs>()
    private val viewModel: DashboardViewModel by activityViewModels()
    private lateinit var adapter: ListItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get list item from remote
        Log.d(TAG, "onViewCreated: ${args.token}")
        viewModel.getItemList(args.token)

        // set view event
        with(binding) {
            swipeRefresh.setOnRefreshListener {
                viewModel.getItemList(args.token)
                swipeRefresh.isRefreshing = false
                etSearch.setText("")
            }

            ivCart.setOnClickListener {
                val action = DashboardFragmentDirections.navigateToListCartFragment(args.token)
                Navigation.findNavController(binding.root).navigate(action)
            }

            tvLogout.setOnClickListener {
                viewModel.logoutUser()
            }

            etSearch.addTextChangedListener {
                viewModel.getItemList(args.token, ItemRequest(it.toString()))
            }
        }

        // observe livedata
        observeLiveData()
    }

    fun goToDetail(item: ItemMessage) {
        val action = DashboardFragmentDirections.navigateToDetailFragment(item)
        Navigation.findNavController(binding.root).navigate(action)
    }

    fun addItemToCart(itemCode: String) {
        viewModel.addItemToCart(args.token, CartRequest(itemCode))
    }

    private fun observeLiveData() {
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                with(binding) {
                    progressLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
                    rvItemList.visibility = if (!isLoading) View.VISIBLE else View.GONE
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { isError ->
            isError?.let {
                with(binding) {
                    tvErrorMessage.visibility = if (isError) View.VISIBLE else View.GONE
                    rvItemList.visibility = if (!isError) View.VISIBLE else View.GONE
                }
            }
        }

        viewModel.items.observe(viewLifecycleOwner) { itemList ->
            itemList?.let {
                adapter = ListItemAdapter(
                    requireContext(),
                    itemList as ArrayList<ItemMessage>,
                    this
                )
                rvItemList.adapter = adapter
            }
        }

        viewModel.logout.observe(viewLifecycleOwner) { isLogout ->
            isLogout?.let {
                if (isLogout) {
                    val action = DashboardFragmentDirections
                        .navigateBackToLoginFragment()
                    Navigation.findNavController(binding.root).navigate(action)
                }
            }
        }

        viewModel.addItem.observe(viewLifecycleOwner) { isAdded ->
            isAdded?.let {
                if (isAdded) {
                    Toast.makeText(requireContext(), "Item has been added.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}