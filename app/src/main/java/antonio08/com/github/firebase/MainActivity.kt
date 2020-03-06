/**
 * Created by Antonio Lozano on 2020-03-04.
 */

package antonio08.com.github.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    private lateinit var mGoogleSignInClient : GoogleSignInClient
    private lateinit var mGoogleSignInOptions : GoogleSignInOptions

    companion object {
        private const val RC_SIGN_IN_GOOGLE = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get Firebase Instance
        mAuth = FirebaseAuth.getInstance()

        // Set the basic permissions to sign in the user
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Request the information specified on the google sign in options
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)

        // Set width for sign in button
        mSignInButton.setSize(SignInButton.SIZE_STANDARD)

        mSignInButton.setOnClickListener{
            signInUser()
        }
    }

    override fun onStart() {
        super.onStart()

        // Check whether the user is already logged in
        val currentUser = GoogleSignIn.getLastSignedInAccount(this)

        // If the current user is different from null it means is already logged in and can proceed
        // to the Dashboard
        if (currentUser != null)
        {
            takeUserToDashboard()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode)
        {
            RC_SIGN_IN_GOOGLE -> handleSigInInResult(GoogleSignIn.getSignedInAccountFromIntent(data))
        }
    }


    private fun signInUser()
    {
        val intent = mGoogleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN_GOOGLE)
    }

    private fun handleSigInInResult(task : Task<GoogleSignInAccount>)
    {
        try
        {
            val googleSignInAccount = task.getResult(ApiException::class.java)
            takeUserToDashboard()
        }
        catch (exception : ApiException)
        {
            val message = exception.message
        }
    }


    private fun takeUserToDashboard()
    {
        startActivity(Intent(this, DashboardActivity::class.java))
    }
}
