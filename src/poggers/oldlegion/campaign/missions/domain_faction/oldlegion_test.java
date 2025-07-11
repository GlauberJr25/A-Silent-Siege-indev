package poggers.oldlegion.campaign.missions.domain_faction;

import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithSearch;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import poggers.oldlegion.utils.OldLegionPeople;

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

       OLDLEGION_NATAH = getImportantPerson(OldLegionPeople.OLDLEGION_NATAH);
       if (OLDLEGION_NATAH == null) return false;

       OLDLEGION_SOLAX = getImportantPerson(OldLegionPeople.OLDLEGION_SOLAX);
       if (OLDLEGION_SOLAX == null) return false;

       Hunhow_market = Global.getSector().getEconomy().getMarket("Hunhow_market");
       if (Hunhow_market == null) return false;

       setStartingStage(oldlegion_test.Stage.TALK_TO_SOLAX);
       addSuccessStages(oldlegion_test.Stage.COMPLETED);

       setStoryMission();

       setStageOnGlobalFlag(oldlegion_test.Stage.COMPLETED, "$oldlegion_test_completed");

       makeImportant(OLDLEGION_SOLAX, null, oldlegion_test.Stage.TALK_TO_SOLAX);
       setStageOnMemoryFlag(oldlegion_test.Stage.COMPLETED, OLDLEGION_SOLAX.getMarket(), "$oldlegion_test_completed");

       beginStageTrigger(oldlegion_test.Stage.COMPLETED);
       triggerSetGlobalMemoryValue("$oldlegion_test_completed", true);
       triggerMakeNonStoryCritical(Hunhow_market, OLDLEGION_NATAH.getMarket(), OLDLEGION_SOLAX.getMarket());
       endTrigger();

       setRepFactionChangesMedium();
       setRepPersonChangesNone();

       return true;
   }

   @Override
   public void addDescriptionForNonEndStage(TooltipMakerAPI info, float width, float height) {
       float opad = 10f;
       Color h = Misc.getHighlightColor();
       if (currentStage == Stage.TALK_TO_SOLAX) {
           info.addPara("Get a lead on the whereabouts of Kelise Astraia, who is associated with "
                   + "a secret Tri-Tachyon research base called \"Alpha Site\".", opad);
       }

   }
    @Override
    public boolean addNextStepText(TooltipMakerAPI info, Color tc, float pad) {
        Color h = Misc.getHighlightColor();
        if (currentStage == Stage.TALK_TO_SOLAX) {
            info.addPara("Get a lead on the whereabouts of Kelise Astraia", tc, pad);
            return true;
        }
        return false;
    }

    @Override
    public String getBaseName() {
        return "Project Birthday (definitely not ziggurat)";
    }
}
