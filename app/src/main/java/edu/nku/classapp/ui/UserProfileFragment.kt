package edu.nku.classapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import edu.nku.classapp.R
//import edu.nku.classapp.WatchlistActivity
import edu.nku.classapp.databinding.FragmentUserProfileBinding
import edu.nku.classapp.viewmodel.UserProfileViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    private val userProfileViewModel: UserProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       val token = getToken() ?: return

        if (token != null) {
            userProfileViewModel.fetchProfile(token)
            setupButtons(token)
        } else {
            navigateToLogin()
        }
        observeUserState()
    }

    private fun setupButtons(token: String) {
        binding.logoutButton.setOnClickListener {
            userProfileViewModel.logout(token) { success ->
                if (success) {
                    requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
                        .edit().remove("AUTH_TOKEN").apply()
                    navigateToLogin()
                } else {
                    Toast.makeText(context, "Logout failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.watchlistButton.setOnClickListener {
            Toast.makeText(context, "Coming soon!", Toast.LENGTH_SHORT).show()
        }

        binding.editUser.setOnClickListener {
            Toast.makeText(context, "Coming soon!", Toast.LENGTH_SHORT).show()
        }

        binding.userSettings.setOnClickListener {
            Toast.makeText(context, "Coming soon!", Toast.LENGTH_SHORT).show()
        }

        val toolbar = view?.findViewById<Toolbar>(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
    }

    private fun observeUserState() {
        lifecycleScope.launch {
            userProfileViewModel.userState.collect { state ->
                when (state) {
                    UserProfileViewModel.UserProfileState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is UserProfileViewModel.UserProfileState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val profile = state.profile
                        binding.usernamePlaceholder.text = profile.username
                        binding.namePlaceholder.text = profile.name
                        binding.emailPlaceholder.text = profile.email

                        if (!profile.avatar.isNullOrEmpty()) {
                            Glide.with(binding.root)
                                .load(profile.avatar)
                                .into(binding.userProfileImage)
                        } else {
                            binding.userProfileImage.setImageResource(R.drawable.user_profile_page)
                        }
                    }

                   UserProfileViewModel.UserProfileState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Failed to load profile", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_global_logout)
    }

    private fun getToken(): String? {
        val prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return prefs.getString("AUTH_TOKEN", null)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                val action = HomePageFragmentDirections
                    .actionHomePageFragmentToStockSearchFragment()
                findNavController().navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
