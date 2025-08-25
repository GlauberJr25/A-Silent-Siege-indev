package poggers.oldlegion.listeners;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.GameState;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.util.Misc;
import poggers.oldlegion.campaign.plugins.NatarukIntroTestPlugin;

public class OldLegionJFIntCheckScript implements EveryFrameScript {
    public void advance(float amount) {
        if (Global.getCurrentState().equals(GameState.CAMPAIGN) && !Global.getSector().isPaused()) {
            CampaignFleetAPI playerFleet = Global.getSector().getPlayerFleet();
            StarSystemAPI currSystem = playerFleet.getStarSystem();
            if (Global.getSector().getMemoryWithoutUpdate().contains("$oldlegion_IntroExpositionDone")) {
                Global.getSector().removeTransientScript(this);
            } else if (currSystem != null && currSystem.getName().contains("Nataruk")) {
                if (!Global.getSector().getMemoryWithoutUpdate().contains("$oldlegion_IntroExpositionDone")) {
                    Global.getSector().getMemoryWithoutUpdate().set("$oldlegion_IntroExpositionDone", true);
//                    Global.getSector().getCampaignUI().showInteractionDialog(new NatarukIntroTestPlugin(), (SectorEntityToken)null);
                    // Rules.csv way of triggering dialog allowing full use of its system
                    CampaignFleetAPI pf = Global.getSector().getPlayerFleet();
                    Misc.showRuleDialog(pf, "YourInitialTrigger"); // YourInitialTrigger = initial trigger
                }
            }
        }
    }


    public boolean isDone() {
        return false;
    }

    public boolean runWhilePaused() {
        return false;
    }
}
