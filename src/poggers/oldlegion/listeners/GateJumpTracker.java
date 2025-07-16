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
import java.util.Random;

import poggers.oldlegion.ReflectionUtilities;
import poggers.oldlegion.utils.OldLegionStrings;

public class GateJumpTracker implements GateTransitListener {

    boolean isKoLEnabled = Global.getSettings().getModManager().isModEnabled("knights_of_ludd");

    String memoryKey = "$oldlegion_domainspace_gate_glitch";

    String memoryKeyKoL = "$kol_nullspace_gate_glitch";

    String memoryKeyInterceptChance = "$chanceToIntercept";

    int InterceptChanceInt;

    int UpdatedInterceptChanceInt;

    Random random = new Random();

    @Override
    public void reportFleetTransitingGate(CampaignFleetAPI fleet, SectorEntityToken gateFrom, SectorEntityToken gateTo) {
        StarSystemAPI destSys = Global.getSector().getStarSystem(OldLegionStrings.DomainSpaceSysName);
        Global.getSector().getMemoryWithoutUpdate().set(memoryKeyInterceptChance, 1);
        InterceptChanceInt = Global.getSector().getMemoryWithoutUpdate().getInt(memoryKeyInterceptChance);
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
            return; //Prevent it from happening during the story-jump
            }

        boolean isNotPlayerFleet = !fleet.isPlayerFleet();
        boolean sendingGateNull = gateFrom == null;
        boolean tooLowCycle = Global.getSector().getClock().getCycle() < 206;
        boolean sendingGateIsDomain = gateFrom.getContainingLocation() == destSys;
        if (isNotPlayerFleet || sendingGateNull || tooLowCycle || sendingGateIsDomain) {
            return;
        }
        if (random.nextInt(101) <= InterceptChanceInt) {
            if (destSys == null) return;
            SectorEntityToken dest = destSys.getEntityById("domain_ops_gate");
            float dist = Misc.getDistanceLY(dest, gateTo);

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

        } else {
            UpdatedInterceptChanceInt = random.nextInt(6) + InterceptChanceInt;
            Global.getSector().getMemoryWithoutUpdate().set(memoryKeyInterceptChance, UpdatedInterceptChanceInt);
        }

    }
}