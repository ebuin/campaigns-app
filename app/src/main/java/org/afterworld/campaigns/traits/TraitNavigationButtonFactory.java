package org.afterworld.campaigns.traits;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import androidx.core.content.ContextCompat;
import info.androidhive.fontawesome.FontDrawable;
import org.afterworld.campaigns.R;

public class TraitNavigationButtonFactory {


  public static void createButton(final Activity activity, ImageButton button) {
    FontDrawable drawable = new FontDrawable(activity, R.string.fa_tags_solid, true, false);
    drawable.setTextColor(ContextCompat.getColor(activity, android.R.color.white));
    button.setImageDrawable(drawable);

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, TraitListActivity.class);
        context.startActivity(intent);
      }
    });
  }

}
