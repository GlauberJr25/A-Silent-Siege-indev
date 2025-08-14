package poggers.oldlegion.scripts;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.characters.AbilityPlugin;
import com.fs.starfarer.api.impl.campaign.ids.Abilities;

public class TransverseAvailabilityScript implements EveryFrameScript {
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
        AbilityPlugin ability = Global.getSector().getPlayerFleet().getAbility(Abilities.TRANSVERSE_JUMP);
        if (ability == null) return;
        ability.forceDisable();
    }
}
