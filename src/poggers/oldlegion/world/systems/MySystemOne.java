package poggers.oldlegion.world.systems;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Condition;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.RepLevel;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.PlanetConditionGenerator;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.terrain.AsteroidFieldTerrainPlugin;
import com.fs.starfarer.api.impl.campaign.terrain.AsteroidFieldTerrainPlugin.AsteroidFieldParams;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.impl.campaign.terrain.MagneticFieldTerrainPlugin.MagneticFieldParams;

import poggers.oldlegion.campaign.econ.industry.GateResupplyRun;

import org.lazywizard.lazylib.MathUtils;

public class MySystemOne {
    public void generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Nataruk");
        system.getLocation().set(+80000,-55000); //bottom rightish

        system.addTag(Tags.THEME_HIDDEN);
        system.addTag(Tags.THEME_SPECIAL);
        system.addTag(Tags.STAR_HIDDEN_ON_MAP);
        system.addTag(Tags.THEME_UNSAFE);
        system.addTag(Tags.NOT_RANDOM_MISSION_TARGET);
        system.addTag("star_hidden_on_map");

        system.setBackgroundTextureFilename("graphics/mymod/backgrounds/mybackground.jpg");

        // create the star and generate the hyperspace anchor for this system
        PlanetAPI argonStar = system.initStar("Argon", // unique id for this star
                "star_red_giant", // id in planets.json
                1100f, // radius (in pixels at default zoom)
                450); // corona radius, from star edge
        system.setLightColor(new Color(239, 155, 128)); // light color in entire system, affects all entities


        //setup all distances here
        final float asteroids1Dist = 2750f;
        final float stable1Dist = 4200f;
        final float asteroidBelt1Dist = 5700f;


        SectorEntityToken argonAF1 = system.addTerrain(Terrain.ASTEROID_FIELD,
                new AsteroidFieldTerrainPlugin.AsteroidFieldParams(
                        200f, // min radius
                        300f, // max radius
                        8, // min asteroid count
                        16, // max asteroid count
                        4f, // min asteroid radius
                        16f, // max asteroid radius
                        "Asteroids Field")); // null for default name
        argonAF1.setCircularOrbit(argonStar, 130, asteroids1Dist, 240);

        //add first stable loc
        SectorEntityToken stableLoc1 = system.addCustomEntity("argon_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc1.setCircularOrbit(argonStar, MathUtils.getRandomNumberInRange(0f, 360f), stable1Dist, 520);

        //asteroid belt1 ring
        system.addAsteroidBelt(argonStar, 1000, asteroidBelt1Dist, 800, 250, 400, Terrain.ASTEROID_BELT, "Inner Band");
        system.addRingBand(argonStar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, asteroidBelt1Dist - 200, 250f);
        system.addRingBand(argonStar, "misc", "rings_asteroids0", 256f, 0, Color.gray, 256f, asteroidBelt1Dist, 350f);
        system.addRingBand(argonStar, "misc", "rings_asteroids0", 256f, 2, Color.gray, 256f, asteroidBelt1Dist + 200, 400f);


        PlanetAPI planet = system.addPlanet("Hunhow", argonStar, "Hunhow's Fall", "barren", 0, 273, 7777, 157);
        planet.setFaction("domainspecops");

        //create and initialize market:
        //A size 1 market's population industry will only demand supplies and produce nothing
        MarketAPI market = Global.getFactory().createMarket(
                "Hunhow_market",
                planet.getName(), //market display name, usually the planet's name
                6
        );

        planet.setMarket(market);
        //market global property settings
        market.setPrimaryEntity(planet);

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

        //planet surface/market conditions
        market.setPlanetConditionMarketOnly(false);
        //some tags, like thin atmosphere will affect industry and upkeep costs
        market.addCondition(Conditions.THIN_ATMOSPHERE);
        market.addCondition(Conditions.ORE_ULTRARICH);
        market.addCondition(Conditions.RARE_ORE_ULTRARICH);
        //population tag is purely decorative. Can set to whatever you want or Omit it. For player colonies, adjusts to match growth of the market size.
        market.addCondition(Conditions.POPULATION_4);

        //the following settings must be implemented in the correct order to function properly
        //1) set the market faction ID
        //2) add industries and sub-markets to the market
        //3) add the market to the global economy

        //the markets owning faction must be set before adding sub-markets and industries or the game will crash.
        market.setFactionId("domainspecops");

        //Planet colony industries
        //if no industries are added, the game wont crash...
        //weapons and such will be available for purchase from black and open markets.
        //your colonies will have a -10 stability rating
        //there will be no supply or demand for goods under ''commodities'' though there will be procurement missions

        //once population is added, there will be supply/demand for supplies
        //stability will be around 5
        //stability information will be available by hovering
        //there will be severe accessibility penalty from lack of spaceport
        //finally, population adds an admnistrator npc to the comm directiory
        market.addIndustry(Industries.POPULATION);

        //spaceport isnt required, but lack gives -100% accessibility to the colony
        //spaceport enables repair option in the main menu
        //spaceport adds quartermaster and portmaster npcs in Comms
        market.addIndustry(Industries.MEGAPORT);

        //Adding orbital station will place a station in orbit and add station commander npc to Comms
        market.addIndustry(Industries.STARFORTRESS_MID);

        market.addIndustry(Industries.ORBITALWORKS);
        market.addIndustry(Industries.MINING);
        market.addIndustry(Industries.HEAVYBATTERIES);
        market.addIndustry(Industries.HIGHCOMMAND);
        market.addIndustry(Industries.WAYSTATION);
        market.addIndustry("oldlegion_gate_infrastructure");


        //planet sub-markets
        market.addSubmarket(Submarkets.SUBMARKET_STORAGE);
        market.addSubmarket(Submarkets.SUBMARKET_BLACK);
        market.addSubmarket(Submarkets.SUBMARKET_OPEN);


        //Market needs to be added to the global economy after sub-markets and industries
        //if you dont do this, at best commodities will be 1$, at worst the game will crash
        EconomyAPI globalEconomy = Global.getSector().getEconomy();
                globalEconomy.addMarket(
                        market, //the market to add obviously!
                        false //the ''WithJunkerAndChatter'' flag. it will add space debris in orbit and radio chatter sound effects.
                );

        //sectorEntityToken relay = system.addCustomEntity("mam_relay", "Comm Relay", "comm_relay", "domainspecops"
        //relay.setCircularOrbit(star, 0, 1831, 23)
        SectorEntityToken relay = system.addCustomEntity("mam_relay", "Comm Relay", "comm_relay", "domainspecops");
        relay.setCircularOrbit(argonStar, 0, 2861, 62);

        SectorEntityToken buoy = system.addCustomEntity("nav_buoy", "Nav Buoy", "nav_buoy", "domainspecops");
        buoy.setCircularOrbit(argonStar, 25, 8861, 275);

        SectorEntityToken array = system.addCustomEntity("sensor_array", "Sensor Array", "sensor_array", "domainspecops");
        array.setCircularOrbit(argonStar, 25, 3961, 95);

        SectorEntityToken gate = system.addCustomEntity("domain_ops_gate", "Domain Gate", "inactive_gate", "domainspecops");
        gate.setCircularOrbit(argonStar, 10, 6736, 233);

        //auto jump point generation
        system.autogenerateHyperspaceJumpPoints(true, false);

        HyperspaceTerrainPlugin plugin = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin();
        NebulaEditor editor = new NebulaEditor(plugin);
        float minRadius = plugin.getTileSize() * 2f;

        float radius = system.getMaxRadiusInHyperspace();
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f);
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f, 0.25f);


    }
}