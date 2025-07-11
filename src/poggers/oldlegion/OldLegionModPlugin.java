package poggers.oldlegion;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import exerelin.campaign.SectorManager;
import poggers.oldlegion.listeners.GateJumpTracker;
import poggers.oldlegion.world.OldLegionModGen;

public class OldLegionModPlugin extends BaseModPlugin {
    private static void initMyMod() {
        new OldLegionModGen().generate(Global.getSector());
    }



    public void onGameLoad(boolean newGame) {
        if (!Global.getSector().getListenerManager().hasListenerOfClass(GateJumpTracker.class))
            Global.getSector().getListenerManager().addListener(new GateJumpTracker(), true);
    }



    @Override
    public void onNewGame() {
        Global.getLogger(this.getClass()).info("Hooray, Old Legion plugin jar is loaded!");
        initMyMod();

        boolean isNexerelinEnabled = Global.getSettings().getModManager().isModEnabled("nexerelin");

        if (!isNexerelinEnabled || SectorManager.getManager().isCorvusMode()) {
            new OldLegionModGen().generate(Global.getSector());
        // You can add more methods from ModPlugin here. Press Control-O in IntelliJ to see options.
        }
    }
}