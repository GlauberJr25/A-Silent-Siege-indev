package poggers.oldlegion.world.talandar;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.BattleAPI;
import com.fs.starfarer.api.campaign.CampaignEventListener;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.FactionDoctrineAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.CampaignEventListener.FleetDespawnReason;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.fleets.PersonalFleetScript;
import com.fs.starfarer.api.impl.campaign.missions.FleetCreatorMission;
import com.fs.starfarer.api.impl.campaign.missions.hub.MissionFleetAutoDespawn;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithTriggers.FleetQuality;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithTriggers.FleetSize;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithTriggers.OfficerNum;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithTriggers.OfficerQuality;
import poggers.oldlegion.utils.oldlegion_people;
import com.fs.starfarer.api.util.Misc;
import org.lwjgl.util.vector.Vector2f;


public class OldLegionPersonalFleetZeratul extends PersonalFleetScript {
    public OldLegionPersonalFleetZeratul() {
        super(oldlegion_people.ZERATUL);
        this.setMinRespawnDelayDays(5.0F);
        this.setMaxRespawnDelayDays(10.0F);
    }

    protected MarketAPI getSourceMarket() {
        return Global.getSector().getEconomy().getMarket("Nataruk");
    }

    public CampaignFleetAPI spawnFleet() {
        MarketAPI holdout = this.getSourceMarket();
        SectorEntityToken hallowhall = holdout.getStarSystem().getEntityById("Hunhow's Fall");
        if (hallowhall == null) {
            return null;
        } else {
            FleetCreatorMission m = new FleetCreatorMission(this.random);
            m.beginFleet();
            Vector2f loc = holdout.getLocationInHyperspace();
            FactionDoctrineAPI doctrine = Global.getSector().getFaction("domainspecops").getDoctrine().clone();
            doctrine.setShipSize(5);
            doctrine.setCombatFreighterCombatUseFraction(0.65F);
            m.triggerCreateFleet(FleetSize.VERY_LARGE, FleetQuality.DEFAULT, "domainspecops", "patrolLarge", loc);
            m.triggerFleetSetFlagship("domain_capital_test");
            m.triggerSetFleetOfficers(OfficerNum.DEFAULT, OfficerQuality.HIGHER);
            m.triggerSetFleetCommander(this.getPerson());
            m.triggerSetPatrol();
            m.triggerGetFleetParams().doctrineOverride = doctrine;
            m.triggerSetFleetMemoryValue("$oldlegion_zeratulFleet", true);
            m.triggerFleetSetName("Zeratul's Ship Devourers");
            m.triggerOrderFleetPatrol(holdout.getStarSystem());
            CampaignFleetAPI fleet = m.createFleet();
            int shipIndex = 1;
            if (Global.getSector().getMemoryWithoutUpdate().contains("$oldlegion_zeratulFleet_timesKilled")) {
                shipIndex += Global.getSector().getMemoryWithoutUpdate().getInt("$oldlegion_zeratulFleet_timesKilled");
            }

            if (shipIndex == 14) {
                shipIndex = 15;
            }

            String shipName = "DSS Bitter Water";
            if (shipIndex > 1) {
                shipName = shipName + " " + Global.getSettings().getRoman(shipIndex);
            }

            fleet.getFleetData().ensureHasFlagship();
            fleet.getFlagship().setShipName(shipName);
            fleet.getMemoryWithoutUpdate().set("$sourceMarket", "Nataruk");
            fleet.removeScriptsOfClass(MissionFleetAutoDespawn.class);
            holdout.getContainingLocation().addEntity(fleet);
            fleet.setLocation(holdout.getPrimaryEntity().getLocation().x, holdout.getPrimaryEntity().getLocation().y);
            fleet.setFacing(this.random.nextFloat() * 360.0F);
            return fleet;
        }


    }

    public void reportBattleOccurred(CampaignFleetAPI fleet, CampaignFleetAPI primaryWinner, BattleAPI battle) {
        if (fleet != null) {
            if (fleet.getFlagship() == null || fleet.getFlagship().getCaptain() != this.getPerson()) {
                Misc.giveStandardReturnToSourceAssignments(fleet);
            }
        }
    }

    public void reportFleetDespawnedToListener(CampaignFleetAPI fleet, CampaignEventListener.FleetDespawnReason reason, Object param) {
        super.reportFleetDespawnedToListener(fleet, reason, param);
        if (reason == FleetDespawnReason.DESTROYED_BY_BATTLE) {
            if (!Global.getSector().getMemoryWithoutUpdate().contains("$oldlegion_zeratulFleet_timesKilled")) {
                Global.getSector().getMemoryWithoutUpdate().set("$oldlegion_zeratulFleet_timesKilled", 1);
            } else {
                int timesKilled = Global.getSector().getMemoryWithoutUpdate().getInt("$oldlegion_zeratulFleet_timesKilled");
                Global.getSector().getMemoryWithoutUpdate().set("$oldlegion_zeratulFleet_timesKilled", timesKilled + 1);
            }
        }

    }

    public boolean canSpawnFleetNow() {
        MarketAPI holdout = Global.getSector().getEconomy().getMarket("Nataruk");
        return holdout != null;
    }

    public boolean shouldScriptBeRemoved() {
        return false;
    }
}
