package androidkotlin.kev.appoodrive.detailFolder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidkotlin.kev.appoodrive.R
import androidx.fragment.app.Fragment

class DetailFolderFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_folder, container, false)
    }
}
