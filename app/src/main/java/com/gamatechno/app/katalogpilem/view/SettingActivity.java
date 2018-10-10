package com.gamatechno.app.katalogpilem.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gamatechno.app.katalogpilem.R;
import com.gamatechno.app.katalogpilem.fragment.MySetting;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.title_activity_settings));

        getFragmentManager().beginTransaction().replace(android.R.id.content, new MySetting()).commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
