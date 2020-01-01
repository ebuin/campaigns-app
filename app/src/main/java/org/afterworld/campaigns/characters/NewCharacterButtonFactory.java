package org.afterworld.campaigns.characters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import androidx.core.content.ContextCompat;
import info.androidhive.fontawesome.FontDrawable;
import org.afterworld.campaigns.R;

public class NewCharacterButtonFactory {

  public static void createButton(final Activity activity, final ImageButton button) {
    FontDrawable drawable = new FontDrawable(activity, R.string.fa_plus_solid, true, false);
    drawable.setTextColor(ContextCompat.getColor(activity, android.R.color.white));
    button.setImageDrawable(drawable);

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        CampaignCharacter new_character = new CampaignCharacter("New character");
        CampaignCharacterStore.getInstance(activity).addCharacter(new_character);


        Context context = view.getContext();
        Intent intent = new Intent(context, CharacterDetailActivity.class);
        intent.putExtra(CharacterDetailFragment.ARG_ITEM_ID, new_character.id);

        context.startActivity(intent);
      }
    });
  }


}
