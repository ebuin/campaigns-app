package org.afterworld.campaigns;

import android.content.Context;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import info.androidhive.fontawesome.FontDrawable;

import java.util.Random;

public class DieRollButtonFactory {

  public static void createButton(Context context, FloatingActionButton fab) {
    FontDrawable drawable = new FontDrawable(context, R.string.fa_dice_d20_solid, true, false);
    drawable.setTextColor(ContextCompat.getColor(context, android.R.color.white));
    fab.setImageDrawable(drawable);

    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        int roll = new Random().nextInt(20) + 1;
        Snackbar.make(view, "Rolled " + roll, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });
  }

}
