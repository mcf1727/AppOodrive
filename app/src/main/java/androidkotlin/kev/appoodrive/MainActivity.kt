package androidkotlin.kev.appoodrive

import android.os.Bundle
import androidkotlin.kev.appoodrive.detailFolder.DetailFolderFragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity(), DetailFolderFragment.DetailFolderFragmentListener {

    private lateinit var detailFolderFragment: DetailFolderFragment
    lateinit var currentFragment: DetailFolderFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * start charging detailFolderFragment = root folder
         */
        detailFolderFragment = DetailFolderFragment.newInstance()

        detailFolderFragment.listener = this
        supportFragmentManager.beginTransaction()
            .add(R.id.container, detailFolderFragment)
            .commit()

    }

    override fun onDetailFragmentFolderSelected(idFolder: String) {

        /**
         * start charging content of a folder )
         */
        currentFragment = supportFragmentManager.findFragmentById(R.id.container) as DetailFolderFragment

        detailFolderFragment = DetailFolderFragment.newInstance()
        detailFolderFragment.listener = this
        detailFolderFragment.arguments = Bundle().apply {
            putString(DetailFolderFragment.EXTRA_ID_FOLDER, idFolder)
        }

        supportFragmentManager.beginTransaction()
            .hide(currentFragment)
            .add(R.id.container, detailFolderFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null)
            .commit()
    }

}


