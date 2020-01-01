package org.afterworld.campaigns.characters;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.NavUtils;
import android.view.MenuItem;
import org.afterworld.campaigns.CharacterListActivity;
import org.afterworld.campaigns.DieRollButtonFactory;
import org.afterworld.campaigns.R;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link CharacterListActivity}.
 */
public class CharacterDetailActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_character_detail);
    Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
    setSupportActionBar(toolbar);

    DieRollButtonFactory.createButton(this, (FloatingActionButton) findViewById(R.id.die_roll));

    // Show the Up button in the action bar.
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    if (savedInstanceState == null) {
      // Create the detail fragment and add it to the activity
      // using a fragment transaction.
      Bundle arguments = new Bundle();
      String characterId = getIntent().getStringExtra(CharacterDetailFragment.ARG_ITEM_ID);

      arguments.putString(CharacterDetailFragment.ARG_ITEM_ID, characterId);
      final CharacterDetailFragment fragment = new CharacterDetailFragment();
      fragment.setArguments(arguments);
      getSupportFragmentManager().beginTransaction()
          .add(R.id.item_detail_container, fragment)
          .commit();


      RenameCharacterButtonFactory.createButton(
          this, (FloatingActionButton) findViewById(R.id.character_rename),
          characterId,
          new CharacterUpdate() {
            @Override
            public void update(CampaignCharacter character) {
              fragment.updateTitle();
              CampaignCharacterStore.getInstance(CharacterDetailActivity.this).updateCharacter(character);
            }
          }
      );
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      // This ID represents the Home or Up button. In the case of this
      // activity, the Up button is shown. Use NavUtils to allow users
      // to navigate up one level in the application structure. For
      // more details, see the Navigation pattern on Android Design:
      //
      // http://developer.android.com/design/patterns/navigation.html#up-vs-back
      //
      NavUtils.navigateUpTo(this, new Intent(this, CharacterListActivity.class));
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
