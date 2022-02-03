package com.thortigen.orgabuy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thortigen.orgabuy.viewmodels.CartViewModel
import com.thortigen.orgabuy.viewmodels.ShopListViewModel

class MainActivity : AppCompatActivity() {

    private val shopListViewModel: ShopListViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navigationController = findNavController(R.id.nav_host_fragment_container)
        bottomNavigationView.setupWithNavController(navigationController)

        setSupportActionBar(findViewById(R.id.app_toolbar))

        setupActionBarWithNavController(navigationController)

        val shopListBadge  = bottomNavigationView.getOrCreateBadge(R.id.shopListFragment).apply {
            backgroundColor = resources.getColor(R.color.colorAccent, resources.newTheme())
            badgeTextColor = resources.getColor(R.color.textMainColorInverse, resources.newTheme())
            verticalOffset = 12
            maxCharacterCount = 3

        }

        val cartBadge  = bottomNavigationView.getOrCreateBadge(R.id.cartFragment).apply {
            backgroundColor = resources.getColor(R.color.colorAccent, resources.newTheme())
            badgeTextColor = resources.getColor(R.color.textMainColorInverse, resources.newTheme())
            verticalOffset = 12
            maxCharacterCount = 3

        }

        shopListViewModel.getAllItems.observe(
            this, { shopListBadge.number = it.size
                shopListBadge.isVisible = shopListBadge.number > 0 }
        )

        cartViewModel.getAllItems.observe(
            this, { cartBadge.number = it.size
                cartBadge.isVisible = cartBadge.number > 0 }
        )

    }

    override fun onSupportNavigateUp(): Boolean {
        val navigationController = findNavController(R.id.nav_host_fragment_container)
        return navigationController.navigateUp() || super.onSupportNavigateUp()
    }

}