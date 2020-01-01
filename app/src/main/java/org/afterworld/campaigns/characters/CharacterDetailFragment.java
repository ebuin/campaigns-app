package org.afterworld.campaigns.characters;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.afterworld.campaigns.CharacterListActivity;
import org.afterworld.campaigns.R;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link CharacterListActivity}
 * in two-pane mode (on tablets) or a {@link CharacterDetailActivity}
 * on handsets.
 */
public class CharacterDetailFragment extends Fragment {
  /**
   * The fragment argument representing the item ID that this fragment
   * represents.
   */
  public static final String ARG_ITEM_ID = "item_id";

  /**
   * The dummy content this fragment is presenting.
   */
  private CampaignCharacter character;
  private View rootView;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public CharacterDetailFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments().containsKey(ARG_ITEM_ID)) {
      character = CampaignCharacterStore.getInstance(getActivity()).getCharacter(getArguments().getString(ARG_ITEM_ID));
      updateTitle();
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    final Activity activity = this.getActivity();

    rootView = inflater.inflate(R.layout.character_detail, container, false);
    ModifyHpButtonFactory.createPlusButton(
        activity, (Button) rootView.findViewById(R.id.hp_plus),
        character.id,
        new CharacterUpdate() {
          @Override
          public void update(CampaignCharacter character) {
            updateText();
            CampaignCharacterStore.getInstance(activity).updateCharacter(character);
          }
        }
    );
    ModifyHpButtonFactory.createMinusButton(
        activity, (Button) rootView.findViewById(R.id.hp_minus),
        character.id,
        new CharacterUpdate() {
          @Override
          public void update(CampaignCharacter character) {
            updateText();
            CampaignCharacterStore.getInstance(activity).updateCharacter(character);
          }
        }
    );

    updateText();
    CharacterTraitsListFactory.createList(activity, rootView, character.id);

    return rootView;
  }

  public void updateTitle() {
    if (character != null) {
      Activity activity = this.getActivity();
      final CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
      if (appBarLayout != null) {
        appBarLayout.setTitle(character.getName());
      }
    }
  }

  public void updateText() {
    if (character != null && rootView != null) {
      StringBuilder text = new StringBuilder();

      text.append("HP: ")
          .append(character.getHp())
          .append(" / ")
          .append(character.getMaxHp());

      ((TextView) rootView.findViewById(R.id.character_hp)).setText(text.toString());
    }
  }
}
