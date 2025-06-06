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
import org.selkie.kol.ReflectionUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.fs.starfarer.api.campaign.listeners.GateTransitListener;
import poggers.oldlegion.utils.OldLegionStrings;

public class GateJumpTracker implements GateTransitListener {

    String memoryKey = "$oldlegion_domainspace_gate_glitch";

    @Override
    public void reportFleetTransitingGate(CampaignFleetAPI fleet, SectorEntityToken gateFrom, SectorEntityToken gateTo) {
        StarSystemAPI destSys = Global.getSector().getStarSystem(OldLegionStrings.DomainSpaceSysName);
        if (Global.getSector().getMemoryWithoutUpdate().getKeys().contains(memoryKey)) {
            Console.showMessage("If-Statement ONE fired their return");
            return; //Dont trigger multiple times.
        }
        if (!Global.getSector().getMemoryWithoutUpdate().getBoolean("$gaATG_missionCompleted")) {
            Console.showMessage("If-Statement TWO fired their return");
            return; //Prevent it from happening during the story-jump
            }
        if (!fleet.isPlayerFleet() || gateFrom == null || Global.getSector().getClock().getCycle() <= 206 || gateFrom.getContainingLocation() == destSys) {
            Console.showMessage("If-Statement THREE fired their return");
            return;
        }

        Console.showMessage("Before the Math.Random() if-statement");
        if (((int)Math.random()*101) <= 99)                     //(/*true*/ Math.random() <= 0.95f) { //0.05f = 5% chance to trigger.
            Console.showMessage("After the Math.Random() if-statement");
            if (destSys == null) return;
            SectorEntityToken dest = destSys.getEntityById("domain_ops_gate");
            float dist = Misc.getDistanceLY(dest, gateTo);
            if (dist < 12f) {

                //Old Version

				//fleet.getContainingLocation().removeEntity(fleet);
				//dest.getContainingLocation().addEntity(fleet);
				//Global.getSector().setCurrentLocation(dest.getContainingLocation());
				//fleet.setLocation(dest.getLocation().x,
                //dest.getLocation().y);
				//fleet.setNoEngaging(1.0f);
				//fleet.clearAssignments();

                for (EveryFrameScript script : new ArrayList<>(Global.getSector().getScripts())) {
                   if (ReflectionUtils.INSTANCE.hasVariableOfName("untilCanWarpOut", script)) {
                       Global.getSector().removeScript(script);
                   }
                }
                ReflectionUtils.INSTANCE.invoke("setInJumpTransition", fleet, new Object[]{false}, false);

                //Start own Transverse
                Global.getSector().doHyperspaceTransition(fleet, gateFrom, new JumpPointAPI.JumpDestination(dest, ""), 5.0f);

                //Disable VFX on the Gate Itself
                //GateEntityPlugin plugin = (GateEntityPlugin) gateFrom.getCustomPlugin();
                //FaderUtil fader = (FaderUtil) ReflectionUtils.get("beingUsedFader", plugin);
                //plugin.showBeingUsed(0f, 0f);
                //fader.forceOut();

                GateCMD.notifyScanned(dest);
                dest.getMemoryWithoutUpdate().set(GateEntityPlugin.GATE_SCANNED, true);

                Global.getSector().getMemoryWithoutUpdate().set(memoryKey, true);
            }

        }
    }
// }
