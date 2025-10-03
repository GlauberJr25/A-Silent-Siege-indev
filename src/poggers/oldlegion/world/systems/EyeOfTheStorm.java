package poggers.oldlegion.world.systems;

import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.PlanetConditionGenerator;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.terrain.MagneticFieldTerrainPlugin;
import com.fs.starfarer.api.impl.campaign.terrain.PulsarBeamTerrainPlugin;
import com.fs.starfarer.api.impl.campaign.terrain.StarCoronaTerrainPlugin;

import java.awt.*;

public class EyeOfTheStorm {
    public void generate(SectorAPI sector) {
        StarSystemAPI StormsEyeSystem = sector.createStarSystem("Eye of the Storm");
        StormsEyeSystem.getLocation().set(10000,10000);

        StormsEyeSystem.addTag(Tags.THEME_HIDDEN);
        StormsEyeSystem.addTag(Tags.THEME_SPECIAL);
        StormsEyeSystem.addTag(Tags.THEME_UNSAFE);
        StormsEyeSystem.addTag(Tags.NOT_RANDOM_MISSION_TARGET);

        StormsEyeSystem.initNonStarCenter();

        SectorEntityToken beacon = StormsEyeSystem.addCustomEntity("warning_beacon_storm","Warning Beacon", "warning_beacon","domainspecops");
        beacon.setCustomDescriptionId("graveyard_beacon");

        PlanetAPI BajaBlast = StormsEyeSystem.addPlanet("BajaBlast", beacon, "Bajazil", "barren_baja", 0, 200, 0, 0);
        SectorEntityToken BajaMagneticField = StormsEyeSystem.addTerrain("magnetic_field", new MagneticFieldTerrainPlugin.MagneticFieldParams(BajaBlast.getRadius() + 25.0F, (BajaBlast.getRadius() + 75.0F) / 2.0F, BajaBlast, BajaBlast.getRadius() + 15.0F, BajaBlast.getRadius() + 15.0F + 150.0F, new Color(149, 149, 149, 40), 0.5F, new Color[]{new Color(90, 180, 40), new Color(130, 145, 90), new Color(150, 140, 190), new Color(140, 190, 210), new Color(90, 200, 170), new Color(128, 128, 128), new Color(154, 154, 154)}));
        BajaMagneticField.setCircularOrbit(BajaBlast, 0.0F, 0.0F, 100.0F);
        BajaBlast.getSpec().setTexture("graphics/planets/oldlegion_baja_blasted.jpg");
        BajaBlast.getMarket().addCondition("oldlegion_baja_blast");
        BajaBlast.getMarket().addCondition(Conditions.NO_ATMOSPHERE);

        PlanetAPI NeutronStar1 = StormsEyeSystem.addPlanet("Khamsin", beacon, "Khamsin", "star_neutron", 0, 500, 1000, 500);
        PlanetAPI NeutronStar2 = StormsEyeSystem.addPlanet("Mistral", beacon, "Mistral", "star_neutron", 120, 500, 1000, 500);
        PlanetAPI NeutronStar3 = StormsEyeSystem.addPlanet("Jetstream", beacon, "Jetstream", "star_neutron", 240, 500, 1000, 500);
        StormsEyeSystem.setStar(NeutronStar1);
        StormsEyeSystem.setSecondary(NeutronStar2);
        StormsEyeSystem.setTertiary(NeutronStar3);
        StormsEyeSystem.setType(StarSystemGenerator.StarSystemType.TRINARY_2CLOSE);

//        SectorEntityToken NeutronStar1_field = StormsEyeSystem.addTerrain(Terrain.CORONA,
//                new MagneticFieldTerrainPlugin.MagneticFieldParams(
//                        NeutronStar1.getRadius() + 150f, // terrain effect band width
//                        (NeutronStar1.getRadius() + 150f), // terrain effect middle radius
//                        NeutronStar1, // entity that it's around
//                        NeutronStar1.getRadius()-25f, // visual band start
//                        NeutronStar1.getRadius() + 50f + 100f, // visual band end
//                        new Color(149, 149, 149, 50), // base color
//                        0.75f, // probability to spawn aurora sequence, checked once/day when no aurora in progress
//                        new Color(90, 180, 40),
//                        new Color(130, 145, 90),
//                        new Color(165, 110, 145),
//                        new Color(95, 55, 160),
//                        new Color(117, 104, 140), // 45, 0, 130
//                        new Color(128, 128, 128), // 20, 0, 130
//                        new Color(154, 154, 154))); // 10, 0, 150
//        NeutronStar1_field.setCircularOrbit(NeutronStar1, 0, 0, 100);

        SectorEntityToken NeutronStar2_corona = StormsEyeSystem.addTerrain(Terrain.CORONA,
                new StarCoronaTerrainPlugin.CoronaParams(
                        NeutronStar2.getRadius() + 50f, //bandwitdth
                        (NeutronStar2.getRadius() + 50f), //middle radius
                        NeutronStar2, //focus
                        1f, //windburn level
                        2f, //flare probability
                        1f //cr loss multiplier
                ));
        NeutronStar2_corona.setCircularOrbit(NeutronStar2, 0, 0, 100);

        SectorEntityToken NeutronStar1_corona = StormsEyeSystem.addTerrain(Terrain.CORONA,
                new StarCoronaTerrainPlugin.CoronaParams(NeutronStar1.getRadius() + 50f, (NeutronStar1.getRadius() + 50f), NeutronStar1, 1f, 2f, 1f));
        NeutronStar1_corona.setCircularOrbit(NeutronStar1, 0, 0, 100);

        SectorEntityToken NeutronStar3_corona = StormsEyeSystem.addTerrain(Terrain.CORONA,
                new StarCoronaTerrainPlugin.CoronaParams(NeutronStar3.getRadius() + 50f, (NeutronStar3.getRadius() + 50f), NeutronStar3, 1f, 2f, 1f));
        NeutronStar3_corona.setCircularOrbit(NeutronStar3, 0, 0, 100);

        SectorEntityToken NeutronStar1_beam = StormsEyeSystem.addTerrain(Terrain.PULSAR_BEAM,
                new PulsarBeamTerrainPlugin()
        );
        NeutronStar1_beam.setCircularOrbit(NeutronStar1,0,0,100);

        PlanetAPI RandomBaja1 = StormsEyeSystem.addPlanet("baja_gamma", beacon, "Gamma", "barren", 20, 297, 3100, 674);
        PlanetConditionGenerator.generateConditionsForPlanet(RandomBaja1, StarAge.OLD);
        PlanetAPI RandomBaja2 = StormsEyeSystem.addPlanet("baja_beta", beacon, "Beta", "barren", 35, 333, 4800, 845);
        PlanetConditionGenerator.generateConditionsForPlanet(RandomBaja2, StarAge.OLD);
        PlanetAPI RandomBaja3 = StormsEyeSystem.addPlanet("baja_alpha", beacon, "Alpha", "barren", 77, 398, 5650, 1174);
        PlanetConditionGenerator.generateConditionsForPlanet(RandomBaja3, StarAge.OLD);

    }
}
