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
  private SeekBar sizeChoice;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    sizeChoice = findViewById(R.id.grid_size);
    sizeChoice.setOnSeekBarChangeListener(
      new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
      }
    );
    // add listener here
    startBtn = findViewById(R.id.startBtn);
    startBtn.on
  }

  /**
   * Should call the new view to create a grid of size gridSize * gridSize
   * @param size
   */
  private void startGame(int size) {}
}
