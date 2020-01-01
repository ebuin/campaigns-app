package org.afterworld.campaigns.traits;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import androidx.core.content.ContextCompat;
import com.google.android.material.textfield.TextInputEditText;
import info.androidhive.fontawesome.FontDrawable;
import org.afterworld.campaigns.R;

public class NewTraitButtonFactory {

  public static void createButton(final Activity activity, ImageButton button, final TraitUpdate traitUpdate) {
    FontDrawable drawable = new FontDrawable(activity, R.string.fa_plus_solid, true, false);
    drawable.setTextColor(ContextCompat.getColor(activity, android.R.color.white));
    button.setImageDrawable(drawable);

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = activity.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.trait_new, null);
        builder.setView(dialogView)
            .setPositiveButton(R.string.ok, confirm(activity, dialogView, traitUpdate))
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

  private static DialogInterface.OnClickListener confirm(final Activity activity, final View dialogView, final TraitUpdate traitUpdate) {
    return new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int id) {
        TextInputEditText inputField = (TextInputEditText) dialogView.findViewById(R.id.new_trait_name);
        Editable text = inputField.getText();
        if (text == null)
          return;

        Trait trait = new Trait(text.toString());
        TraitStore.getInstance(activity).addTrait(trait);

        traitUpdate.update(trait);

        dialog.dismiss();
      }
    };
  }

}
