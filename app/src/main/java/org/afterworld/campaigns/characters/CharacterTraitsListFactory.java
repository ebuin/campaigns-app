package org.afterworld.campaigns.characters;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import org.afterworld.campaigns.R;
import org.afterworld.campaigns.traits.Trait;

public class CharacterTraitsListFactory {

  public static void createList(final Activity activity, View rootView, String characterId) {
    CampaignCharacter character = CampaignCharacterStore.getInstance(activity).getCharacter(characterId);

    final View recyclerView = rootView.findViewById(R.id.trait_list);
    assert recyclerView != null;
    setupRecyclerView((RecyclerView) recyclerView, activity, character);

    AddCharacterTraitButtonFactory.createButton(activity, (Button) rootView.findViewById(R.id.character_trait_add),
        characterId,
        new CharacterUpdate() {
          @Override
          public void update(CampaignCharacter character) {
            CampaignCharacterStore.getInstance(activity).updateCharacter(character);
            ((RecyclerView) recyclerView).getAdapter().notifyDataSetChanged();
          }
        });
  }

  private static void setupRecyclerView(@NonNull RecyclerView recyclerView, Activity activity, CampaignCharacter character) {
    recyclerView.setAdapter(new TraitRecyclerViewAdapter(character, activity));
  }

  public static class TraitRecyclerViewAdapter extends RecyclerView.Adapter<TraitRecyclerViewAdapter.ViewHolder> {

    private final CampaignCharacter character;
    private final Activity parent;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        final Trait trait = (Trait) view.getTag();
        AlertDialog dialog = new AlertDialog.Builder(parent)
            .setMessage("Remove " + trait.getName() + "?")
            .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int i) {
                character.getTraits().remove(trait);
                CampaignCharacterStore.getInstance(parent).updateCharacter(character);
                TraitRecyclerViewAdapter.this.notifyDataSetChanged();
              }
            })
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
              }
            })
            .create();

        dialog.show();
      }
    };

    public TraitRecyclerViewAdapter(CampaignCharacter character, Activity parent) {
      this.character = character;
      this.parent = parent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.trait_list_content, parent, false);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TraitRecyclerViewAdapter.ViewHolder holder, int position) {
      holder.mContentView.setText(character.getTraits().get(position).getName());

      holder.itemView.setTag(character.getTraits().get(position));
      holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
      return character.getTraits().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
      final TextView mContentView;

      ViewHolder(View view) {
        super(view);
        mContentView = (TextView) view.findViewById(R.id.content);
      }
    }
  }
}
