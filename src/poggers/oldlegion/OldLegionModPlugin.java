package poggers.oldlegion;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import exerelin.campaign.SectorManager;
import poggers.oldlegion.listeners.GateJumpTracker;
import poggers.oldlegion.world.OldLegionModGen;
import poggers.oldlegion.utils.oldlegion_people;

public class OldLegionModPlugin extends BaseModPlugin {
    private static void initMyMod() {
        new OldLegionModGen().generate(Global.getSector());
    }



    public void onGameLoad(boolean newGame) {
        MarketAPI jangala = Global.getSector().getEconomy().getMarket("jangala");
        if (jangala != null) {
            oldlegion_people.oldlegion_createMiscCharacters();
        }

        if (!Global.getSector().getListenerManager().hasListenerOfClass(GateJumpTracker.class)) Global.getSector().getListenerManager().addListener(new GateJumpTracker(), true);
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