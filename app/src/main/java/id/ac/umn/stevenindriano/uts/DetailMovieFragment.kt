package id.ac.umn.stevenindriano.uts

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import id.ac.umn.stevenindriano.uts.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailMovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailMovieFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_movie, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            val fragment = parentFragmentManager.findFragmentByTag(DetailMovieFragment::class.java.simpleName)
            Log.d("DetailMovieFragment", "BACK")

            if (fragment != null) {
                parentFragmentManager.beginTransaction()
                    .hide(fragment)
                    .commit()
            }
        }

        val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("DATA_MOVIE", MovieModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable("DATA_MOVIE")
        }

        val thumbnail = view.findViewById<ImageView>(R.id.detail_movie_thumbnail)
        Glide.with(thumbnail)
            .load(movie?.thumbnail)
            .into(thumbnail)

        val title = movie?.title.toString()
        val year = getString(R.string.year_movie, movie?.year.toString())
        val genre = getString(R.string.genre_movie, movie?.genre.toString())
        val rating = getString(R.string.rating_movie, movie?.rating.toString())

        view.findViewById<TextView>(R.id.detail_movie_title).text = title
        view.findViewById<TextView>(R.id.movie_year).text = year
        view.findViewById<TextView>(R.id.movie_genre).text = genre
        view.findViewById<TextView>(R.id.movie_rating).text = rating

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailMovieFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailMovieFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}