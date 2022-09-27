package com.yassine.smarthome

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.yassine.smarthome.databinding.ActivityCommandsBinding
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets

class CommandsActivity : AppCompatActivity() {
    lateinit var binding : ActivityCommandsBinding
    var command : String? = null
    lateinit var commandsAdapter: CommandsAdapter
    var commandsList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCommandsBinding.inflate(layoutInflater)
        initWindow()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initHeader()
        loadData()
    }

    private fun loadData() {
        commandsAdapter = CommandsAdapter(this , commandsList)
        binding.recycleView.adapter = commandsAdapter
        val pos : Int = intent.getIntExtra("pos" , -1)
        try {
            val jsonObject = JSONObject(loadJSONFromAsset())
            val jsonArray = jsonObject.getJSONArray("items")
            val jsonObjectCategories = jsonArray.getJSONObject(pos)
            val jsonArrayTitles = jsonObjectCategories.getJSONArray("content")
            for (i in 0 until jsonArrayTitles.length()) {
                val jsonObjectTitle = jsonArrayTitles.getJSONObject(i)
                for (y in 0 until jsonObjectTitle.getJSONArray("details").length()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        commandsList.add(
                            Html.fromHtml(
                                "" + jsonObjectTitle.getJSONArray("details")[y],
                                Html.FROM_HTML_MODE_LEGACY
                            ).toString()
                        )
                    } else {
                        commandsList.add(
                            Html.fromHtml("" + jsonObjectTitle.getJSONArray("details")[y])
                                .toString()
                        )
                    }
                }
            }
            commandsAdapter.notifyDataSetChanged()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun initHeader() {
        command = intent.getStringExtra("nameCategory")
        binding.header.apply {
            headerTextView.text = command
            back.visibility = View.VISIBLE
            back.setOnClickListener {
                finish()
            }
        }
    }

    private fun initWindow() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawableResource(R.drawable.statue_bar_back)
    }

    private fun loadJSONFromAsset(): String? {
        val json: String = try {
            val inputStream = assets.open("contents_commands.json")
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