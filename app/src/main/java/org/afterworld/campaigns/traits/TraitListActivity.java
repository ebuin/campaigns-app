package org.afterworld.campaigns.traits;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import org.afterworld.campaigns.R;

import java.util.List;


public class TraitListActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_trait_list);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setTitle(getTitle());

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    final View recyclerView = findViewById(R.id.trait_list);
    assert recyclerView != null;
    setupRecyclerView((RecyclerView) recyclerView);

    NewTraitButtonFactory.createButton(this, (ImageButton) findViewById(R.id.new_trait),
        new TraitUpdate() {
          @Override
          public void update(Trait trait) {
            int idx = TraitStore.getInstance(TraitListActivity.this).getTraits().indexOf(trait);
            ((RecyclerView) recyclerView).getAdapter().notifyItemInserted(idx);
          }
        });
  }

  private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
    recyclerView.setAdapter(new TraitRecyclerViewAdapter(this, TraitStore.getInstance(this).getTraits()));
  }

  public static class TraitRecyclerViewAdapter extends RecyclerView.Adapter<TraitRecyclerViewAdapter.ViewHolder> {

    private final TraitListActivity parent;
    private final List<Trait> traits;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        final Trait trait = (Trait) view.getTag();
        AlertDialog dialog = new AlertDialog.Builder(parent)
            .setMessage("Delete " + trait.getName() + "?")
            .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                int idx = traits.indexOf(trait);
                TraitStore.getInstance(parent).removeTrait(trait);
                TraitRecyclerViewAdapter.this.notifyItemRemoved(idx);
              }
            })
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
              }
            })
            .create();

        dialog.show();
      }
    };

    public TraitRecyclerViewAdapter(TraitListActivity parent, List<Trait> traits) {
      this.parent = parent;
      this.traits = traits;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.trait_list_content, parent, false);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
      holder.mContentView.setText(traits.get(position).getName());

      holder.itemView.setTag(traits.get(position));
      holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
      return traits.size();
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
