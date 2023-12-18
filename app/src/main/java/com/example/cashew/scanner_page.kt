package com.example.cashew

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator


class scanner_page : AppCompatActivity() {
    lateinit var scan_btn: Button
    var textView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner_page)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        scan_btn = findViewById(R.id.scanner)
        textView = findViewById(R.id.text)

        scan_btn.setOnClickListener(View.OnClickListener {
            val intentIntegrator = IntentIntegrator(this@scanner_page)
            intentIntegrator.setOrientationLocked(false)
            intentIntegrator.setPrompt("Scan a QR Code")
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            intentIntegrator.initiateScan()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (intentResult != null) {
            val contents = intentResult.contents
            if (contents != null) {
                textView!!.text = intentResult.contents
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}