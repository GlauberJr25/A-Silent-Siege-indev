package poggers.oldlegion.campaign.submarkets;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.SubmarketPlugin.TransferAction;
import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.impl.campaign.DModManager;
import com.fs.starfarer.api.impl.campaign.fleets.DefaultFleetInflater;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.submarkets.BaseSubmarketPlugin;
import com.fs.starfarer.api.impl.campaign.submarkets.MilitarySubmarketPlugin;
import com.fs.starfarer.api.util.Misc;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class OldHangar extends BaseSubmarketPlugin {
    public static RepLevel MIN_REPUTATION = RepLevel.COOPERATIVE;
    Random random = new Random();

    public float getTariff() {
        return 0.05F;
    }
    public void init(SubmarketAPI submarket) {
        super.init(submarket);
    }

    public void updateCargoPrePlayerInteraction() {
        float seconds = Global.getSector().getClock().convertToSeconds(sinceLastCargoUpdate);
        sinceLastCargoUpdate = 0.0F;
        if (okToUpdateShipsAndWeapons()) {
            sinceSWUpdate = 0f;
            pruneWeapons(0f);
            int weapons = 7 + Math.max(0, market.getSize() - 1) * 2;
            int fighters = 2 + Math.max(0, market.getSize() - 3);
            addWeapons(weapons, weapons + 2, 3, "domainspecops");
            addFighters(fighters, fighters + 2, 3, "domainspecops");
            getCargo().getMothballedShips().clear();

            FactionDoctrineAPI doctrineOverride = submarket.getFaction().getDoctrine().clone();
            doctrineOverride.setShipQuality(2);

//            addShips("domainspecops", 200.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, (Float)null, 0.0F, (FactionAPI.ShipPickMode)null, doctrineOverride);
            addShip("onslaught_mk1_Ancient", true, 0);
            addShip("onslaught_mk1_Ancient", true, 0);
            addShip("onslaught_mk1_Ancient", true, 0);
            addShip("onslaught_mk1_Ancient", true, 0);
            addShip("onslaught_mk1_Ancient", true, 0);
            addShip("onslaught_mk1_Ancient", true, 0);
            addShip("onslaught_mk1_Ancient", true, 0);
            addShip("onslaught_mk1_Ancient", true, 0);
            addShip("onslaught_mk1_Ancient", true, 0);
            addShip("onslaught_mk1_Ancient", true, 0);
            addShip("onslaught_mk1_Ancient", true, 0);
            addShip("onslaught_mk1_Ancient", true, 0);
            addHullMods(4, 2 + itemGenRandom.nextInt(4), "domainspecops");
        }
        getCargo().sort();
    }
    protected FleetMemberAPI addShip(String variantOrWingId, boolean withDmods, float quality) {
        FleetMemberAPI member = null;
        if (variantOrWingId.endsWith("_wing")) {
            member = Global.getFactory().createFleetMember(FleetMemberType.FIGHTER_WING, variantOrWingId);
        } else {
            member = Global.getFactory().createFleetMember(FleetMemberType.SHIP, variantOrWingId);
        }
        if (withDmods) {
            int addDmods = 1 + random.nextInt(3);
            DModManager.setDHull(member.getVariant());
            DModManager.addDMods(member, true, addDmods, itemGenRandom);
        }
        member.getRepairTracker().setMothballed(true);
        member.getRepairTracker().setCR(0.5f);
        getCargo().getMothballedShips().addFleetMember(member);
        return member;
    }
    @Override
    public boolean isEnabled(CoreUIAPI ui)
    {
        RepLevel level = submarket.getFaction().getRelationshipLevel(Global.getSector().getFaction(Factions.PLAYER));
        boolean hangarPermission = Global.getSector().getCharacterData().getMemoryWithoutUpdate().getBoolean("$OldlegionHangarSubmarketPermission");
        return level.isAtWorst(MIN_REPUTATION) && hangarPermission;
    }
    protected boolean requiresCommission(RepLevel req) {return false;}

    public boolean isParticipatesInEconomy() {
        return false;
    }
}
