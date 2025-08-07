package poggers.oldlegion.campaign.missions.domain_faction;

import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithSearch;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import poggers.oldlegion.utils.OldLegionPeople;

import java.awt.Color;

public class OldLegionIntroQuest extends HubMissionWithSearch {

    public static enum Stage {
        TALK_TO_JOEL_ON_BASE,
        COMPLETED,
    }
    protected PersonAPI OLDLEGION_NATAH;
    protected PersonAPI ARTANIS;
    //protected PersonAPI DOMAIN_CAPTAIN_ZERATUL;
    protected MarketAPI Hunhow_market;

    protected boolean create(MarketAPI createdAt, boolean barEvent) {
        OLDLEGION_NATAH = getImportantPerson(OldLegionPeople.OLDLEGION_NATAH);
        if (OLDLEGION_NATAH == null) return false;

        ARTANIS = getImportantPerson(OldLegionPeople.ARTANIS);
        if (ARTANIS == null) return false;

        //DOMAIN_CAPTAIN_ZERATUL = getImportantPerson(OldLegionPeople.DOMAIN_CAPTAIN_ZERATUL);
        //if (DOMAIN_CAPTAIN_ZERATUL == null) return false;

        Hunhow_market = Global.getSector().getEconomy().getMarket("Hunhow_market");
        if (Hunhow_market == null) return false;

        setStartingStage(OldLegionIntroQuest.Stage.TALK_TO_JOEL_ON_BASE);
        addSuccessStages(OldLegionIntroQuest.Stage.COMPLETED);

        setStoryMission();

        setStageOnGlobalFlag(OldLegionIntroQuest.Stage.COMPLETED, "$oldlegion_intro_completed");

        makeImportant(OLDLEGION_NATAH, null, OldLegionIntroQuest.Stage.TALK_TO_JOEL_ON_BASE);
        setStageOnMemoryFlag(OldLegionIntroQuest.Stage.COMPLETED, OLDLEGION_NATAH.getMarket(), "$oldlegion_intro_completed");

        beginStageTrigger(OldLegionIntroQuest.Stage.COMPLETED);
        triggerSetGlobalMemoryValue("$oldlegion_intro_completed", true);
        triggerMakeNonStoryCritical(Hunhow_market, OLDLEGION_NATAH.getMarket());
        endTrigger();

        setRepFactionChangesMedium();
        setRepPersonChangesNone();

        return true;
    }

    @Override
    public void addDescriptionForNonEndStage(TooltipMakerAPI info, float width, float height) {
        float opad = 10f;
        Color h = Misc.getHighlightColor();
        if (currentStage == OldLegionIntroQuest.Stage.TALK_TO_JOEL_ON_BASE) {
            info.addPara("Reach out to the leader of the outpost located on the nearby planet in this system.", opad);
        }

    }
    @Override
    public boolean addNextStepText(TooltipMakerAPI info, Color tc, float pad) {
        Color h = Misc.getHighlightColor();
        if (currentStage == OldLegionIntroQuest.Stage.TALK_TO_JOEL_ON_BASE) {
            info.addPara("Go to the nearby planetary outpost.", tc, pad);
            return true;
        }
        return false;
    }

    @Override
    public String getBaseName() {
        return "Unknown Operatives";
    }

}
