package edu.nku.classapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import edu.nku.classapp.ui.auth.LoginActivity
import edu.nku.classapp.PortfolioActivity
import edu.nku.classapp.R
//import edu.nku.classapp.WatchlistActivity
import edu.nku.classapp.databinding.ActivityProfileBinding
import edu.nku.classapp.di.AppModule
import edu.nku.classapp.ui.state.UserProfileUiState
import edu.nku.classapp.viewmodel.UserProfileViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileFragment : Fragment() {

    private var _binding: ActivityProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UserProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[UserProfileViewModel::class.java]

        // Back button
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        // Get token from SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("AUTH_TOKEN", null)

        if (token != null) {
            viewModel.fetchProfile(token)
            setupButtons(token)
        } else {
            navigateToLogin()
        }

        observeUiState()

    }

    private fun observeUiState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UserProfileUiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UserProfileUiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val profile = state.profile
                    binding.nameTextView.text = profile.name
                    binding.usernameTextView.text = "@${profile.username}"
                    binding.emailTextView.text = profile.email

                    if (!profile.avatar.isNullOrEmpty()) {
                        Glide.with(binding.root)
                            .load(profile.avatar)
                            .into(binding.avatarImageView)
                    } else {
                        binding.avatarImageView.setImageResource(R.drawable.default_avatar)
                    }
                }

                is UserProfileUiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setupButtons(token: String?) {
        binding.watchlistButton.setOnClickListener {
            Toast.makeText(requireContext(), "Watchlist screen coming soon!", Toast.LENGTH_SHORT).show()
//            startActivity(Intent(requireContext(), WatchlistActivity::class.java))
        }

        binding.portfolioButton.setOnClickListener {
            startActivity(Intent(requireContext(), PortfolioActivity::class.java))
        }

        binding.logoutButton.setOnClickListener {
            if (token != null) {
                AppModule.instance.logout("Token $token").enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            val sharedPrefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
                            sharedPrefs.edit().remove("AUTH_TOKEN").apply()
                            navigateToLogin()
                        } else {
                            Toast.makeText(requireContext(), "Logout failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(requireContext(), "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
