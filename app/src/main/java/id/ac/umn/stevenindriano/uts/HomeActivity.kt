package id.ac.umn.stevenindriano.uts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import id.ac.umn.stevenindriano.uts.retrofit.ApiServices
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.ac.umn.stevenindriano.uts.R
import retrofit2.Call
import retrofit2.Response
import id.ac.umn.stevenindriano.uts.MovieAdapter
import id.ac.umn.stevenindriano.uts.MovieModel
import id.ac.umn.stevenindriano.uts.TABS_FIXED
import id.ac.umn.stevenindriano.uts.UnionAdapter


class HomeActivity : AppCompatActivity() {

    val TAG: String = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupActionBar()
        setupTabLayout()

    }

    private fun setupTabLayout() {
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)

        val name = intent.getStringExtra("NAME")
        val adapter = UnionAdapter(supportFragmentManager, lifecycle, name.toString())
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = resources.getString(TABS_FIXED[position])
        }.attach()
    }

    private fun setupActionBar() {
        val actionBar: ActionBar? = supportActionBar

        actionBar?.title = ""
        actionBar?.setIcon(R.drawable.union_netflix_small)

        actionBar?.setDisplayUseLogoEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home, menu)

        val name = intent.getStringExtra("NAME")

        val nameMenuItem: MenuItem? = menu?.findItem(R.id.username)
        nameMenuItem?.title = "Hi, $name"

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.logout -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
//        setAdapter
        getDataFromApi()
    }

    private fun getDataFromApi() {
        ApiServices.endpoint.getMovie()
            .enqueue(object : retrofit2.Callback<List<MovieModel>> {
                override fun onResponse(
                    call: Call<List<MovieModel>>,
                    response: Response<List<MovieModel>>
                ) {
                    if (response.isSuccessful) {
//                        printLog(response.body().toString())
//                        showMovies(response.body()!!)

                        val movieAdapter = MovieAdapter(response.body()!!, object : MovieAdapter.OnAdapterListener{
                            override fun onClick(movie: MovieModel) {
                                val bundle = Bundle()
                                bundle.putParcelable("DATA_MOVIE", movie)
                                val detailMovieFragment = DetailMovieFragment()
                                detailMovieFragment.arguments = bundle
                                val fragment = supportFragmentManager.findFragmentByTag(DetailMovieFragment::class.java.simpleName)

                                if (fragment !is DetailMovieFragment) {
                                    supportFragmentManager.beginTransaction()
                                        .replace(R.id.container_home, detailMovieFragment, DetailMovieFragment::class.java.simpleName)
                                        .addToBackStack(null)
                                        .commit()
                                } else {
                                    supportFragmentManager.beginTransaction()
                                        .remove(fragment)
                                        .add(R.id.container_home, detailMovieFragment, DetailMovieFragment::class.java.simpleName)
                                        .commit()
                                }
                            }
                        })
                        findViewById<RecyclerView>(R.id.recycler_view).adapter = movieAdapter
                    }
                }

                override fun onFailure(call: Call<List<MovieModel>>, t: Throwable) {
                    printLog(t.toString())
                }
            })
    }

    private fun showMovies(movies: List<MovieModel>) {
        for (movie in movies) {
            printLog("Title: ${movie.title}")
//            printLog(movie.description)
//            printLog(movie.thumbnail)
        }
    }

    private fun printLog(Movies: String) {
        Log.d(TAG, Movies)
    }
}