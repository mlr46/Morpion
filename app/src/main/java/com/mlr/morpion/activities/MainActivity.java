package com.mlr.morpion.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mlr.morpion.R;

public class MainActivity extends AppCompatActivity {

    private Button startBtn;
    private TextView tvGridSize;
    private SeekBar gridSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
