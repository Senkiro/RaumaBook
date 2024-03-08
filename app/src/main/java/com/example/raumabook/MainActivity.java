package com.example.raumabook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    Toolbar toolbar;
    GoogleSignInClient gsc;
//    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();


//        fab = findViewById(R.id.fab);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_Close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int iteamId = item.getItemId();
                if(iteamId == R.id.bottom_home){
                    openFragment(new HomeFragment());
                    return true;
                } else if (iteamId == R.id.bottom_community) {
                    openFragment(new AboutUsFragment());
                    return true;
                }else if (iteamId == R.id.bottom_history) {
                    openFragment(new HistoryFragment());
                    return true;
                }else if (iteamId == R.id.bottom_account) {
                    openFragment(new AccountFragment());
                    return true;
                }
                return false;
            }
        });

        fragmentManager = getSupportFragmentManager();
        openFragment(new HomeFragment());

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Upload", Toast.LENGTH_SHORT).show();
//            }
//        });

        // Khởi tạo GoogleSignInClient
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);
        Intent intent = getIntent();
        if (intent != null) {
            String fragmentToOpen = intent.getStringExtra("fragmentToOpen");
            if ("community".equals(fragmentToOpen)) {
                openFragment(new CommunityFragment());
            } else if ("read".equals(fragmentToOpen)) {
                openFragment(new ReadPageFragment());
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.nav_shopping){
            openFragment(new ShoppingFragment());
        } else if (itemId == R.id.nav_cart){
            openFragment(new CartFragment());
        }else if (itemId == R.id.nav_account){
            openFragment(new AccountFragment());
        }else if (itemId == R.id.nav_logout) {
            signOut();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }


    private void openFragment(Fragment fragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    void signOut() {

        SharedPreferences preferences = getSharedPreferences("account", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        boolean isSaved = preferences.getBoolean("is_saved", false);
        if(isSaved == false){
            editor.clear();
            editor.apply();
        }else{
            editor.apply();
        }
        // Sign out khỏi Google
        gsc.signOut().addOnCompleteListener(this, task -> {
            // Xử lý sau khi sign out thành công
            startActivity(new Intent(MainActivity.this, Home_Login.class));
            finish();
        });
    }
}