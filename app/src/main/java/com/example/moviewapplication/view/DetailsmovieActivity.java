package com.example.moviewapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.moviewapplication.R;
import com.example.moviewapplication.databinding.ActivityDetailsmovieBinding;
import com.example.moviewapplication.models.Apiresponse;

public class DetailsmovieActivity extends AppCompatActivity {
    ActivityDetailsmovieBinding activityDetailsmovieBinding;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailsmovieBinding = DataBindingUtil.setContentView(this, R.layout.activity_detailsmovie);
        activityDetailsmovieBinding.toolbar.setTitle("");
        setSupportActionBar(activityDetailsmovieBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // retrieving the  value of movie one data
        intent = getIntent();
        Apiresponse.OneMovie oneMovie = (Apiresponse.OneMovie) getIntent().getSerializableExtra("data");
        activityDetailsmovieBinding.setMydatamovie(oneMovie);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
