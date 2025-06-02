package poggers.oldlegion.world;

import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.RepLevel;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.FleetAssignment;
import com.fs.starfarer.api.campaign.LocationAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;

import poggers.oldlegion.world.systems.MySystemOne;

public class OldLegionModGen {
    public void generate(SectorAPI sector) {
        new MySystemOne().generate(sector);
    }

    //public static void initFactionRelationships(SectorAPI sector) {
    //    FactionAPI hegemony = sector.getFaction(Factions.HEGEMONY);
    //   FactionAPI tritachyon = sector.getFaction(Factions.TRITACHYON);
    //   FactionAPI pirates = sector.getFaction(Factions.PIRATES);
    //   FactionAPI kol = sector.getFaction(Factions.KOL);
    //   FactionAPI church = sector.getFaction(Factions.LUDDIC_CHURCH);
    //   FactionAPI path = sector.getFaction(Factions.LUDDIC_PATH);
    //   FactionAPI league = sector.getFaction(Factions.PERSEAN);
    //  FactionAPI myfaction= sector.getFaction("myfaction");

    // myfaction.setRelationship(path.getId(), RepLevel.HOSTILE);
    // myfaction.setRelationship(hegemony.getId(), RepLevel.SUSPICIOUS);
    // myfaction.setRelationship(pirates.getId(), RepLevel.HOSTILE);
    // myfaction.setRelationship(tritachyon.getId(), RepLevel.SUSPICIOUS);
    // myfaction.setRelationship(church.getId(), RepLevel.SUSPICIOUS);
    // myfaction.setRelationship(kol.getId(), RepLevel.SUSPICIOUS);
    // myfaction.setRelationship(league.getId(), RepLevel.SUSPICIOUS);

   // }
}
