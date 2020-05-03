package androidkotlin.kev.appoodrive

import android.os.Bundle
import android.util.Log
import android.view.View
import androidkotlin.kev.appoodrive.Internet.Item
import androidkotlin.kev.appoodrive.detailDetailFolder.DetailDetailFolderFragment
import androidkotlin.kev.appoodrive.detailDetailFolder.DetailDetailFolderModel
import androidkotlin.kev.appoodrive.detailFolder.DetailFolderFragment
import androidkotlin.kev.appoodrive.detailFolder.DetailFolderModel
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var adapter: ItemAdapter
    private lateinit var detailFolderFragment: DetailFolderFragment
    private lateinit var detailDetailFolderFragment: DetailDetailFolderFragment
    private val detailFolderModel = DetailFolderModel(this)
    private val detailDetailFolderModel = DetailDetailFolderModel(this)

    companion object{
        const val TAG = "MainActivity"
        const val API_URL = "http://192.168.1.77:8080"   //val API_URL = "http://localhost:8080", here localhost is my PC IP = 192.168.1.77
        const val USER = "noel"
        const val PASSWORD = "foobar"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * start charging detailFolderFragment
         */
        detailFolderFragment = DetailFolderFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.container, detailFolderFragment)
            .commit()

        /**
         * start getting data
         */
        //use HTTP Basic Authentication before all API request
        val okHttpClient = detailFolderModel.authentication(USER, PASSWORD)
        // GET request to server
        detailFolderModel.requestServer(API_URL, okHttpClient, R.id.detail_folder_fragment_recycler_view)
    }

    override fun onClick(view: View) {
        if (view.tag != null) {
            Log.i(TAG, "Click sur un item de la liste")    //to remove later

            /**
             * start charging detailDetailFolderFragment (content of a folder in the detailFolder)
             */
            detailDetailFolderFragment = DetailDetailFolderFragment()
            supportFragmentManager.beginTransaction()
                //.replace(R.id.container, detailDetailFolderFragment)
                .hide(detailFolderFragment)
                .add(R.id.container, detailDetailFolderFragment)
                //.show(detailDetailFolderFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()

            /**
             * start getting data
             */
            //use HTTP Basic Authentication before all API request
            val okHttpClient = detailDetailFolderModel.authentication(USER, PASSWORD)
            // GET request to server
            detailDetailFolderModel.requestServer(API_URL, okHttpClient, R.id.detail_detail_folder_fragment_recycler_view)
        }
    }

    /**
     * action for when request is successful from the server
     */
    fun onSuccessfulDataHandled(items: List<Item>, viewId: Int) {
        adapter = ItemAdapter(items, this@MainActivity)
        val mRecyclerView =findViewById<RecyclerView>(viewId)
        mRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        mRecyclerView.adapter = adapter

        Log.i(TAG, "(succeed) nb of items : ${items.size}")
    }

    /**
     * action for when request is failed from the server
     */
    fun onFailedDataHandled(throwable: Throwable){
        Log.i(TAG, "(failed reason) : ${throwable.message}")
    }



    /**
     * useless  --- old method
      */
//    fun requestService(mFragmentView: RecyclerView) {
//        //start get data
//        items = mutableListOf<Item>()
//        //Create retrofit client
//        //val detailFolderModel = DetailFolderModel()
//        val okHttpClient = detailFolderModel.authentication(USER, PASSWORD)
//
//        val retrofitJson = Retrofit.Builder()
//            .client(okHttpClient)
//            .baseUrl(API_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val serviceJson: HttpBinServiceJson =retrofitJson.create(HttpBinServiceJson::class.java)
//        val callJson = serviceJson.getFolderContent()
//
//        callJson.enqueue(object: Callback<List<GetDataItems>> {
//            override fun onResponse(call: Call<List<GetDataItems>>?, response: Response<List<GetDataItems>>) {
//                val getDataItems = response.body()
//                for (i in 0 until getDataItems?.size!!) {
//                    items.add(
//                        Item(
//                            getDataItems[i].name
//                        )
//                    )
//                }
//                adapter = ItemAdapter(items, this@MainActivity)
//                mFragmentView.layoutManager = LinearLayoutManager(this@MainActivity)
//                mFragmentView.adapter = adapter
//
//                Log.i(TAG, "(succeed) nb of items : ${getDataItems.size}")
//            }
//            override fun onFailure(call: Call<List<GetDataItems>>, t: Throwable) {
//                Log.i(TAG, "(failed reason) : ${t.message}")
//            }
//        })
//        //finish get data
//
//
//        //        // charge recycleView
////        items = mutableListOf<Item>()
////        items.add(Item("dossier1"))
////        items.add(Item("dossier2"))
////        items.add(Item("dossier3"))
////        items.add(Item("dossier4"))
////
////        adapter = ItemAdapter(items, this)
////        items_recycler_view.layoutManager = LinearLayoutManager(this)
////        items_recycler_view.adapter = adapter
////        //finish charging recycleView
//    }
}
