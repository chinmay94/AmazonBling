package com.amazon.mshopbling;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.amazon.mshopbling.MenuFragments.Account;
import com.amazon.mshopbling.MenuFragments.Bling;
import com.amazon.mshopbling.MenuFragments.BlingInfluencer;
import com.amazon.mshopbling.MenuFragments.Customer;
import com.amazon.mshopbling.MenuFragments.Home;
import com.amazon.mshopbling.MenuFragments.Order;
import com.amazon.mshopbling.MenuFragments.Pay;
import com.amazon.mshopbling.MenuFragments.Prime;
import com.amazon.mshopbling.MenuFragments.Sell;
import com.amazon.mshopbling.MenuFragments.Settings;
import com.amazon.mshopbling.MenuFragments.Shop;
import com.amazon.mshopbling.MenuFragments.Today;
import com.amazon.mshopbling.MenuFragments.Wish;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //add this line to display menu1 when the activity is loaded
        displaySelectedScreen(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_home:
                fragment = new Home();
                break;
            case R.id.nav_shop:
                fragment = new Shop();
                break;
            case R.id.nav_today:
                fragment = new Today();
                break;
            case R.id.nav_order:
                fragment = new Order();
                break;
            case R.id.nav_wish:
                fragment = new Wish();
                break;
            case R.id.nav_account:
                fragment = new Account();
                break;
            case R.id.nav_pay:
                fragment = new Pay();
                break;
            case R.id.nav_prime:
                fragment = new Prime();
                break;
            case R.id.nav_bling:
                fragment = new Bling();
                break;
            case R.id.nav_bling_influencer:
                fragment = new BlingInfluencer();
                break;
            case R.id.nav_sell:
                fragment = new Sell();
                break;
            case R.id.nav_settings:
                fragment = new Settings();
                break;
            case R.id.nav_customer:
                fragment = new Customer();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }


}
