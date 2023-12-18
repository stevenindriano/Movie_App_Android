package id.ac.umn.stevenindriano.uts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.android.material.textfield.TextInputEditText
import id.ac.umn.stevenindriano.uts.R
import id.ac.umn.stevenindriano.uts.HomeActivity

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        findViewById<Button>(R.id.login).setOnClickListener {

            val name = findViewById<TextInputEditText>(R.id.name_edit_text).text.toString()

            if(name.isEmpty() || name.isBlank()) {
                Toast.makeText(this@LandingActivity, "Name cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(
                    Intent(this, HomeActivity::class.java).putExtra("NAME", name)
                )
            }

        }
    }
}