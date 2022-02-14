package space.iqbalsyafiq.ecommerceapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import space.iqbalsyafiq.ecommerceapp.databinding.FragmentSplashScreenBinding
import space.iqbalsyafiq.ecommerceapp.viewmodel.SplashScreenViewModel

class SplashScreenFragment : Fragment() {

    companion object {
        const val TAG = "SplashScreenFrag"
    }

    private lateinit var binding: FragmentSplashScreenBinding
    private val viewModel: SplashScreenViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplashScreenBinding.inflate(
            layoutInflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.checkSession()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                with(binding) {
                    progressLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { isError ->
            isError?.let {
                with(binding) {
                    tvErrorMessage.visibility = if (isError) View.VISIBLE else View.GONE
                }
            }
        }

        viewModel.token.observe(viewLifecycleOwner) { token ->
            token?.let {
                Log.d(TAG, "observeLiveData: $token")
                if (token == "Guest") {
                    val action = SplashScreenFragmentDirections.navigateToLoginFragment()
                    Navigation.findNavController(binding.root).navigate(action)
                } else {
                    val action = SplashScreenFragmentDirections
                        .navigateToDashboardFragmentFromSplash(token)
                    Navigation.findNavController(binding.root).navigate(action)
                }
            }
        }
    }
}