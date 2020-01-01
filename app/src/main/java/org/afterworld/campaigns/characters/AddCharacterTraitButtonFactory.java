package org.afterworld.campaigns.characters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import org.afterworld.campaigns.traits.Trait;
import org.afterworld.campaigns.traits.TraitStore;

import java.util.List;

public class AddCharacterTraitButtonFactory {

  public static void createButton(final Activity activity, final Button button, final String id, final CharacterUpdate callback) {
    final CampaignCharacter campaignCharacter = CampaignCharacterStore.getInstance(activity).getCharacter(id);

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        final List<Trait> traits = TraitStore.getInstance(activity).getTraits();
        String[] options = new String[traits.size()];
        for (int i = 0; i < traits.size(); i++) {
          options[i] = traits.get(i).getName();
        }

        builder
            .setItems(options, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int i) {
                Trait trait = traits.get(i);

                campaignCharacter.getTraits().add(trait);
                callback.update(campaignCharacter);

                dialog.dismiss();
              }
            });

        AlertDialog dialog = builder.create();
        dialog.show();
      }
    });
  }

}
