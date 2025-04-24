package edu.nku.classapp.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import edu.nku.classapp.R
import edu.nku.classapp.viewmodel.AuthViewModel
import kotlinx.coroutines.launch
import edu.nku.classapp.databinding.FragmentLoginPageBinding
import androidx.navigation.fragment.findNavController


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginPageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginPageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = view.findViewById<Button>(R.id.signInButton)
        Log.d("LoginFragment", "Button found: ${button != null}")


        binding.signInButton.setOnClickListener {
            val email = binding.usernameEmail.text.toString().trim()
            val password = binding.password.text.toString().trim()
            Log.d("LoginButtn", "This is a debug message")

            if (email.isNotEmpty() && password.length >= 6) {
                viewModel.login(email, password)
            } else {
                Toast.makeText(requireContext(), "Enter valid email and password", Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launch {
            viewModel.authState.collect { state ->
                when (state) {
                    is AuthViewModel.AuthState.Loading -> binding.signInButton.isEnabled = true
                    is AuthViewModel.AuthState.Success -> {
                        binding.signInButton.isEnabled = true
                        val token = state.response.token
                        val prefs = requireContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
                        prefs.edit().putString("AUTH_TOKEN", token).apply()

                        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                    }
                    is AuthViewModel.AuthState.Failure -> {
                        binding.signInButton.isEnabled = true
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
