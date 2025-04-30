package edu.nku.classapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.nku.classapp.R
import edu.nku.classapp.databinding.FragmentSignupPageBinding
import edu.nku.classapp.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private var _binding: FragmentSignupPageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signupButton.setOnClickListener {
            val data = mapOf(
                "first_name" to binding.firstNameEdittextSignup.text.toString().trim(),
                "last_name" to binding.lastNameEdittextSignup.text.toString().trim(),
                "username" to binding.usernameEdittextSignup.text.toString().trim(),
                "email" to binding.emailEdittextSignup.text.toString().trim(),
                "password" to binding.passwordEdittextSignup.text.toString().trim()
            )

            if (data.values.all { it.isNotEmpty() }) {
                viewModel.signup(data)
            } else {
                Toast.makeText(requireContext(), "Fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.loginPage.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

        lifecycleScope.launch {
            viewModel.authState.collect { state ->
                when (state) {
                    is AuthViewModel.AuthState.Loading -> binding.signupButton.isEnabled = true
                    is AuthViewModel.AuthState.SignupSuccess -> {
                        binding.signupButton.isEnabled = true
                        Toast.makeText(requireContext(), "Signed up successfully!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
                    }
                    is AuthViewModel.AuthState.Failure -> {
                        binding.signupButton.isEnabled = true
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }
}
