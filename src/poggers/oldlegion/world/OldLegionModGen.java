package poggers.oldlegion.world;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.Script;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.RepLevel;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.StarSystemAPI;

import poggers.oldlegion.world.systems.EyeOfTheStorm;
import poggers.oldlegion.world.systems.MySystemOne;
import poggers.oldlegion.world.systems.Xaphar;

public class OldLegionModGen {
    public void generate(SectorAPI sector) { initFactionRelationships(sector); }

    public static void trySpawnOutpost(SectorAPI sector) {
        MemoryAPI sector_mem = sector.getMemoryWithoutUpdate();
        StarSystemAPI outpost = sector.getStarSystem("Nataruk");
        if (outpost == null) {
            (new MySystemOne()).generate(sector);
        }
    }
    public static void trySpawnStormEye(SectorAPI sector) {
        MemoryAPI sector_mem = sector.getMemoryWithoutUpdate();
        StarSystemAPI StormEye = sector.getStarSystem("Eye of the Storm");
        if (StormEye == null) {
           (new EyeOfTheStorm()).generate(sector);
        }
    }

    public static void trySpawnXaphar(SectorAPI sector) {
        MemoryAPI sector_mem = sector.getMemoryWithoutUpdate();
        StarSystemAPI XapharSys = sector.getStarSystem("Xaphar");
        if (XapharSys == null) {
            (new Xaphar()).generate(sector);
        }
    }

    public static void initFactionRelationships(SectorAPI sector) {
        FactionAPI domint = sector.getFaction("domainspecops");
        domint.setRelationship("independent", RepLevel.SUSPICIOUS);
        domint.setRelationship("player", RepLevel.SUSPICIOUS);
        domint.setRelationship("hegemony", RepLevel.SUSPICIOUS);
        domint.setRelationship("luddic_church", RepLevel.HOSTILE);
        domint.setRelationship("tritachyon", RepLevel.INHOSPITABLE);
        domint.setRelationship("pirates", RepLevel.HOSTILE);
        domint.setRelationship("luddic_path", RepLevel.VENGEFUL);
        domint.setRelationship("sindrian_diktat", RepLevel.VENGEFUL);
        domint.setRelationship("derelict", RepLevel.COOPERATIVE);
        domint.setRelationship("remnant", RepLevel.VENGEFUL);
        domint.setRelationship("omega", RepLevel.NEUTRAL);
        domint.setRelationship("tahlan_legioinfernalis", RepLevel.VENGEFUL);
    }
}
