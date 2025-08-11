package poggers.oldlegion;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.listeners.ListenerManagerAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import exerelin.campaign.SectorManager;
import poggers.oldlegion.listeners.CoreUIListener;
import poggers.oldlegion.listeners.GateJumpTracker;
import poggers.oldlegion.utils.OldLegionPeople;
import poggers.oldlegion.world.OldLegionModGen;

import static com.fs.starfarer.api.Global.getSettings;

public class OldLegionModPlugin extends BaseModPlugin {

    private static void initMyMod() {
        new OldLegionModGen().generate(Global.getSector());
    }

    // call order: onNewGame -> onNewGameAfterProcGen -> onNewGameAfterEconomyLoad -> onEnabled -> onNewGameAfterTimePass -> onGameLoad


    // TODO: Actual Dialog and art for characters (LOTS OF DIALOG TO FIX)
    // TODO: change license (maybe not?)
    // TODO: stop faction relationships from changing with NEX
    // TODO: block transverse jump on outpost system (number 2 priority)
    // TODO: fix npc comm order issue (number 1 priority)
    // TODO: trade shroud/threat/omega items for rep


    private void addListenersIfNeeded() {
        ListenerManagerAPI l = Global.getSector().getListenerManager();

        if (!l.hasListenerOfClass(GateJumpTracker.class))
            l.addListener(new GateJumpTracker(), true);

        if (!l.hasListenerOfClass(CoreUIListener.class))
            l.addListener(new CoreUIListener(), true);

    }

    public void onGameLoad(boolean newGame) {
        SectorAPI sector = Global.getSector();
        MemoryAPI sector_mem = Global.getSector().getMemoryWithoutUpdate();

        addListenersIfNeeded();

        OldLegionModGen.trySpawnOutpost(sector);

        OldLegionPeople.oldlegion_createStoryCharacters();
        OldLegionPeople.create();
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

    @Override
    public void onApplicationLoad() throws Exception
    {
        OldLegionPeople.setupDomainContactMissions();
    }
}