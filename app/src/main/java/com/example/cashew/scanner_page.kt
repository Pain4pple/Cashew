import android.content.Intent
import android.os.Build.VERSION_CODES.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cashew.R
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class scanner_page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_scanner_page)

        startQRCodeScanner()
    }

    private fun startQRCodeScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setOrientationLocked(false)
        integrator.setBeepEnabled(false)
        //integrator.setCaptureActivity(CustomCaptureActivity::class.java)  // Use your own CaptureActivity if needed
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result.contents != null) {
            val scannedOrderId = result.contents
            // Retrieve order details from Firebase using the scannedOrderId
            // Display the full order details in a new activity or fragment
        }
    }
}
