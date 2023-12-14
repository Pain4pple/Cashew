import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.cashew.R
import com.example.cashew.models.cart_model

class CartAdapter(private val context: Context, private val cartList: List<cart_model>) : BaseAdapter() {

    override fun getCount(): Int {
        return cartList.size
    }

    override fun getItem(position: Int): Any {
        return cartList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val viewHolder: ViewHolder

        if (view == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.list_item_cart, null)

            viewHolder = ViewHolder()
            viewHolder.itemNameTextView = view.findViewById(R.id.textViewItemName)
            viewHolder.itemPriceTextView = view.findViewById(R.id.textViewItemPrice)
            // Add more views if needed

            view.tag = viewHolder

        } else {
            viewHolder = view.tag as ViewHolder
        }

        // Set data to views
        val cartItem = cartList[position]
        viewHolder.itemNameTextView.text = cartItem.productName
        viewHolder.itemPriceTextView.text = "${cartItem.productPrice} USD"
        // Set more data as needed

        return view!!
    }

    private class ViewHolder {
        lateinit var itemNameTextView: TextView
        lateinit var itemPriceTextView: TextView
        // Add more views if needed
    }
}
