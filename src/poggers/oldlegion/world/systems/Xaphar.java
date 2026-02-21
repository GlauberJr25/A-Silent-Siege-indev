package poggers.oldlegion.world.systems;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.Script;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.rulecmd.salvage.special.ShipRecoverySpecial;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.impl.campaign.terrain.AsteroidFieldTerrainPlugin;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.impl.campaign.terrain.DebrisFieldTerrainPlugin;
import com.fs.starfarer.api.impl.campaign.terrain.DebrisFieldTerrainPlugin.DebrisFieldParams;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.util.Misc;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.VectorUtils;
import org.lwjgl.util.vector.Vector2f;


public class Xaphar {
    public void generate(SectorAPI sector) {
        StarSystemAPI Xaphar = sector.createStarSystem("Xaphar");
        Xaphar.getLocation().set(10000, 10000);

        Xaphar.addTag(Tags.THEME_HIDDEN);
        Xaphar.addTag(Tags.THEME_SPECIAL);
        Xaphar.addTag(Tags.NOT_RANDOM_MISSION_TARGET);

        PlanetAPI argonianStar = Xaphar.initStar("Argonian", "star_browndwarf", 450.0F, -46450.0F, 8760.0F, 200.0F);
        Xaphar.setLightColor(new Color(239, 155, 128)); // light color in entire system, affects all entities

        SectorEntityToken argonAF1 = Xaphar.addTerrain(Terrain.ASTEROID_FIELD,
                new AsteroidFieldTerrainPlugin.AsteroidFieldParams(
                        450f, // min radius
                        555f, // max radius
                        36, // min asteroid count
                        48, // max asteroid count
                        8f, // min asteroid radius
                        14f, // max asteroid radius
                        "Asteroid Field")); // null for default name
        argonAF1.setCircularOrbit(argonianStar, 130, 3097f, 262);

        SectorEntityToken argonAF2 = Xaphar.addTerrain(Terrain.ASTEROID_FIELD,
                new AsteroidFieldTerrainPlugin.AsteroidFieldParams(397f,468f, 24, 36, 14f, 18f, "Asteroid Field"));
        argonAF2.setCircularOrbit(argonianStar, 223, 4136f, 464);

        SectorEntityToken argonAF3 = Xaphar.addTerrain(Terrain.ASTEROID_FIELD,
                new AsteroidFieldTerrainPlugin.AsteroidFieldParams(321f,421f, 14, 20, 24f, 30f, "Asteroid Field"));
        argonAF3.setCircularOrbit(argonianStar, 307, 5243f, 651);

        Xaphar.addAsteroidBelt(argonianStar, 150, 2500.0F, 515.0F, 351.0F, 400.0F, "asteroid_belt", "Outer Ring");
        Xaphar.addRingBand(argonianStar, "misc", "rings_asteroids0", 512.0F, 3, Color.white, 512.0F, 2500.0F, 350.0F);

//        SectorEntityToken TestShunt = Xaphar.addCustomEntity("TestHypershuntD","Destroyed Hypershunt","oldlegion_hypershunt","domainspecops");
//        TestShunt.setCircularOrbit(argonianStar,2,3000,300);
//        TestShunt.setSensorProfile(1.0F);
//        TestShunt.setDiscoverable(true);
//        TestShunt.getDetectedRangeMod().modifyFlat("gen", 5000.0F);

//        DebrisFieldParams DebrisF1 = new DebrisFieldParams(
//                800f, // field radius - should not go above 1000 for performance reasons
//                1.5f, // density, visual - affects number of debris pieces
//                10000000f, // duration in days
//                0f); // days the field will keep generating glowing pieces
//        DebrisF1.source = DebrisFieldTerrainPlugin.DebrisFieldSource.MIXED;
//        DebrisF1.baseSalvageXP = 250; // base XP for scavenging in field
//        SectorEntityToken debrisNextToShunt = Misc.addDebrisField(Xaphar, DebrisF1, StarSystemGenerator.random);
//        debrisNextToShunt.setSensorProfile(1000f);
//        debrisNextToShunt.setDiscoverable(true);
//        debrisNextToShunt.setCircularOrbit(TestShunt, 0f, 0f, 250f);
//        debrisNextToShunt.setId("domain_debrisF1Xaphar");

        DebrisFieldParams DebrisF2 = new DebrisFieldParams(600f, 1.5f, 10000000f, 0f);
        DebrisF2.source = DebrisFieldTerrainPlugin.DebrisFieldSource.MIXED;
        DebrisF2.baseSalvageXP = 250; // base XP for scavenging in field
        SectorEntityToken debrisField2 = Misc.addDebrisField(Xaphar, DebrisF2, StarSystemGenerator.random);
        debrisField2.setSensorProfile(800f);
        debrisField2.setDiscoverable(true);
        debrisField2.setCircularOrbit(argonianStar, 80f, 1500f, 90000f);
        debrisField2.setId("domain_debrisF2Xaphar");

        DebrisFieldParams DebrisF3 = new DebrisFieldParams(500f, 1.5f, 10000000f, 0f);
        DebrisF3.source = DebrisFieldTerrainPlugin.DebrisFieldSource.MIXED;
        DebrisF3.baseSalvageXP = 250; // base XP for scavenging in field
        SectorEntityToken debrisField3 = Misc.addDebrisField(Xaphar, DebrisF3, StarSystemGenerator.random);
        debrisField3.setSensorProfile(700f);
        debrisField3.setDiscoverable(true);
        debrisField3.setCircularOrbit(argonianStar, 70f, 3500f, 90000f);
        debrisField3.setId("domain_debrisF2Xaphar");

        DebrisFieldParams DebrisF4 = new DebrisFieldParams(400f, 1.5f, 10000000f, 0f);
        DebrisF4.source = DebrisFieldTerrainPlugin.DebrisFieldSource.MIXED;
        DebrisF4.baseSalvageXP = 250; // base XP for scavenging in field
        SectorEntityToken debrisField4= Misc.addDebrisField(Xaphar, DebrisF4, StarSystemGenerator.random);
        debrisField4.setSensorProfile(600f);
        debrisField4.setDiscoverable(true);
        debrisField4.setCircularOrbit(argonianStar, 59f, 5500f, 90000f);
        debrisField4.setId("domain_debrisF2Xaphar");

        Xaphar.autogenerateHyperspaceJumpPoints(true, true);
    }
}
