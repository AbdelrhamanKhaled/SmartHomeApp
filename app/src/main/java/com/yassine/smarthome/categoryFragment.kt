package com.yassine.smarthome

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.yassine.smarthome.MainActivity.Companion.displayAdAfterNumberOfRows
import com.yassine.smarthome.MainActivity.Companion.numberOFItemsInRow
import com.yassine.smarthome.databinding.FragmentCategoryBinding
import org.json.JSONException
import org.json.JSONObject

class categoryFragment : Fragment() {

    lateinit var binding : FragmentCategoryBinding
    private var arrayListCategories = ArrayList<Any>()
    private var categoryCommandsAdapter: CategoryCommandsAdapter? = null
    val spanNum = (numberOFItemsInRow * displayAdAfterNumberOfRows) + 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHeader();
        loadData();
    }

    private fun initHeader() {
        binding.header.headerTextView.text = "Category"
    }

    private fun loadData() {
        arrayListCategories.clear()
        categoryCommandsAdapter = CategoryCommandsAdapter(arrayListCategories = arrayListCategories, context = requireActivity())
        val gridLayoutManager = GridLayoutManager(activity, numberOFItemsInRow)
        Log.i("ab_do" , "numberOFItemsInRow = $numberOFItemsInRow")
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                Log.i("ab_oo", "getSpanSize $position")
                return if ((position + 1) % spanNum == 0) {
                    // ad
                    Log.i("ab_oo", "ad  $position")
                    numberOFItemsInRow
                }

                else {
                    Log.i("ab_oo", "command  $position")
                    1
                }
            }
        }
        binding.recycleView.layoutManager = gridLayoutManager
        binding.recycleView.adapter = categoryCommandsAdapter
        loadCategoriesData()
    }

    private fun loadCategoriesData() {
        try {
            val str: String? = Utilities.loadJSONFromAsset(requireActivity())
            if (str != null) {
                val jsonObject = JSONObject(str)
                val jsonArray = jsonObject.getJSONArray("items")
                for (i in 0 until jsonArray.length()) {
                    val jsonObjectCategory = jsonArray.getJSONObject(i)
                    arrayListCategories.add(jsonObjectCategory.getString("title"))
                }
                var y = 0
                while (y < arrayListCategories.size) {
                    Log.i("ab_do" , "index = $y")
                    if ((y + 1) % spanNum == 0) {
                        // add Ad
                        Log.i("ab_do" , "add Add $y")
                        arrayListCategories.add(
                            y,
                            Ads.createNativeAd(requireActivity(), categoryCommandsAdapter, y)
                        )
                    }
                    y++
                }
                categoryCommandsAdapter?.notifyDataSetChanged()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}