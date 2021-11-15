package com.km.backflow.calculator.ui.sharedialog

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.km.backflow.calculator.api.GithubApiBuilder
import com.km.backflow.calculator.databinding.FragmentShareDialogBinding
import com.km.backflow.calculator.models.GithubAsset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShareDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentShareDialogBinding
    private var showUrl = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = FragmentShareDialogBinding.inflate(LayoutInflater.from(context))

        binding.tvUrl.visibility = if (showUrl) View.VISIBLE else View.GONE
        binding.btnShowUrl.setOnClickListener {
            toggleUrlVisibility()
        }

        binding.btnOk.setOnClickListener { dismiss() }

        return MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pbLoading.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {

            val apkAsset = getLatestReleaseUrl()
            val qrCode = generateQrCode(apkAsset?.browserDownloadUrl)

            CoroutineScope(Dispatchers.Main).launch {
                binding.tvUrl.text = apkAsset?.browserDownloadUrl
                binding.ivQr.setImageBitmap(qrCode)
                binding.pbLoading.visibility = View.GONE
            }

        }
    }

    private suspend fun getLatestReleaseUrl(): GithubAsset? {
        return GithubApiBuilder.createApi().getLatestGithubRelease().getApkAsset()
    }

    private fun toggleUrlVisibility() {
        showUrl = !showUrl
        binding.tvUrl.visibility = if (showUrl) View.VISIBLE else View.GONE
    }

    private fun generateQrCode(content: String?): Bitmap {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }

        return bitmap
    }

}