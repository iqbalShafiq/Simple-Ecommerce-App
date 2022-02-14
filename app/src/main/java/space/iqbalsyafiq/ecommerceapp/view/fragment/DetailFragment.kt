package space.iqbalsyafiq.ecommerceapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import space.iqbalsyafiq.ecommerceapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set view
        val item = args.itemMessage
        with(binding) {
            tvItemName.text = item.itemName
            tvItemCode.text = item.itemCode
            tvItemPrice.text = item.itemPrice.toString()

            ivBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }

    }
}