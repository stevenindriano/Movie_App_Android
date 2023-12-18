package id.ac.umn.stevenindriano.uts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

val TABS_FIXED = listOf(
    R.string.class_enrollment,
    R.string.profile
)

class UnionAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, var name: String) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return TABS_FIXED.size
    }

    override fun createFragment(position: Int): Fragment {

        when (position) {
            0 -> return ListMovieFragment()
        }

        return fragmentProfileWithName()
    }

    private fun fragmentProfileWithName(): ProfileFragment {
        val fragmentProfile = ProfileFragment()

        Log.d("NAMA DI UNION ADAPTER: ", name)
        val bundle = Bundle()
        bundle.putString("NAME", name)

        fragmentProfile.arguments = bundle

        return fragmentProfile
    }

}