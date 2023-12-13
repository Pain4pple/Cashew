package com.example.cashew

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.DialogFragment

class logOut_dialog : DialogFragment() {

    private lateinit var viewLayout: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewLayout = inflater.inflate(R.layout.activity_log_out_dialog, container, false)

        var confirmLogOutBtn = viewLayout.findViewById<Button>(R.id.confirmLogoutBtn)
        var cancelBtn = viewLayout.findViewById<Button>(R.id.cancelBtn)

        confirmLogOutBtn.setOnClickListener {
            val intent = Intent(context, login_page::class.java)
            startActivity(intent)
            dismiss() // Close the dialog after starting the new activity
        }

        cancelBtn.setOnClickListener {
            dismiss() // Close the dialog when the cancel button is clicked
        }

        // Return the inflated layout
        return viewLayout
    }
}