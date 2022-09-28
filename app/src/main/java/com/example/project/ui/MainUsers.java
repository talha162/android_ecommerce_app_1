package com.example.project.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.project.R;
//import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainUsers extends AppCompatActivity {
    BottomNavigationView navView;
    int i=0;
    private long pressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        navView = findViewById(R.id.nav_view1);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.l1, new Products());
            ft.commit();


            navView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId() == R.id.cart) {
                        Bundle b3 = getIntent().getExtras();
                        Carts c = new Carts();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        c.setArguments(b3);
                        ft.replace(R.id.l1, c);
                        ft.commit();
                    }
                    if (item.getItemId() == R.id.explore) {
                        Products p = new Products();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.l1, p);
                        ft.commit();
//                    Bundle b=getIntent().getExtras()=null;
                    }

                    if (item.getItemId() == R.id.user) {
                        UserAccountsInfo u = new UserAccountsInfo();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.l1, u);
                        ft.commit();
                    }

                    return true;
                }
            });
        }
    }
    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    }



