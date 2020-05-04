package androidkotlin.kev.appoodrive

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidkotlin.kev.appoodrive.detailDetailFolder.DetailDetailFolderFragment
import androidkotlin.kev.appoodrive.detailFolder.DetailFolderFragment
import androidkotlin.kev.appoodrive.network.RequestServerModel
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var adapter: ItemAdapter
    private lateinit var detailFolderFragment: DetailFolderFragment
    private lateinit var detailDetailFolderFragment: DetailDetailFolderFragment
    private val requestServerModel = RequestServerModel(this)
    lateinit var idsInFolder: List<String>

    companion object{
        //TODO:  replace "localhost" in val API_URL = "http://localhost:8080" by the IP address of your established server
        const val API_URL = "http://localhost:8080"
        const val USER = "noel"
        const val PASSWORD = "foobar"
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * start charging detailFolderFragment = root folder
         */
        detailFolderFragment = DetailFolderFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.container, detailFolderFragment)
            .commit()

        /**
         * start getting data
         */
        //use HTTP Basic Authentication before all API request
        val okHttpClient = requestServerModel.authentication(USER, PASSWORD)
        // GET request to server
        requestServerModel.requestUser(API_URL, okHttpClient, R.id.detail_folder_fragment_recycler_view)
    }

    override fun onClick(view: View) {
        if (view.tag != null) {
            /**
             * start charging detailDetailFolderFragment (content of a folder in the detailFolder)
             */
            detailDetailFolderFragment = DetailDetailFolderFragment()
            supportFragmentManager.beginTransaction()
                .hide(detailFolderFragment)
                .add(R.id.container, detailDetailFolderFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()

            /**
             * start getting data
             */
            //use HTTP Basic Authentication before all API request
            val okHttpClient = requestServerModel.authentication(USER, PASSWORD)
            // GET request to server
            requestServerModel.requestFolder(API_URL, okHttpClient, R.id.detail_detail_folder_fragment_recycler_view, idsInFolder[view.tag as Int])
        }
    }

    /**
     * action for when request is successful from the server
     */
    fun onSuccessfulDataHandled(items: List<Item>, viewId: Int, idFolders: List<String>) {
        adapter = ItemAdapter(items, this@MainActivity)
        val mRecyclerView =findViewById<RecyclerView>(viewId)
        mRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        mRecyclerView.adapter = adapter

        idsInFolder = idFolders
    }

    /**
     * action for when request is failed from the server
     */
    fun onFailedDataHandled(throwable: Throwable){
        Toast.makeText(this, "${throwable.message}", Toast.LENGTH_LONG).show()
    }
}
