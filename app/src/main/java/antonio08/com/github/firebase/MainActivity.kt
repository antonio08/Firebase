/**
 * Created by Antonio Lozano on 2020-03-04.
 */

package antonio08.com.github.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get Firebase Instance
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()

        // Check whether the user is already logged in
        val currentUser = mAuth.currentUser

        // If the current user is different from null it means is already logged in and can proceed
        // to the Dashboard
        if (currentUser != null)
        {
            takeUserToDashboard()
        }
    }

    private fun takeUserToDashboard()
    {
        startActivity(Intent(this, DashboardActivity::class.java))
    }
}
