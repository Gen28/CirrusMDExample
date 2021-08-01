package com.gen28.cirrusmdexample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.cirrusmd.androidsdk.CirrusEvents
import com.cirrusmd.androidsdk.CirrusListener
import com.cirrusmd.androidsdk.CirrusMD.credentialIdListener
import com.cirrusmd.androidsdk.CirrusMD.enableDebugLogging
import com.cirrusmd.androidsdk.CirrusMD.listener
import com.cirrusmd.androidsdk.CirrusMD.start
import com.cirrusmd.androidsdk.CredentialIdListener
import com.gen28.cirrusmdexample.ui.main.ChannelFragment
import com.gen28.cirrusmdexample.ui.main.MainViewModel
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class MainActivity : AppCompatActivity(), CirrusListener {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setContentView(R.layout.main_activity)
        setupCirrus()
        // Call fetch token in viewModel
        viewModel.fetchToken()
    }

    private fun setupCirrus() {
//        enableDebugLogging = true

        // Obtain Credential Id
        credentialIdListener = object : CredentialIdListener {
            override fun onCredentialIdReady(id: String) {
                Timber.d("Credential ID: $id")
            }
        }
        start(this, SECRET)
        listener = this
    }

    override fun onEvent(event: CirrusEvents) {
        when (event) {
            CirrusEvents.SUCCESS -> {
                displayChannelFragment()
            }
            CirrusEvents.LOGGED_OUT -> onEventError("CirrusMD SDK user was logged out.")
            CirrusEvents.INVALID_JWT -> onEventError("CirrusMD SDK invalid JWT supplied")
            CirrusEvents.INVALID_SECRET -> onEventError("CirrusMD SDK invalid secret supplied")
            CirrusEvents.MISSING_JWT -> onEventError("CirrusMD SDK missing jwt")
            CirrusEvents.MISSING_SECRET -> onEventError("CirrusMD SDK missing secret")
            CirrusEvents.CONNECTION_ERROR -> onEventError("CirrusMD SDK connection error")
            CirrusEvents.AUTHENTICATION_ERROR -> onEventError("CirrusMD SDK auth error")
            CirrusEvents.USER_INTERACTION -> Timber.d("CirrusMD SDK user interaction")
            CirrusEvents.UNKNOWN_ERROR -> onEventError("CirrusMD SDK generic error") //This error would include cases like network errors
        }
    }

    private fun displayChannelFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ChannelFragment.newInstance())
            .commitNow()
    }

    private fun onEventError(s: String) {
        Timber.e(s)
        //Display Snackbar of error for debugging
        Snackbar.make(findViewById(R.id.container), s, Snackbar.LENGTH_SHORT)
    }

    override fun viewForError(event: CirrusEvents): View? {
        return null
    }
}