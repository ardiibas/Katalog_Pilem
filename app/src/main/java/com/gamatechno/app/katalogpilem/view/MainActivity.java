package com.gamatechno.app.katalogpilem.view;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.gamatechno.app.katalogpilem.R;
import com.gamatechno.app.katalogpilem.adapter.ViewPagerAdapter;
import com.gamatechno.app.katalogpilem.fragment.FavoriteFragment;
import com.gamatechno.app.katalogpilem.fragment.NowPlayingFragment;
import com.gamatechno.app.katalogpilem.fragment.UpComingFragment;
import com.gamatechno.app.katalogpilem.utils.NotifReciver;
import com.gamatechno.app.katalogpilem.utils.NotifTask;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private TabLayout tabLayout;

    private NotifReciver notifReciver = new NotifReciver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotifTask notifTask = new NotifTask(getApplication());
        notifTask.createPeriodicTask();

        notifReciver.setRepeatingAlarm(getApplication(), NotifReciver.TYPE_REPEATING, "07:00", "Cek Aplikasi kita dong sis gan");

        tabLayout = findViewById(R.id.main_tabs);
        ViewPager viewPager = findViewById(R.id.main_viewpager);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        setupTab();

        if(savedInstanceState == null){
            viewPager.setCurrentItem(0);
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem());
        }
    }

    private void setupTab() {
        TextView tabSatu = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabSatu.setText(getString(R.string.now_playing));
        Objects.requireNonNull(tabLayout.getTabAt(0)).setCustomView(tabSatu);

        TextView tabDua = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabDua.setText(getString(R.string.upcoming));
        Objects.requireNonNull(tabLayout.getTabAt(1)).setCustomView(tabDua);

        TextView tabTiga = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTiga.setText(getString(R.string.list_favorit));
        Objects.requireNonNull(tabLayout.getTabAt(2)).setCustomView(tabTiga);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NowPlayingFragment(), getString(R.string.now_playing));
        adapter.addFragment(new UpComingFragment(), getString(R.string.upcoming));
        adapter.addFragment(new FavoriteFragment(), getString(R.string.list_favorit));
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.action_search :
                SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
                searchView.setOnQueryTextListener(this);
                break;

            case R.id.setting :
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent i = new Intent(MainActivity.this, SearchActivity.class);
        i.putExtra("keyword", query);
        startActivity(i);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
