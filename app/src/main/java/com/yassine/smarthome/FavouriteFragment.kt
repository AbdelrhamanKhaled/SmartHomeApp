package com.yassine.smarthome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yassine.smarthome.databinding.FragmentFavouriteBinding
import com.yassine.smarthome.databinding.FragmentHowToBinding

class FavouriteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var binding : FragmentFavouriteBinding
    lateinit var adapter : FavouriteAdapter
    private var favouriteCommands = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHeader();
        loadData();
    }

    private fun loadData() {
        favouriteCommands.clear()
        adapter = FavouriteAdapter(requireActivity() , favouriteCommands)
        binding.recycleView.adapter = adapter
        val favoriteDatabase = FavoriteDatabase(requireActivity())
        favouriteCommands.addAll(favoriteDatabase.allFavorites)
        adapter.notifyDataSetChanged()
    }

    private fun initHeader() {
        binding.header.headerTextView.text = "Favourite"
    }
}