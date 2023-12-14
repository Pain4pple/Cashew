import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.cashew.R
import com.example.cashew.models.order_model
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class generate_page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_page)

        val order = order_model(orderID = "123456", orderDate = null)
        generateQRCode(order)
    }

    private fun generateQRCode(order: order_model) {
        val barcodeEncoder = BarcodeEncoder()
        val bitmap: Bitmap = barcodeEncoder.encodeBitmap(order.orderID, BarcodeFormat.QR_CODE, 400, 400)
        val imageView: ImageView = findViewById(R.id.qrCodeImageView)
        imageView.setImageBitmap(bitmap)
    }
}
