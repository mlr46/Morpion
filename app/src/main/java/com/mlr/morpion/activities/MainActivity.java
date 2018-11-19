package com.mlr.morpion.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mlr.morpion.R;

import java.util.Locale;

import static com.mlr.morpion.activities.MorpionActivity.EXTRA_GRID_SIZE;

public class MainActivity extends AppCompatActivity {

  private static final int MIN_SIZE = 10;

  private TextView tvGridSize;
  private SeekBar sizeChoice;
  private int gridSize;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    sizeChoice = findViewById(R.id.grid_size);
    tvGridSize = findViewById(R.id.grid_size_label);

    initializeSeekbar();
    sizeChoice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        gridSize = progress;
        updateDisplay();
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });
  }

  private void initializeSeekbar() {
    gridSize = MIN_SIZE;
    sizeChoice.setProgress(gridSize);
    updateDisplay();
  }

  public void startGame(View view) {
    if (gridSize >= MIN_SIZE) {
      Intent intent = new Intent(this, MorpionActivity.class);
      intent.putExtra(EXTRA_GRID_SIZE, gridSize);
      startActivity(intent);
    } else {
      showInvalidSize();
    }
  }

  private void showInvalidSize() {
    Toast toast = Toast.makeText(this, "Invalid size...", Toast.LENGTH_SHORT);
    toast.show();
  }

  /**
   * For the time being, the update display function will update the grid size label.
   */
  private void updateDisplay() {
    String gridSizeText = String.format(Locale.getDefault(), "%1$d x %1$d", gridSize);
    tvGridSize.setText(gridSizeText);
  }
}
