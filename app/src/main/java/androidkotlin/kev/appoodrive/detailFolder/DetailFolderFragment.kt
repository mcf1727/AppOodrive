package androidkotlin.kev.appoodrive.detailFolder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidkotlin.kev.appoodrive.Item
import androidkotlin.kev.appoodrive.ItemAdapter
import androidkotlin.kev.appoodrive.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class DetailFolderFragment : Fragment(), IView, View.OnClickListener {

    companion object{
        fun newInstance(): DetailFolderFragment {
            val fragment = DetailFolderFragment()
            val presenter = Presenter()
            val model = RequestServerModel()
            fragment.setPresenter(presenter)
            model.setPresenter(presenter)
            presenter.setModel(model)
            presenter.setView(fragment)
            return fragment
        }

        //TODO:  replace "localhost" in val API_URL = "http://localhost:8080" by the IP address of your established server
        const val API_URL = "http://localhost:8080"
        const val USER = "noel"
        const val PASSWORD = "foobar"
        const val EXTRA_ID_FOLDER = "androidkotlin.kev.appoodrive.EXTRA_ID_FOLDER"
    }

    interface DetailFolderFragmentListener {
        fun onDetailFragmentFolderSelected(idFolder: String)
    }

    var mpresenter: IPresenter? = null
    var listener: DetailFolderFragmentListener? = null
    private lateinit var idsInFolder: List<String>
    private lateinit var adapter: ItemAdapter
    private lateinit var recyclerView: RecyclerView
    private var items = mutableListOf<Item>()


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail_folder, container, false)
        recyclerView = view.findViewById(R.id.detail_folder_fragment_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ItemAdapter(items, this)
        recyclerView.adapter = adapter

        /**
         * start getting data
         */
        if (savedInstanceState != null) {
            items = savedInstanceState.getStringArrayList("items") as MutableList<Item>
            adapter.notifyDataSetChanged()
        } else {
            //use HTTP Basic Authentication before all API request
            val okHttpClient = mpresenter?.authenticationRequestSent(USER, PASSWORD)
            // GET request to server
            if (arguments?.getString(EXTRA_ID_FOLDER).isNullOrEmpty()) {
                mpresenter?.requestUserSent(API_URL, okHttpClient!!)
            } else {
                mpresenter?.requestFolderSent(API_URL, okHttpClient!!, arguments?.getString(EXTRA_ID_FOLDER)!!)
            }
        }
    }

    /**
     * When clicking on q folder
     */
    override fun onClick(view: View) {
        if (view.tag != null) {
            //start getting data
            listener?.onDetailFragmentFolderSelected(idsInFolder[view.tag as Int])
        }
    }

    override fun setPresenter(presenter: IPresenter) {
        this.mpresenter = presenter
    }

    /**
     * action for when request is successful from the server
     */
    override fun showSuccessfulData(items: List<Item>, idFolders: List<String>) {
        getAllItems(items)
        adapter.notifyDataSetChanged()

        idsInFolder = idFolders
    }

    /**
     * action for when request is failed from the server
     */
    override fun showFailedData(throwable: Throwable){
        Toast.makeText(activity, "${throwable.message}", Toast.LENGTH_LONG).show()
    }

    private fun getAllItems(items: List<Item>) {
        for (item in items) {
            this.items.add(item)
        }
    }

    /**
     * Keep the data when the screen is rotated
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (items != emptyList<Item>()) {
            outState.putStringArrayList("items", items as ArrayList<String>)
        }
    }

}
