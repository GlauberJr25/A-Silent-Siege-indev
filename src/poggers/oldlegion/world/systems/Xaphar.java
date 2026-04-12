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

        PlanetAPI argonianStar = Xaphar.initStar("Argonian", "star_browndwarf", 450.0F, -46450.0F, 10760.0F, 200.0F);
        Xaphar.setLightColor(new Color(239, 155, 128)); // light color in entire system, affects all entities

        HyperspaceTerrainPlugin plugin = (HyperspaceTerrainPlugin)Misc.getHyperspaceTerrain().getPlugin();
        NebulaEditor editor = new NebulaEditor(plugin);
        float minRadius = plugin.getTileSize() * 2.0F;
        float radius = Xaphar.getMaxRadiusInHyperspace() * 3.0F;
        editor.clearArc(Xaphar.getLocation().x, Xaphar.getLocation().y, 0.0F, radius + minRadius * 0.65F, 0.0F, 360.0F);
        editor.clearArc(Xaphar.getLocation().x, Xaphar.getLocation().y, 0.0F, radius + minRadius, 0.0F, 360.0F, 0.25F);

        StarSystemGenerator.addSystemwideNebula(Xaphar, StarAge.YOUNG);

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
        debrisField3.setId("domain_debrisF3Xaphar");

        DebrisFieldParams DebrisF4 = new DebrisFieldParams(400f, 1.5f, 10000000f, 0f);
        DebrisF4.source = DebrisFieldTerrainPlugin.DebrisFieldSource.MIXED;
        DebrisF4.baseSalvageXP = 250; // base XP for scavenging in field
        SectorEntityToken debrisField4= Misc.addDebrisField(Xaphar, DebrisF4, StarSystemGenerator.random);
        debrisField4.setSensorProfile(600f);
        debrisField4.setDiscoverable(true);
        debrisField4.setCircularOrbit(argonianStar, 59f, 5500f, 90000f);
        debrisField4.setId("domain_debrisF4Xaphar");

        DebrisFieldParams DebrisF5 = new DebrisFieldParams(350f, 1.5f, 10000000f, 0f);
        DebrisF5.source = DebrisFieldTerrainPlugin.DebrisFieldSource.MIXED;
        DebrisF5.baseSalvageXP = 250; // base XP for scavenging in field
        SectorEntityToken debrisField5= Misc.addDebrisField(Xaphar, DebrisF5, StarSystemGenerator.random);
        debrisField5.setSensorProfile(500f);
        debrisField5.setDiscoverable(true);
        debrisField5.setCircularOrbit(argonianStar, 51f, 7500f, 90000f);
        debrisField5.setId("domain_debrisF5Xaphar");

        //testing for another stuff, please disregard
//        PlanetAPI RandomBaja1 = Xaphar.addPlanet("baja_gamma4", argonianStar, "Gamma", "barren", 20, 297, 10100, 674);
//        PlanetConditionGenerator.generateConditionsForPlanet(RandomBaja1, StarAge.OLD);
//
//        DebrisFieldParams DebrisF6 = new DebrisFieldParams(350f, 1.5f, 10000000f, 0f);
//        DebrisF6.source = DebrisFieldTerrainPlugin.DebrisFieldSource.MIXED;
//        DebrisF6.baseSalvageXP = 250; // base XP for scavenging in field
//        SectorEntityToken debrisField6= Misc.addDebrisField(Xaphar, DebrisF4, StarSystemGenerator.random);
//        debrisField6.setSensorProfile(500f);
//        debrisField6.setDiscoverable(true);
//        debrisField6.setCircularOrbit(RandomBaja1, 0f, 1000f, 360f);
//        debrisField6.setId("domain_debrisF6Xaphar");
//
//        DebrisFieldParams DebrisF7 = new DebrisFieldParams(350f, 1.5f, 10000000f, 0f);
//        DebrisF7.source = DebrisFieldTerrainPlugin.DebrisFieldSource.MIXED;
//        DebrisF7.baseSalvageXP = 250; // base XP for scavenging in field
//        SectorEntityToken debrisField7= Misc.addDebrisField(Xaphar, DebrisF4, StarSystemGenerator.random);
//        debrisField7.setSensorProfile(500f);
//        debrisField7.setDiscoverable(true);
//        debrisField7.setCircularOrbit(RandomBaja1, 0f, 2000f, 360f);
//        debrisField7.setId("domain_debrisF7Xaphar");

//        JumpPointAPI jumpPoint1 = Global.getFactory().createJumpPoint("fringe_jump", "Fringe System Jump");
//        jumpPoint1.setCircularOrbit(Xaphar.getEntityById("Argonian"), 2, 6103f, 4000f);
//        jumpPoint1.setStandardWormholeToHyperspaceVisual();
//        Xaphar.addEntity(jumpPoint1);

       Xaphar.autogenerateHyperspaceJumpPoints(false, true);
    }
}
