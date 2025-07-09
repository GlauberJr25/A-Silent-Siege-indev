package poggers.oldlegion.campaign.missions.domain_faction;

import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithSearch;
import poggers.oldlegion.world.systems.MySystemOne;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.NascentGravityWellAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.People;
import com.fs.starfarer.api.impl.campaign.missions.hub.BaseMissionHub;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import poggers.oldlegion.utils.oldlegion_people;

import java.awt.Color;

public class oldlegion_test extends HubMissionWithSearch {

   public static enum Stage {
       TALK_TO_SOLAX,
       COMPLETED,
   }

   protected PersonAPI OLDLEGION_NATAH;
   protected PersonAPI OLDLEGION_SOLAX;
   protected MarketAPI Hunhow_market;

   protected boolean create(MarketAPI createdAt, boolean barEvent) {
      if (this.OLDLEGION_NATAH == null) {
           return false;
       }

       OLDLEGION_NATAH = getImportantPerson(oldlegion_people.OLDLEGION_NATAH);
       if (OLDLEGION_NATAH == null) return false;

       OLDLEGION_SOLAX = getImportantPerson(oldlegion_people.OLDLEGION_SOLAX);
       if (OLDLEGION_NATAH == null) return false;

       setStartingStage(oldlegion_test.Stage.TALK_TO_SOLAX);
       addSuccessStages(oldlegion_test.Stage.COMPLETED);

       setStoryMission();

       makeImportant(OLDLEGION_NATAH.getMarket(), null, oldlegion_test.Stage.TALK_TO_SOLAX);
       setStageOnMemoryFlag(oldlegion_test.Stage.COMPLETED, OLDLEGION_NATAH.getMarket(), "$oldlegion_test_completed");

       setRepFactionChangesNone();
       setRepPersonChangesNone();

       return true;
   }

}
