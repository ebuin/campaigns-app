package org.afterworld.campaigns.characters;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class ModifyHpButtonFactory {

  public static void createPlusButton(final Activity activity, final Button button, final String id, final CharacterUpdate callback) {
    final CampaignCharacter campaignCharacter = getCharacter(activity, id);

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        int hp = campaignCharacter.getHp();
        if (hp == campaignCharacter.getMaxHp()) return;

        campaignCharacter.setHp(++hp);

        callback.update(campaignCharacter);
      }
    });
  }

  public static void createMinusButton(final Activity activity, final Button button, final String id, final CharacterUpdate callback) {
    final CampaignCharacter campaignCharacter = getCharacter(activity, id);

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        int hp = campaignCharacter.getHp();
        if (hp == 0) return;

        campaignCharacter.setHp(--hp);

        callback.update(campaignCharacter);
      }
    });
  }

  private static CampaignCharacter getCharacter(Activity activity, String id) {
    return CampaignCharacterStore.getInstance(activity).getCharacter(id);
  }


}
