package poggers.oldlegion.world.systems;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.Script;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.rulecmd.salvage.special.ShipRecoverySpecial;
import com.fs.starfarer.api.impl.campaign.terrain.AsteroidFieldTerrainPlugin;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.impl.campaign.terrain.DebrisFieldTerrainPlugin;
import com.fs.starfarer.api.impl.campaign.terrain.DebrisFieldTerrainPlugin.DebrisFieldParams;

import com.fs.starfarer.api.impl.campaign.terrain.EventHorizonPlugin;
import com.fs.starfarer.api.impl.campaign.terrain.MagneticFieldTerrainPlugin;
import com.fs.starfarer.api.util.Misc;
import org.apache.log4j.Logger;

import org.lazywizard.lazylib.MathUtils;
import poggers.oldlegion.scripts.OldLegionMisc;

public class MySystemOne {
    ImportantPeopleAPI ip = Global.getSector().getImportantPeople();

    public static Logger log = Global.getLogger(MySystemOne.class);

    public void generate(SectorAPI sector) {
        StarSystemAPI domainOutpostSystem = sector.createStarSystem("Nataruk");
        domainOutpostSystem.getLocation().set(0,-65000); //bottom centerish - was +80000,-55000

      //domainOutpost.setOptionalUniqueId("oldlegion_domint_outpost");
        domainOutpostSystem.addTag(Tags.THEME_HIDDEN);
        domainOutpostSystem.addTag(Tags.THEME_SPECIAL);
        domainOutpostSystem.addTag(Tags.THEME_UNSAFE);
        domainOutpostSystem.addTag(Tags.NOT_RANDOM_MISSION_TARGET);

        domainOutpostSystem.setBackgroundTextureFilename("graphics/mymod/backgrounds/mybackground.jpg");

        domainOutpostSystem.initNonStarCenter();
//        PlanetAPI argonStar = domainOutpost.initStar("Argonian", // unique id for this star
//                "star_red_giant", // id in planets.json
//                100f, // radius (in pixels at default zoom)
//                650); // corona radius, from star edge
//        domainOutpost.setLightColor(new Color(239, 155, 128)); // light color in entire DomainOutpost, affects all entities
//        domainOutpost.removeEntity(argonStar);

        SectorEntityToken relay = domainOutpostSystem.addCustomEntity("mam_relay", "Domain Comm Relay", "comm_relay_domint", "domainspecops");

        SectorEntityToken relay_field = domainOutpostSystem.addTerrain(Terrain.MAGNETIC_FIELD,
                new MagneticFieldTerrainPlugin.MagneticFieldParams(
                        relay.getRadius() + 150f, // terrain effect band width
                        (relay.getRadius() + 150f), // terrain effect middle radius
                        relay, // entity that it's around
                        relay.getRadius()-25f, // visual band start
                        relay.getRadius() + 50f + 100f, // visual band end
                        new Color(149, 149, 149, 50), // base color
                        0.75f, // probability to spawn aurora sequence, checked once/day when no aurora in progress
                        new Color(90, 180, 40),
                        new Color(130, 145, 90),
                        new Color(165, 110, 145),
                        new Color(95, 55, 160),
                        new Color(117, 104, 140), // 45, 0, 130
                        new Color(128, 128, 128), // 20, 0, 130
                        new Color(154, 154, 154))); // 10, 0, 150
        relay_field.setCircularOrbit(relay, 0, 0, 100);

        SectorEntityToken relay_field1 = domainOutpostSystem.addTerrain(Terrain.MAGNETIC_FIELD,
                new MagneticFieldTerrainPlugin.MagneticFieldParams(
                        relay.getRadius() + 150f, // terrain effect band width
                        (relay.getRadius() + 150f), // terrain effect middle radius
                        relay, // entity that it's around
                        relay.getRadius()+25f, // visual band start
                        relay.getRadius() + 50f + 200f, // visual band end
                        new Color(149, 149, 149, 50), // base color
                        0.75f, // probability to spawn aurora sequence, checked once/day when no aurora in progress
                        new Color(90, 180, 40),
                        new Color(130, 145, 90),
                        new Color(165, 110, 145),
                        new Color(95, 55, 160),
                        new Color(117, 104, 140), // 45, 0, 130
                        new Color(128, 128, 128), // 20, 0, 130
                        new Color(154, 154, 154))); // 10, 0, 150
        relay_field1.setCircularOrbit(relay, 0, 0, 100);

        SectorEntityToken relay_field2 = domainOutpostSystem.addTerrain(Terrain.MAGNETIC_FIELD,
                new MagneticFieldTerrainPlugin.MagneticFieldParams(relay.getRadius() + 150f, // terrain effect band width
                        (relay.getRadius() + 150f), // terrain effect middle radius
                        relay, // entity that it's around
                        relay.getRadius() + 50f, // visual band start
                        relay.getRadius() + 50f + 500f, // visual band end
                        new Color(149, 149, 149, 50), // base color
                        0.75f, // probability to spawn aurora sequence, checked once/day when no aurora in progress
                        new Color(90, 180, 40),
                        new Color(130, 145, 90),
                        new Color(165, 110, 145),
                        new Color(95, 55, 160),
                        new Color(117, 104, 140), // 45, 0, 130
                        new Color(128, 128, 128), // 20, 0, 130
                        new Color(154, 154, 154))); // 10, 0, 150
        relay_field2.setCircularOrbit(relay, 0, 0, 100);

        SectorEntityToken relay_field3 = domainOutpostSystem.addTerrain(Terrain.MAGNETIC_FIELD,
                new MagneticFieldTerrainPlugin.MagneticFieldParams(relay.getRadius() + 300f, // terrain effect band width
                        (relay.getRadius() + 200f), // terrain effect middle radius
                        relay, // entity that it's around
                        relay.getRadius() + 100f, // visual band start
                        relay.getRadius() + 50f + 500f, // visual band end
                        new Color(149, 149, 149, 50), // base color
                        0.75f, // probability to spawn aurora sequence, checked once/day when no aurora in progress
                        new Color(90, 180, 40),
                        new Color(130, 145, 90),
                        new Color(165, 110, 145),
                        new Color(95, 55, 160),
                        new Color(117, 104, 140), // 45, 0, 130
                        new Color(128, 128, 128), // 20, 0, 130
                        new Color(154, 154, 154))); // 10, 0, 150
        relay_field3.setCircularOrbit(relay, 0, 0, 100);

        //asteroid belt1 ring
        //domainOutpost.addAsteroidBelt(relay, 1000, asteroidBelt1Dist, 800, 250, 400, Terrain.ASTEROID_BELT, "Inner Band");
        //domainOutpost.addRingBand(relay, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, asteroidBelt1Dist - 200, 250f);
        //domainOutpost.addRingBand(relay, "misc", "rings_asteroids0", 256f, 0, Color.gray, 256f, asteroidBelt1Dist, 350f);
        //domainOutpost.addRingBand(relay, "misc", "rings_asteroids0", 256f, 2, Color.gray, 256f, asteroidBelt1Dist + 200, 400f);

        PlanetAPI Hunhow = domainOutpostSystem.addPlanet("Hunhow", relay, "Hunhow's Fall", "barren", 0, 273, 4977, 157);
        Hunhow.setFaction("domainspecops");
        MarketAPI market = Global.getFactory().createMarket(
                "Hunhow_market",
                Hunhow.getName(), //market display name, usually the planet's name
                6
        );
        Hunhow.setMarket(market);
        //market global property settings
        market.setPrimaryEntity(Hunhow);
        market.setHidden(true);
        market.setInvalidMissionTarget(true);
        //setting survey level to fully surveyed to automatically reveal normally hidden resources on the planet overview and remove unexplored description.
        market.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        //set tariff percentage of 30%
        //this part of the code uses a mechanism called "MutableStat"
        //Used in a variety of places throughout Starsector to accomplish a variety of things
        //here it is important to simply know that "generator" is the id of the tariff value
        //and that you set it using the modifyFlat() function with a decimal value.
        market.getTariff().modifyFlat("generator", 0.3f);
        market.setPlanetConditionMarketOnly(false);
        market.addCondition(Conditions.THIN_ATMOSPHERE);
        market.addCondition(Conditions.ORE_ULTRARICH);
        market.addCondition(Conditions.RARE_ORE_ULTRARICH);
        //population tag is purely decorative. Can set to whatever you want or Omit it. For player colonies, adjusts to match growth of the market size.
        market.addCondition(Conditions.POPULATION_4);
        //1) set the market faction ID
        //2) add industries and sub-markets to the market
        //3) add the market to the global economy
        //the markets owning faction must be set before adding sub-markets and industries or the game will crash.
        market.setFactionId("domainspecops");
        //if no industries are added, the game wont crash...
        //weapons and such will be available for purchase from black and open markets.
        //colonies will have a -10 stability rating
        //there will be no supply or demand for goods under ''commodities'' though there will be procurement missions
        //once population is added, there will be supply/demand for supplies
        //stability will be around 5
        //stability information will be available by hovering
        //there will be severe accessibility penalty from lack of spaceport
        //finally, population adds an admnistrator npc to the comm directiory
        market.addIndustry(Industries.POPULATION);
        //spaceport adds quartermaster and portmaster npcs in Comms
        market.addIndustry(Industries.MEGAPORT);
        market.addIndustry(Industries.WAYSTATION);
        //Adding orbital station will place a station in orbit and add station commander npc to Comms
        market.addIndustry(Industries.STARFORTRESS_MID);
        market.addIndustry(Industries.ORBITALWORKS, new ArrayList(Arrays.asList("pristine_nanoforge")));
        market.addIndustry(Industries.HIGHCOMMAND);
        market.addIndustry(Industries.HEAVYBATTERIES);
        market.addIndustry(Industries.MINING);
        market.addIndustry("oldlegion_gate_infrastructure");
        //planet sub-markets
        market.addSubmarket(Submarkets.SUBMARKET_STORAGE);
        //market.addSubmarket(Submarkets.SUBMARKET_BLACK);
        market.addSubmarket(Submarkets.SUBMARKET_OPEN);
        //Market needs to be added to the global economy after sub-markets and industries
        //if you dont do this, at best commodities will be 1$, at worst the game will crash
        market.setEconGroup(market.getFactionId());
        market.addTag("market_no_officer_spawn");
        Global.getSector().getEconomy().addMarket(market, false); //the ''WithJunkerAndChatter'' flag. it will add space debris in orbit and radio chatter sound effects.

        SectorEntityToken buoy = domainOutpostSystem.addCustomEntity("nav_buoy", "Nav Buoy", "nav_buoy", "domainspecops");
        buoy.setCircularOrbit(relay, 25, 6561, 315);

        SectorEntityToken array = domainOutpostSystem.addCustomEntity("sensor_array", "Sensor Array", "sensor_array", "domainspecops");
        array.setCircularOrbit(relay, 25, 2361, 93);

        SectorEntityToken stableLoc1 = domainOutpostSystem.addCustomEntity("argon_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc1.setCircularOrbit(relay, MathUtils.getRandomNumberInRange(0f, 360f), 3700f, 520);

        SectorEntityToken domaingate = domainOutpostSystem.addCustomEntity("domain_ops_gate", "Domain Gate", "inactive_gate", "domainspecops");
        domaingate.setCircularOrbit(relay, 10, 5736, 273);

        SectorEntityToken beacon = domainOutpostSystem.addCustomEntity("warning_beacon_grave","Warning Beacon", "warning_beacon","domainspecops");
        beacon.setCircularOrbit(relay,2,7934,1256);
        beacon.setCustomDescriptionId("graveyard_beacon");

        SectorEntityToken argonAF1 = domainOutpostSystem.addTerrain(Terrain.ASTEROID_FIELD,
                new AsteroidFieldTerrainPlugin.AsteroidFieldParams(
                        300f, // min radius
                        500f, // max radius
                        10, // min asteroid count
                        24, // max asteroid count
                        4f, // min asteroid radius
                        16f, // max asteroid radius
                        "Asteroids Field")); // null for default name
        argonAF1.setCircularOrbit(relay, 130, 2750f, 240);

        DebrisFieldParams params = new DebrisFieldParams(
                500f, // field radius - should not go above 1000 for performance reasons
                3f, // density, visual - affects number of debris pieces
                10000000f, // duration in days
                0f); // days the field will keep generating glowing pieces
        params.source = DebrisFieldTerrainPlugin.DebrisFieldSource.MIXED;
        params.baseSalvageXP = 250; // base XP for scavenging in field

        SectorEntityToken debrisNextToGate = Misc.addDebrisField(domainOutpostSystem, params, StarSystemGenerator.random);
        debrisNextToGate.setSensorProfile(1000f);
        debrisNextToGate.setDiscoverable(true);
        debrisNextToGate.setCircularOrbit(beacon, 0f, 0f, 130f);
        debrisNextToGate.setId("domain_debrisNextToBeacon");

        SectorEntityToken Arcon_A = OldLegionMisc.addDerelict(
                domainOutpostSystem, //Star system
                domainOutpostSystem.getEntityById("warning_beacon_grave"), //Orbital focus
                "onslaught_xiv_Elite", //Variant Id
                ShipRecoverySpecial.ShipCondition.BATTERED, //Ship condition
                100f, //Orbital radius
                true //Recoverable?
        );
        SectorEntityToken Arcon_1 = OldLegionMisc.addDerelict(domainOutpostSystem, domainOutpostSystem.getEntityById("warning_beacon_grave"), "onslaught_xiv_Elite", ShipRecoverySpecial.ShipCondition.WRECKED, 234f, true);
        SectorEntityToken Arcon_2 = OldLegionMisc.addDerelict(domainOutpostSystem, domainOutpostSystem.getEntityById("warning_beacon_grave"), "onslaught_xiv_Elite", ShipRecoverySpecial.ShipCondition.WRECKED, 407f, true);
        SectorEntityToken Arcon_3 = OldLegionMisc.addDerelict(domainOutpostSystem, domainOutpostSystem.getEntityById("warning_beacon_grave"), "onslaught_xiv_Elite", ShipRecoverySpecial.ShipCondition.WRECKED, 458f, true);
        SectorEntityToken Arcon_5 = OldLegionMisc.addDerelict(domainOutpostSystem, domainOutpostSystem.getEntityById("warning_beacon_grave"), "legion_xiv_Elite", ShipRecoverySpecial.ShipCondition.WRECKED, 363f, true);
        SectorEntityToken Arcon_6 = OldLegionMisc.addDerelict(domainOutpostSystem, domainOutpostSystem.getEntityById("warning_beacon_grave"), "legion_xiv_Elite", ShipRecoverySpecial.ShipCondition.WRECKED, 171f, true);
        SectorEntityToken Arcon_7 = OldLegionMisc.addDerelict(domainOutpostSystem, domainOutpostSystem.getEntityById("warning_beacon_grave"), "dominator_XIV_Elite", ShipRecoverySpecial.ShipCondition.WRECKED, 281f, true);
        SectorEntityToken Arcon_8 = OldLegionMisc.addDerelict(domainOutpostSystem, domainOutpostSystem.getEntityById("warning_beacon_grave"), "dominator_XIV_Elite", ShipRecoverySpecial.ShipCondition.WRECKED, 384f, true);
        SectorEntityToken Arcon_9 = OldLegionMisc.addDerelict(domainOutpostSystem, domainOutpostSystem.getEntityById("warning_beacon_grave"), "dominator_XIV_Elite", ShipRecoverySpecial.ShipCondition.WRECKED, 432f, true);
        SectorEntityToken Arcon_10 = OldLegionMisc.addDerelict(domainOutpostSystem, domainOutpostSystem.getEntityById("warning_beacon_grave"), "falcon_xiv_Elite", ShipRecoverySpecial.ShipCondition.WRECKED, 331f, true);
        SectorEntityToken Arcon_11 = OldLegionMisc.addDerelict(domainOutpostSystem, domainOutpostSystem.getEntityById("warning_beacon_grave"), "falcon_xiv_Elite", ShipRecoverySpecial.ShipCondition.WRECKED, 397f, true);
        SectorEntityToken Arcon_12 = OldLegionMisc.addDerelict(domainOutpostSystem, domainOutpostSystem.getEntityById("warning_beacon_grave"), "falcon_xiv_Elite", ShipRecoverySpecial.ShipCondition.WRECKED, 303f, true);
        SectorEntityToken Arcon_13 = OldLegionMisc.addDerelict(domainOutpostSystem, domainOutpostSystem.getEntityById("warning_beacon_grave"), "eagle_xiv_Elite", ShipRecoverySpecial.ShipCondition.WRECKED, 91f, true);
        SectorEntityToken Arcon_14 = OldLegionMisc.addDerelict(domainOutpostSystem, domainOutpostSystem.getEntityById("warning_beacon_grave"), "eagle_xiv_Elite", ShipRecoverySpecial.ShipCondition.WRECKED, 121f, true);
        SectorEntityToken Arcon_15 = OldLegionMisc.addDerelict(domainOutpostSystem, domainOutpostSystem.getEntityById("warning_beacon_grave"), "eagle_xiv_Elite", ShipRecoverySpecial.ShipCondition.WRECKED, 481f, true);
        SectorEntityToken Arcon_16 = OldLegionMisc.addDerelict(domainOutpostSystem, domainOutpostSystem.getEntityById("warning_beacon_grave"), "eagle_xiv_Elite", ShipRecoverySpecial.ShipCondition.WRECKED, 394f, true);

        Global.getSector().addScript(new ArtanisPersonalFleet());
    }
}