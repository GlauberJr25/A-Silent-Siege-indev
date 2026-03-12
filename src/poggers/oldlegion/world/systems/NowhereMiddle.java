package poggers.oldlegion.world.systems;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.Script;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.procgen.*;
import com.fs.starfarer.api.impl.campaign.rulecmd.salvage.special.ShipRecoverySpecial;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.impl.campaign.terrain.*;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.impl.campaign.terrain.DebrisFieldTerrainPlugin.DebrisFieldParams;
import com.fs.starfarer.api.util.Misc;
import exerelin.utilities.StringHelper;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.VectorUtils;
import org.lwjgl.util.vector.Vector2f;

public class NowhereMiddle {
    public static String NASCENT_WELL_KEY = "$prismAI_nascent_well";

    public void generate(SectorAPI sector) {
        StarSystemAPI Nowhere = sector.createStarSystem("Middle of Nowhere");
        Nowhere.setName("Middle of Nowhere"); // to get rid of "Star System" at the end of the name
        Nowhere.setType(StarSystemGenerator.StarSystemType.DEEP_SPACE);
        Nowhere.getLocation().set(-40000, 15000);
        Nowhere.initNonStarCenter();
        LocationAPI hyper = Global.getSector().getHyperspace();

        SectorEntityToken nowhere_Nebula = Misc.addNebulaFromPNG("graphics/terrain/nebula.png",
                0,0, //center of nebula
                Nowhere, //location to add to
                "terrain", "nebula_blue", //"nebula_blue", //texture to use, uses xxx_map for map
                4,4, StarAge.YOUNG //number of cells
        );
        Nowhere.addTag(Tags.THEME_UNSAFE);
        Nowhere.addTag(Tags.THEME_HIDDEN);
        Nowhere.addTag(Tags.THEME_SPECIAL);
        Nowhere.addTag(Tags.NOT_RANDOM_MISSION_TARGET);

        HyperspaceTerrainPlugin hyperTerrain = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin();
        NebulaEditor editor = new NebulaEditor(hyperTerrain);
        editor.clearArc(Nowhere.getLocation().x, Nowhere.getLocation().y, 0, 100, 0, 360f);

        SectorEntityToken TestShunt = Nowhere.addCustomEntity("TestHypershuntD","Destroyed Hypershunt","oldlegion_hypershunt","domainspecops");
        TestShunt.setSensorProfile(1.0F);
        TestShunt.setDiscoverable(true);
        TestShunt.getDetectedRangeMod().modifyFlat("gen", 5000.0F);
        TestShunt.setCustomDescriptionId("destroyed_hyper_shunt");
        TestShunt.getMemoryWithoutUpdate().set("$PAshunt", true);

        DebrisFieldParams DebrisF1 = new DebrisFieldParams(
                1100f, // field radius - should not go above 1000 for performance reasons
                1.5f, // density, visual - affects number of debris pieces
                10000000f, // duration in days
                0f); // days the field will keep generating glowing pieces
        DebrisF1.source = DebrisFieldTerrainPlugin.DebrisFieldSource.MIXED;
        DebrisF1.baseSalvageXP = 250; // base XP for scavenging in field
        SectorEntityToken debrisNextToShunt = Misc.addDebrisField(Nowhere, DebrisF1, StarSystemGenerator.random);
        debrisNextToShunt.setSensorProfile(1000f);
        debrisNextToShunt.setDiscoverable(true);
        debrisNextToShunt.setCircularOrbit(TestShunt, 0f, 0f, 250f);
        debrisNextToShunt.setId("domain_debrisF1Nowhere");

        Nowhere.generateAnchorIfNeeded();

        NascentGravityWellAPI well = Global.getSector().createNascentGravityWell(TestShunt, 50f);
        well.addTag(Tags.NO_ENTITY_TOOLTIP);
        well.setColorOverride(new Color(125, 50, 255));
        hyper.addEntity(well);
        well.autoUpdateHyperLocationBasedOnInSystemEntityAtRadius(TestShunt, 0);
        //can spawn exactly under a star and be invisible to the user. Needs fixing. Whenever its not midnight. Ugh. Sorry everyone.

        Global.getSector().getMemoryWithoutUpdate().set(NASCENT_WELL_KEY, well);
    }
    public static NascentGravityWellAPI getWell() {
        return (NascentGravityWellAPI) Global.getSector().getMemoryWithoutUpdate().get(NASCENT_WELL_KEY);
    }
}
