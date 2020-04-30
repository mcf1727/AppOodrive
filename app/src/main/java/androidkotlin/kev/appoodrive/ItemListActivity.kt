package androidkotlin.kev.appoodrive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_item_list.*

class ItemListActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var items: MutableList<Item>
    lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        items = mutableListOf<Item>()
        items.add(Item("dossier1"))
        items.add(Item("dossier2"))
        items.add(Item("dossier3"))
        items.add(Item("dossier4"))
        items.add(Item("dossier5"))
        items.add(Item("dossier6"))
        items.add(Item("dossier7"))
        items.add(Item("dossier8"))
        items.add(Item("dossier9"))
        items.add(Item("dossier10"))
        items.add(Item("dossier11"))
        items.add(Item("dossier12"))
        items.add(Item("dossier13"))
        items.add(Item("dossier14"))
        items.add(Item("dossier15"))
        items.add(Item("dossier16"))

        adapter = ItemAdapter(items, this)
        items_recycler_view.layoutManager = LinearLayoutManager(this)
        items_recycler_view.adapter = adapter
    }

    override fun onClick(view: View) {
        if (view.tag != null) {
            Log.i("ItemListActivity", "Click sur un item de la liste")
        }
    }
}
