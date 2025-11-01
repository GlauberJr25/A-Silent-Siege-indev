package poggers.oldlegion;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.listeners.ListenerManagerAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import exerelin.campaign.SectorManager;
import poggers.oldlegion.listeners.CoreUIListener;
import poggers.oldlegion.listeners.GateJumpTracker;
import poggers.oldlegion.listeners.OldLegionJFIntCheckScript;
import poggers.oldlegion.listeners.SystemListener;
import poggers.oldlegion.utils.OldLegionPeople;
import poggers.oldlegion.world.OldLegionModGen;

public class OldLegionModPlugin extends BaseModPlugin {

    private static void initMyMod() {
        new OldLegionModGen().generate(Global.getSector());
    }

    // call order: onNewGame -> onNewGameAfterProcGen -> onNewGameAfterEconomyLoad -> onEnabled -> onNewGameAfterTimePass -> onGameLoad

    // TODO: Actual Dialog and art for characters (LOTS OF DIALOG TO FIX) (1)
    // TODO: change license (maybe not?)
    // TODO: trade shroud/threat/omega items for rep (3)
    // TODO: scuttle charge ''d-mod'' (2)
    // TODO: maaaybe add the seed as the domain id when talking?

    private void addListenersIfNeeded() {
        ListenerManagerAPI l = Global.getSector().getListenerManager();
        // Add any listener to this
        if (!l.hasListenerOfClass(GateJumpTracker.class))
            l.addListener(new GateJumpTracker(), true);

        if (!l.hasListenerOfClass(CoreUIListener.class))
            l.addListener(new CoreUIListener(), true);


    }

    private static void addTransientScriptsIfNeeded(SectorAPI sector) {

        // Add any transient scripts to this
        if (!sector.hasTransientScript(SystemListener.class))
            sector.addTransientScript(new SystemListener());

        if (!sector.hasTransientScript(OldLegionJFIntCheckScript.class))
            sector.addTransientScript(new OldLegionJFIntCheckScript());

    }

    public void onGameLoad(boolean newGame) {
        SectorAPI sector = Global.getSector();
        MemoryAPI sector_mem = Global.getSector().getMemoryWithoutUpdate();

        addListenersIfNeeded();
        addTransientScriptsIfNeeded(sector);

        OldLegionModGen.trySpawnOutpost(sector);
        //OldLegionModGen.trySpawnStormEye(sector);

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