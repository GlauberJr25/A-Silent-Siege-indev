package poggers.oldlegion.listeners;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.listeners.GateTransitListener;
import com.fs.starfarer.api.impl.campaign.GateEntityPlugin;
import com.fs.starfarer.api.impl.campaign.rulecmd.missions.GateCMD;
import com.fs.starfarer.api.util.FaderUtil;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.campaign.BaseScript;
import com.fs.starfarer.campaign.fleet.CampaignFleet;
import org.lazywizard.console.Console;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.fs.starfarer.api.campaign.listeners.GateTransitListener;
import poggers.oldlegion.ReflectionUtilities;
import poggers.oldlegion.utils.OldLegionStrings;

public class GateJumpTracker implements GateTransitListener {

    boolean isKoLEnabled = Global.getSettings().getModManager().isModEnabled("knights_of_ludd");

    String memoryKey = "$oldlegion_domainspace_gate_glitch";

    String memoryKeyKoL = "$kol_nullspace_gate_glitch";

    int InterceptChance = 1;

    @Override
    public void reportFleetTransitingGate(CampaignFleetAPI fleet, SectorEntityToken gateFrom, SectorEntityToken gateTo) {
        StarSystemAPI destSys = Global.getSector().getStarSystem(OldLegionStrings.DomainSpaceSysName);
        if (Global.getSector().getMemoryWithoutUpdate().getKeys().contains(memoryKey)) {
            //Console.showMessage("If-Statement ONE fired their return");
            return; //Dont trigger multiple times.
        }
        if (isKoLEnabled) {
            if (!Global.getSector().getMemoryWithoutUpdate().getKeys().contains(memoryKeyKoL)) {
                Console.showMessage("If-Statement TWO fired their return");
                return; //Dont trigger before KoL triggers their Gate Interceptor.
            }
        }
        if (!Global.getSector().getMemoryWithoutUpdate().getBoolean("$gaATG_missionCompleted")) {
            //Console.showMessage("If-Statement THREE fired their return");
            return; //Prevent it from happening during the story-jump
            }

        boolean isNotPlayerFleet = !fleet.isPlayerFleet();
        boolean sendingGateNull = gateFrom == null;
        boolean tooLowCycle = Global.getSector().getClock().getCycle() < 206;
        boolean sendingGateIsDomain = gateFrom.getContainingLocation() == destSys;
        // TODO | IF wanted, put this back as it was, set like this so it's easier to debug which are true or false when the below condition is breakpointed.
        ///  ~Purple
        if (isNotPlayerFleet || sendingGateNull || tooLowCycle || sendingGateIsDomain) {
            //Console.showMessage("If-Statement FOUR fired their return");
            return;
        }
        //Console.showMessage("Before the Math.Random() if-statement");
        // TODO | Change the condition to be more proper, and remove any unnecessary " Console.showMessage " pieces of code :P
        ///  ~Purple
        if (((int)Math.random()*101) <= InterceptChance) {                    //(/*true*/ Math.random() <= 0.95f) { //0.05f = 5% chance to trigger.
            //Console.showMessage("After the Math.Random() if-statement");
            if (destSys == null) return;
            SectorEntityToken dest = destSys.getEntityById("domain_ops_gate");
            float dist = Misc.getDistanceLY(dest, gateTo);

            // TODO | (1) Idea, change the beneath condition to a memKey value in " Global.getSector().getPlayerMemoryWithoutUpdate() "
            // TODO | (2) This value is to be raised every time the condition isn't met yet (so the value isn't exceeding the given amount yet)
            // TODO | (3) Best to increase the value each time is to have a Random Number Picker between valueA and valueB,
            // TODO | NOTE, it currently ALWAYS fires whenever the gate you jump to is not within 10ly of the Nataruk system
            ///  ~Purple
            if (dist > 10f) {

                for (EveryFrameScript script : new ArrayList<>(Global.getSector().getScripts())) {
                    if (ReflectionUtilities.INSTANCE.hasVariableOfName("untilCanWarpOut", script)) {
                        Global.getSector().removeScript(script);
                    }
                }
                ReflectionUtilities.INSTANCE.invoke("setInJumpTransition", fleet, new Object[]{false}, false);

                //Start own Transverse
                Global.getSector().doHyperspaceTransition(fleet, gateFrom, new JumpPointAPI.JumpDestination(dest, ""), 5.0f);

                GateCMD.notifyScanned(dest);
                dest.getMemoryWithoutUpdate().set(GateEntityPlugin.GATE_SCANNED, true);

                Global.getSector().getMemoryWithoutUpdate().set(memoryKey, true);
            }
        }

    }
}

