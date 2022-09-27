package com.yassine.smarthome

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yassine.smarthome.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHeader()
        binding.linearReview.setOnClickListener { rateUs() }
        binding.linearSendEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val data =
                Uri.parse("mailto:customer@something.com?subject=Feedback")
            intent.data = data
            startActivity(intent)
        }
        binding.linearShareApp.setOnClickListener {
            shareText(
                requireActivity(),
                "",
                "download",
                resources.getString(R.string.app_name)
            )
        }
        Ads.loadBanner(requireContext(), binding.adContainer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Ads.destroyBanner()
    }

    private fun initHeader() {
        binding.header.headerTextView.text = "Settings"
    }

    fun shareText(activity: Activity, message: String, subject: String?, chooserTitle: String?) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        shareIntent.putExtra(
            Intent.EXTRA_TEXT, message + "" + String.format(
                activity.resources.getString(R.string.share_email_body),
                activity.resources.getString(R.string.app_name)
            ) + " " + activity.resources.getString(R.string.playStore_link) + activity.packageName
        )
        try {
            activity.startActivity(Intent.createChooser(shareIntent, chooserTitle))
        } catch (e: Throwable) {
            Toast.makeText(activity, R.string.err_no_app_found, Toast.LENGTH_LONG).show()
        }
    }

    private fun rateUs() {
        val playUrl = resources.getString(R.string.playStore_link) + requireActivity().packageName
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(playUrl)
        startActivity(intent)
    }
}