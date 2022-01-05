package com.github.rahul_gill.sellbuy;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.github.rahul_gill.sellbuy.api.VolleySingleton;
import com.github.rahul_gill.sellbuy.viemodel.AppViewModel;
import com.github.rahul_gill.sellbuy.viemodel.AppViewModelFactory;

public class MainActivity extends AppCompatActivity {
    private AppViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host);
        if(navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupActionBarWithNavController(this, navController);
        }
        viewModel = new ViewModelProvider(
                this, new AppViewModelFactory(
                this.getApplication(),
                VolleySingleton.getInstance(this.getApplication())
        )
        ).get(AppViewModel.class);

        viewModel.appBarTitle.observe(this, title -> {
            ActionBar actionBar = getSupportActionBar();
            if(actionBar != null)
                actionBar.setTitle(title);
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host);
        if(navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            return  navController.navigateUp();
        }
        return super.onNavigateUp();
    }

    @Override
    public void onBackPressed() {
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host);
        boolean unsuccessfulNavigation = true;
        if(navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            unsuccessfulNavigation = !navController.navigateUp();
        }
        if(unsuccessfulNavigation) super.onBackPressed();
    }
}