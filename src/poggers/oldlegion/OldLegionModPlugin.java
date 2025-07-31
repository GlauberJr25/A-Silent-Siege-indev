package poggers.oldlegion;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import exerelin.campaign.SectorManager;
import poggers.oldlegion.listeners.GateJumpTracker;
import poggers.oldlegion.utils.OldLegionPeople;
import poggers.oldlegion.world.OldLegionModGen;

public class OldLegionModPlugin extends BaseModPlugin {
    private static void initMyMod() {
        new OldLegionModGen().generate(Global.getSector());
    }

    // TODO: Actual Dialog and art for characters
    // TODO: Actual quests and stuff for reputation
    // TODO: change license
    // TODO: stop faction relationships from changing with NEX

    public void onGameLoad(boolean newGame) {
        SectorAPI sector = Global.getSector();
        MemoryAPI sector_mem = Global.getSector().getMemoryWithoutUpdate();

        if (!Global.getSector().getListenerManager().hasListenerOfClass(GateJumpTracker.class))
            Global.getSector().getListenerManager().addListener(new GateJumpTracker(), true);

        OldLegionPeople.oldlegion_createStoryCharacters();
        OldLegionPeople.create();

        OldLegionModGen.trySpawnOutpost(sector);
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