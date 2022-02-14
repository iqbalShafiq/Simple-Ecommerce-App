package space.iqbalsyafiq.ecommerceapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import space.iqbalsyafiq.ecommerceapp.databinding.FragmentLoginBinding
import space.iqbalsyafiq.ecommerceapp.model.authentication.LoginRequest
import space.iqbalsyafiq.ecommerceapp.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                if (email.isNotBlank() && password.isNotBlank()) {
                    viewModel.loginUser(LoginRequest(email, password))
                } else {
                    Toast.makeText(
                        requireContext(),
                        "You have to input the entry",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            btnRegister.setOnClickListener {
                val action = LoginFragmentDirections
                    .navigateToRegisterFragment()
                Navigation.findNavController(binding.root).navigate(action)
            }
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                with(binding) {
                    progressLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
                    tvErrorMessage.visibility = if (isLoading) View.GONE else View.VISIBLE
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

        viewModel.message.observe(viewLifecycleOwner) { messageResponse ->
            messageResponse?.let {
                if (!messageResponse.equals("Guest")) {
                    val action = LoginFragmentDirections
                        .navigateToDashboardFragment(messageResponse)
                    Navigation.findNavController(binding.root).navigate(action)
                }
            }
        }
    }
}