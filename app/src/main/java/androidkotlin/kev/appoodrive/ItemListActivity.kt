package androidkotlin.kev.appoodrive

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_item_list.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItemListActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var items: MutableList<Item>
    lateinit var adapter: ItemAdapter

    companion object{
        //val API_URL = "http://localhost:8080"
        val TAG = "ItemListActivity"
        val API_URL = "http://192.168.1.77:8080"
        val USER = "noel"
        val PASSWORD = "foobar"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        items = mutableListOf<Item>()

        //start get data
        //Create retrofit client
        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(AuthenticationInterceptor(USER, PASSWORD))
            .build()

        val retrofitJson = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val serviceJson: HttpBinServiceJson =retrofitJson.create(HttpBinServiceJson::class.java)
        val callJson = serviceJson.getFolderContent()

        callJson.enqueue(object: Callback<List<GetDataItems>> {
            override fun onResponse(call: Call<List<GetDataItems>>?, response: Response<List<GetDataItems>>) {
                val getDataItems = response.body()
                for (i in 0 until getDataItems?.size!!) {
                    items.add(Item(getDataItems[i].name))
                }
                adapter = ItemAdapter(items, this@ItemListActivity)
                items_recycler_view.layoutManager = LinearLayoutManager(this@ItemListActivity)
                items_recycler_view.adapter = adapter

                Log.i(TAG, "(succeed) nb of items : ${getDataItems.size}")
            }
            override fun onFailure(call: Call<List<GetDataItems>>, t: Throwable) {
                Log.i(TAG, "(failed reason) : ${t.message}")
            }
        })


        //finish get data

//        // charge recycleView
//        items = mutableListOf<Item>()
//        items.add(Item("dossier1"))
//        items.add(Item("dossier2"))
//        items.add(Item("dossier3"))
//        items.add(Item("dossier4"))
//
//        adapter = ItemAdapter(items, this)
//        items_recycler_view.layoutManager = LinearLayoutManager(this)
//        items_recycler_view.adapter = adapter
//        //finish charging recycleView
    }

    override fun onClick(view: View) {
        if (view.tag != null) {
            Log.i("ItemListActivity", "Click sur un item de la liste")
        }
    }
}
