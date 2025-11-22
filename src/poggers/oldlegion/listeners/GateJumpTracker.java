package poggers.oldlegion.listeners;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.listeners.GateTransitListener;
import com.fs.starfarer.api.impl.campaign.GateEntityPlugin;
import com.fs.starfarer.api.impl.campaign.rulecmd.missions.GateCMD;
import com.fs.starfarer.api.util.Misc;

import java.util.ArrayList;
import java.util.Random;

import poggers.oldlegion.ReflectionUtilities;
import poggers.oldlegion.utils.OldLegionStrings;
import poggers.oldlegion.utils.ReflectionUtils;

public class GateJumpTracker implements GateTransitListener {

    // KoL Compat
    boolean isKoLEnabled = Global.getSettings().getModManager().isModEnabled("knights_of_ludd");
    String memoryKeyKoL = "$kol_nullspace_gate_glitch";
    String memoryKey = "$oldlegion_domainspace_gate_glitch";
    String memoryKeyInterceptChance = "$chanceToIntercept";
    int InterceptChanceInt;
    int UpdatedInterceptChanceInt;

    Random random = new Random();

    @Override
    public void reportFleetTransitingGate(CampaignFleetAPI fleet, SectorEntityToken gateFrom, SectorEntityToken gateTo) {
        StarSystemAPI destSys = Global.getSector().getStarSystem(OldLegionStrings.DomainSpaceSysName);

        Global.getSector().getMemoryWithoutUpdate().set(memoryKeyInterceptChance, 1);
        InterceptChanceInt = Global.getSector().getMemoryWithoutUpdate().getInt(memoryKeyInterceptChance);

        //Dont trigger multiple times.
        if (Global.getSector().getMemoryWithoutUpdate().getKeys().contains(memoryKey)) {
            //Console.showMessage("If-Statement ONE fired their return");
            return;
        }
        // KoL compat
        if (isKoLEnabled) {
            //Dont trigger before KoL triggers their Gate Interceptor.
            if (!Global.getSector().getMemoryWithoutUpdate().getKeys().contains(memoryKeyKoL)) {
                return;
            }
        }
        //Prevent it from happening during the story-jump
        if (!Global.getSector().getMemoryWithoutUpdate().getBoolean("$gaATG_missionCompleted")) {
            return;
            }

        boolean isNotPlayerFleet = !fleet.isPlayerFleet();
        boolean sendingGateNull = gateFrom == null;
        boolean tooLowCycle = Global.getSector().getClock().getCycle() < 206;
        boolean sendingGateIsDomain = gateFrom.getContainingLocation() == destSys;
        if (isNotPlayerFleet || sendingGateNull || tooLowCycle || sendingGateIsDomain) {
            return;
        }
        if (random.nextInt(101) <= InterceptChanceInt) { // random.nextInt(101) <= InterceptChanceInt
            if (destSys == null) return;
            SectorEntityToken dest = destSys.getEntityById("domain_ops_gate");
            float dist = Misc.getDistanceLY(dest, gateTo);

            for (EveryFrameScript script : new ArrayList<>(Global.getSector().getScripts())) {
                if (ReflectionUtils.hasVariableOfName("untilCanWarpOut", script)) {
                    Global.getSector().removeScript(script);
                }
            }
            try {
                // Uses KoL reflection, couldn't find a non-kotlin version
                ReflectionUtilities.INSTANCE.invoke("setInJumpTransition", fleet, new Object[]{false}, false);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
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