package com.orengo.miquel.proyectojedi;

import android.app.Fragment;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener{

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();
        onFragmentInteraction("Perfil",1);
    }


    private void setUpViews() {
        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navview);

        //Initializing DrawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout)
        ;
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    case R.id.menu_perfil:
                        //Toast.makeText(DrawerActivity.this, "Profile Selected", Toast.LENGTH_SHORT).show();
                        onFragmentInteraction("Perfil",1);
                        break;

                    case R.id.menu_memory:
                        //Toast.makeText(DrawerActivity.this, "Rate Selected", Toast.LENGTH_SHORT).show();
                        onFragmentInteraction("Memory", 2);
                        break;

                    case R.id.menu_calculadora:
                        //Toast.makeText(DrawerActivity.this, "Purchase Selected", Toast.LENGTH_SHORT).show();
                        onFragmentInteraction("Calculadora",3);
                        break;
                    case R.id.menu_ranking:
                        //Toast.makeText(DrawerActivity.this, "Purchase Selected", Toast.LENGTH_SHORT).show();
                        onFragmentInteraction("Ranking", 4);
                        break;
                    case R.id.menu_reproductor:
                        //Toast.makeText(DrawerActivity.this, "Purchase Selected", Toast.LENGTH_SHORT).show();
                        onFragmentInteraction("Reproductor",5);
                        break;
                }
                return true;
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    public void onFragmentInteraction(String text, Integer from) {
        android.support.v4.app.Fragment f = null;
        Bundle b = new Bundle();
        switch(from){
            case 1:
                setTitle(text);
                f = new PerfilUsuario();
                break;
            case 2:
                setTitle(text);
                break;
            case 3:
                setTitle(text);
                break;
            case 4:
                setTitle(text);
                break;
            case 5:
                setTitle(text);
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameDrawer, f);
        fragmentTransaction.commit();
    }
}
