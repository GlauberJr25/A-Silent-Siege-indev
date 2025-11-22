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

    protected boolean create(MarketAPI createdAt, boolean barEvent) {
        setName("Unknown Operatives");
        setStoryMission();
        setRepFactionChangesNone();
        setRepPersonChangesNone();
        completedKey = "$oldlegion_intro_completed";

        OLDLEGION_NATAH = getImportantPerson(OldLegionPeople.OLDLEGION_NATAH);
        if (OLDLEGION_NATAH == null) return false;
        makeImportant(OLDLEGION_NATAH, "$olintro", Stage.TALK_TO_JOEL_ON_BASE);

        //setting the stages
        setStartingStage(Stage.TALK_TO_JOEL_ON_BASE);
        setSuccessStage(Stage.COMPLETED);
        setNoAbandon();
        //setting stage transition
        setStageOnGlobalFlag(Stage.COMPLETED, "$oldlegion_intro_completed");
        return true;
    }
    protected void updateInteractionDataImpl() {
        set("$oldlegion_intro_stage", getCurrentStage());
    }

    @Override
    public void addDescriptionForNonEndStage(TooltipMakerAPI info, float width, float height) {
        float opad = 10f;
        Color h = Misc.getHighlightColor();
        if (currentStage == Stage.TALK_TO_JOEL_ON_BASE) {
            info.addPara("Reach out to the leader of the outpost located on the nearby planet in this system.", opad);
        }
    }
    @Override
    public boolean addNextStepText(TooltipMakerAPI info, Color tc, float pad) {
        Color h = Misc.getHighlightColor();
        if (currentStage == Stage.TALK_TO_JOEL_ON_BASE) {
            info.addPara("Go to the nearby planetary outpost.", tc, pad);
            return true;
        }
        return false;
    }
}
