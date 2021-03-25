package dev.joaogabriel.bytebank.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.Navigation
import dev.joaogabriel.bytebank.R

class Splash : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            openSignIn()
        }, 2000)
    }

    private fun openSignIn() {
        Navigation.findNavController(requireView()).navigate(R.id.splashToSignIn)
    }
}