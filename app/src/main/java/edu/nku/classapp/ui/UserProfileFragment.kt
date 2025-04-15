//package edu.nku.classapp.ui.profile
//
//import android.content.Context
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import edu.nku.classapp.viewmodel.UserProfileViewModel
//import androidx.lifecycle.Observer
//import edu.nku.classapp.R
//
//
//class UserProfileFragment : Fragment() {
//
//    private val viewModel: UserProfileViewModel by viewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_user_profile, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
//        val token = sharedPreferences.getString("AUTH_TOKEN", null)
//
//        if (token != null) {
//            viewModel.fetchProfile(token)
//        }
//
//        val avatarImageView = view.findViewById<ImageView>(R.id.avatarImageView)
//        val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
//        val emailTextView = view.findViewById<TextView>(R.id.emailTextView)
//
//        viewModel.profile.observe(viewLifecycleOwner, Observer { profile ->
//            nameTextView.text = profile.name
//            emailTextView.text = profile.email
//            if (!profile.avatar.isNullOrEmpty()) {
//                Picasso.get().load(profile.avatar).into(avatarImageView)
//            }
//        })
//
//        viewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
//            emailTextView.text = errorMessage
//        })
//    }
//}
