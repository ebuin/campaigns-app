package org.afterworld.campaigns;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.afterworld.campaigns.characters.CampaignCharacter;
import org.afterworld.campaigns.characters.CampaignCharacterStore;
import org.afterworld.campaigns.characters.CharacterDetailActivity;
import org.afterworld.campaigns.characters.CharacterDetailFragment;
import org.afterworld.campaigns.characters.NewCharacterButtonFactory;
import org.afterworld.campaigns.traits.TraitNavigationButtonFactory;
import org.afterworld.campaigns.traits.TraitStore;

import java.util.List;

public class CharacterListActivity extends AppCompatActivity {

  /**
   * Whether or not the activity is in two-pane mode, i.e. running on a tablet
   * device.
   */
  private boolean mTwoPane;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_character_list);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setTitle(getTitle());

    DieRollButtonFactory.createButton(this, (FloatingActionButton) findViewById(R.id.die_roll));
    NewCharacterButtonFactory.createButton(this, (FloatingActionButton) findViewById(R.id.new_character));
    TraitNavigationButtonFactory.createButton(this, (ImageButton) findViewById(R.id.traits_nav));

    if (findViewById(R.id.item_detail_container) != null) {
      mTwoPane = true;
    }

    View recyclerView = findViewById(R.id.item_list);
    assert recyclerView != null;
    setupRecyclerView((RecyclerView) recyclerView);
  }

  private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
    recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, CampaignCharacterStore.getInstance(this).getCharacters(), mTwoPane));
  }

  public static class SimpleItemRecyclerViewAdapter
      extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final CharacterListActivity mParentActivity;
    private final List<CampaignCharacter> mValues;
    private final boolean mTwoPane;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        CampaignCharacter item = (CampaignCharacter) view.getTag();
        if (mTwoPane) {
          Bundle arguments = new Bundle();
          arguments.putString(CharacterDetailFragment.ARG_ITEM_ID, item.id);
          CharacterDetailFragment fragment = new CharacterDetailFragment();
          fragment.setArguments(arguments);
          mParentActivity.getSupportFragmentManager().beginTransaction()
              .replace(R.id.item_detail_container, fragment)
              .commit();
        } else {
          Context context = view.getContext();
          Intent intent = new Intent(context, CharacterDetailActivity.class);
          intent.putExtra(CharacterDetailFragment.ARG_ITEM_ID, item.id);

          context.startActivity(intent);
        }
      }
    };

    SimpleItemRecyclerViewAdapter(CharacterListActivity parent,
                                  List<CampaignCharacter> items,
                                  boolean twoPane) {
      mValues = items;
      mParentActivity = parent;
      mTwoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.character_list_content, parent, false);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
      holder.mContentView.setText(mValues.get(position).getName());

      holder.itemView.setTag(mValues.get(position));
      holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
      return mValues.size();
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
