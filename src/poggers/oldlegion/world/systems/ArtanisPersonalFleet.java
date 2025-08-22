package poggers.oldlegion.world.systems;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.fleets.PersonalFleetScript;
import com.fs.starfarer.api.impl.campaign.ids.FleetTypes;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;
import com.fs.starfarer.api.impl.campaign.missions.FleetCreatorMission;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithTriggers.FleetQuality;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithTriggers.FleetSize;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithTriggers.OfficerNum;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithTriggers.OfficerQuality;
import com.fs.starfarer.api.impl.campaign.missions.hub.MissionFleetAutoDespawn;
import com.fs.starfarer.api.util.Misc;

import poggers.oldlegion.utils.OldLegionIDs;
import poggers.oldlegion.utils.OldLegionPeople;

import org.lwjgl.util.vector.Vector2f;

public class ArtanisPersonalFleet extends PersonalFleetScript {

    public ArtanisPersonalFleet() {
        super(OldLegionPeople.ARTANIS);
        setMinRespawnDelayDays(20F);
        setMaxRespawnDelayDays(30F);
    }


    protected MarketAPI getSourceMarket() {
        return Global.getSector().getEconomy().getMarket("Hunhow_market");
    }


    public CampaignFleetAPI spawnFleet() {
        MarketAPI base = getSourceMarket();
        SectorEntityToken outpost = base.getStarSystem().getEntityById("Hunhow");

        if (outpost == null) { return null; }

        FleetCreatorMission m = new FleetCreatorMission(this.random);
        m.beginFleet();

        Vector2f loc = outpost.getLocationInHyperspace();

        FactionDoctrineAPI doctrine = Global.getSector().getFaction(OldLegionIDs.DOMINT).getDoctrine().clone();
        doctrine.setShipSize(5);
        doctrine.setCombatFreighterCombatUseFraction(0.65f);
        m.triggerCreateFleet(FleetSize.MAXIMUM, FleetQuality.SMOD_2, OldLegionIDs.DOMINT, FleetTypes.PATROL_LARGE, loc);
        m.triggerFleetSetFlagship("domain_capital_test");
        m.triggerAddShips("onslaught_di_Elite");
        m.triggerSetFleetOfficers(OfficerNum.DEFAULT, OfficerQuality.HIGHER);
        m.triggerSetFleetCommander(getPerson());
        m.triggerSetFleetFaction("domainspecops");
        m.triggerSetPatrol();
        m.triggerGetFleetParams().doctrineOverride = doctrine;
        //m.triggerFleetSetNoFactionInName();
        m.triggerPatrolAllowTransponderOff();
        m.triggerSetFleetMemoryValue(MemFlags.MEMORY_KEY_NO_JUMP, true);
        m.triggerSetFleetMemoryValue(OldLegionIDs.ARTANIS_FLEET, true);
        if (!Global.getSector().getMemoryWithoutUpdate().contains("$oldlegion_HHtOffDone")) {
            m.triggerSetFleetHasslePlayer("oldlegion_HHtOff");
        }
        m.triggerFleetSetName("Artanis's Astral Astartes");

        m.triggerOrderFleetPatrol(outpost.getStarSystem().getEntityById("domain_ops_gate"));
        m.triggerFleetSetPatrolLeashRange(1);

        CampaignFleetAPI fleet = m.createFleet();

        int shipIndex = 1;
        if (Global.getSector().getMemoryWithoutUpdate().contains(OldLegionIDs.ARTANIS_FLEET + "_timesKilled")) {
            shipIndex += Global.getSector().getMemoryWithoutUpdate().getInt(OldLegionIDs.ARTANIS_FLEET + "_timesKilled");
        }
        if (shipIndex == 14) {
            shipIndex = 15;
        }
        String shipName = "DSS Bitter Venom";
        if (shipIndex > 1) {
            shipName += " " + Global.getSettings().getRoman(shipIndex);
        }
        fleet.getFleetData().ensureHasFlagship();
        fleet.getFlagship().setShipName(shipName);
        fleet.getMemoryWithoutUpdate().set(MemFlags.MEMORY_KEY_SOURCE_MARKET, "Hunhow_market");
        fleet.getMemoryWithoutUpdate().set("$oldlegion_finnish", true);
        fleet.removeScriptsOfClass(MissionFleetAutoDespawn.class);
        outpost.getContainingLocation().addEntity(fleet);
        fleet.setLocation(outpost.getLocation().x, outpost.getLocation().y);
        fleet.setFacing((float) random.nextFloat() * 360f);

        return fleet;
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
        if (reason == CampaignEventListener.FleetDespawnReason.DESTROYED_BY_BATTLE) {
            if (!Global.getSector().getMemoryWithoutUpdate().contains(OldLegionIDs.ARTANIS_FLEET + "_timesKilled")) {
                Global.getSector().getMemoryWithoutUpdate().set(OldLegionIDs.ARTANIS_FLEET + "_timesKilled", 1);
            } else {
                int timesKilled = Global.getSector().getMemoryWithoutUpdate().getInt(OldLegionIDs.ARTANIS_FLEET + "_timesKilled");
                Global.getSector().getMemoryWithoutUpdate().set(OldLegionIDs.ARTANIS_FLEET + "_timesKilled", timesKilled + 1);
            }
        }

    }


    public boolean canSpawnFleetNow() {
            MarketAPI outpost = Global.getSector().getEconomy().getMarket("Hunhow_market");
            return outpost != null;
    }

    public boolean shouldScriptBeRemoved() {return false;}
}

