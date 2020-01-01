package org.afterworld.campaigns.characters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import info.androidhive.fontawesome.FontDrawable;
import org.afterworld.campaigns.R;

public class RenameCharacterButtonFactory {

  public static void createButton(final Activity activity, final FloatingActionButton button, final String id, final CharacterUpdate callback) {
    final CampaignCharacter campaignCharacter = CampaignCharacterStore.getInstance(activity).getCharacter(id);

    FontDrawable drawable = new FontDrawable(activity, R.string.fa_pen_solid, true, false);
    drawable.setTextColor(ContextCompat.getColor(activity, android.R.color.white));
    button.setImageDrawable(drawable);

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = activity.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.character_rename, null);
        builder.setView(dialogView)
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int id) {
                TextInputEditText inputField = (TextInputEditText) dialogView.findViewById(R.id.new_character_name);
                Editable text = inputField.getText();
                if (text == null)
                  return;

                String name = text.toString();
                campaignCharacter.setName(name);
                callback.update(campaignCharacter);

                dialog.dismiss();
              }
            })
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
              }
            });

        AlertDialog dialog = builder.create();
        dialog.show();
      }
    });
  }


}
