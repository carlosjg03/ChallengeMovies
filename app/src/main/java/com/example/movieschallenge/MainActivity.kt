package com.example.movieschallenge

import android.os.Build
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.movieschallenge.databinding.ActivityMainBinding
import com.example.movieschallenge.worker.LocationAccess
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.picturesFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        val listener = object : MultiplePermissionsListener {
            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                if (p0?.areAllPermissionsGranted() == true) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        val permissionList = mutableListOf<String>()
                        val listener = object : MultiplePermissionsListener {
                            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                                if (p0?.areAllPermissionsGranted() == true) {
                                    LocationAccess.init(applicationContext)
                                }
                            }
                            override fun onPermissionRationaleShouldBeShown(
                                p0: List<PermissionRequest>,
                                p1: PermissionToken?
                            ) {
                                p1?.continuePermissionRequest()
                            }
                        }
                        permissionList.add(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                        Dexter.withContext(applicationContext)
                            .withPermissions(permissionList)
                            .withListener(listener).check()
                    } else {
                        LocationAccess.init(applicationContext)
                    }
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: List<PermissionRequest>,
                p1: PermissionToken?
            ) {
                p1?.continuePermissionRequest()
            }
        }
        val permissionList = mutableListOf<String>()
        permissionList.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION)

        Dexter.withContext(this.applicationContext)
            .withPermissions(permissionList)
            .withListener(listener).check()
    }
}