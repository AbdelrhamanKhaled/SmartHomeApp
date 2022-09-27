package com.yassine.smarthome

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.yassine.smarthome.databinding.FragmentHowToBinding
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets

class HowToFragment : Fragment() {
    lateinit var binding : FragmentHowToBinding
    private val arrayListArticles = ArrayList<Any>()
    private var articlesAdapter: ArticlesAdapter? = null
    val spanNum = (MainActivity.numberOFItemsInRow * MainActivity.displayAdAfterNumberOfRows) + 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHowToBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHeader();
        initRecycleView();
    }

    private fun initRecycleView() {
        articlesAdapter = ArticlesAdapter(arrayListArticles, requireActivity())
        val gridLayoutManager = GridLayoutManager(activity, MainActivity.numberOFItemsInRow)
        Log.i("ab_do" , "numberOFItemsInRow = ${MainActivity.numberOFItemsInRow}")
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                Log.i("ab_oo", "getSpanSize $position")
                return if ((position + 1) % spanNum == 0) {
                    // ad
                    Log.i("ab_oo", "ad  $position")
                    MainActivity.numberOFItemsInRow
                }

                else {
                    Log.i("ab_oo", "command  $position")
                    1
                }
            }
        }
        binding.recycleView.layoutManager = gridLayoutManager
        binding.recycleView.adapter = articlesAdapter
        loadData()
    }

    private fun loadData() {
        arrayListArticles.clear()
        try {
            val jsonObject = JSONObject(loadJSONFromAsset())
            val jsonArray = jsonObject.getJSONArray("items")
            for (i in 0 until jsonArray.length()) {
                val jsonObjectCategory = jsonArray.getJSONObject(i)
                arrayListArticles.add(jsonObjectCategory.getString("title"))
            }
            var y = 0
            while (y < arrayListArticles.size) {
                Log.i("ab_do" , "index = $y")
                if ((y + 1) % spanNum == 0) {
                    // add Ad
                    Log.i("ab_do" , "add Add $y")
                    arrayListArticles.add(
                        y,
                        Ads.createNativeAd(requireActivity(), articlesAdapter, y)
                    )
                }
                y++
            }
            articlesAdapter?.notifyDataSetChanged()
        }
        catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun initHeader() {
        binding.header.headerTextView.text = "How To?"
    }

    private fun loadJSONFromAsset(): String? {
        val json: String = try {
            val inputStream = requireActivity().assets.open("contents.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}