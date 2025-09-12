package poggers.oldlegion.listeners;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import poggers.oldlegion.scripts.TransverseAvailabilityScript;

public class SystemListener implements EveryFrameScript {
    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean runWhilePaused() {
        return false;
    }

    @Override
    public void advance(float amount) {
        CampaignFleetAPI playerFleet = Global.getSector().getPlayerFleet();
        if (playerFleet.getStarSystem() == null) return;
        // Adds script when in Nataruk system
        if (playerFleet.getStarSystem().getName().contains("ataruk") &&
                !playerFleet.hasScriptOfClass(TransverseAvailabilityScript.class)) {
            playerFleet.addScript(new TransverseAvailabilityScript());
        }
        // Removes script when no longer in Nataruk system
        if (!playerFleet.getStarSystem().getName().contains("ataruk") &&
                playerFleet.hasScriptOfClass(TransverseAvailabilityScript.class)) {
            playerFleet.removeScriptsOfClass(TransverseAvailabilityScript.class);
        }
    }
}
