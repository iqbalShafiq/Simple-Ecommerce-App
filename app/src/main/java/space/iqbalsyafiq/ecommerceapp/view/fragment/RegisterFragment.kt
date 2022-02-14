package space.iqbalsyafiq.ecommerceapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import space.iqbalsyafiq.ecommerceapp.databinding.FragmentRegisterBinding
import space.iqbalsyafiq.ecommerceapp.model.authentication.RegisterRequest
import space.iqbalsyafiq.ecommerceapp.viewmodel.RegisterViewModel

class RegisterFragment : Fragment() {

    companion object {
        const val TAG = "RegisterFrag"
    }

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            btnRegister.setOnClickListener {
                val email = etEmail.text.toString()
                val fullName = etFullName.text.toString()
                val password = etPassword.text.toString()

                if (email.isNotBlank() && fullName.isNotBlank() && password.isNotBlank()) {
                    viewModel.registerUser(
                        RegisterRequest(
                            email, fullName, password
                        )
                    )
                } else {
                    Toast.makeText(requireContext(), "Input should be filled", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            btnBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }

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

        viewModel.message.observe(viewLifecycleOwner) { messageResponse ->
            messageResponse?.let {
                if (messageResponse.equals("User has been added")) {
                    activity?.onBackPressed()
                    Toast.makeText(requireContext(), "You have registered", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}