package poggers.oldlegion.world.systems;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.Script;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.FleetAssignment;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.fleet.MutableFleetStatsAPI;
import com.fs.starfarer.api.impl.campaign.fleets.FleetFactoryV3;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.terrain.AsteroidFieldTerrainPlugin;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.lazywizard.lazylib.MathUtils;
import poggers.oldlegion.utils.OldLegionPersons;

public class MySystemOne {
    ImportantPeopleAPI ip = Global.getSector().getImportantPeople();

    public static Logger log = Global.getLogger(MySystemOne.class);

    public JSONObject getFleetData(String id) {
        JSONObject fleetData = null;

        try {
            JSONObject jsonData = Global.getSettings().loadJSON("data/config/domain_escort_fleet.json", "poggers_old_legion");
            JSONArray allFleet = jsonData.getJSONArray("availableFleets");

            for(int n = 0; n < allFleet.length(); ++n) {
                JSONObject captData = allFleet.getJSONObject(n);
                if (captData.getString("fleetId").equals(id)) {
                    fleetData = captData;
                    break;
                }
            }
        } catch (Exception e) {
            log.info(e);
        }

        return fleetData;
    }

    public void generate(SectorAPI sector) {
        StarSystemAPI DomainOutpost = sector.createStarSystem("Nataruk");
        DomainOutpost.getLocation().set(+80000,-55000); //bottom rightish

        DomainOutpost.setOptionalUniqueId("oldlegion_domint_outpost");
        DomainOutpost.addTag(Tags.THEME_HIDDEN);
        DomainOutpost.addTag(Tags.THEME_SPECIAL);
        DomainOutpost.addTag(Tags.THEME_UNSAFE);
        DomainOutpost.addTag(Tags.NOT_RANDOM_MISSION_TARGET);

        DomainOutpost.setBackgroundTextureFilename("graphics/mymod/backgrounds/mybackground.jpg");

        PlanetAPI argonStar = DomainOutpost.initStar("Argonian", // unique id for this star
                "star_red_giant", // id in planets.json
                100f, // radius (in pixels at default zoom)
                650); // corona radius, from star edge
        DomainOutpost.setLightColor(new Color(239, 155, 128)); // light color in entire DomainOutpost, affects all entities

        DomainOutpost.removeEntity(argonStar);
        SectorEntityToken relay = DomainOutpost.addCustomEntity("mam_relay", "Domain Comm Relay", "comm_relay", "domainspecops");

        //setup all distances here
        final float asteroids1Dist = 2750f;
        final float stable1Dist = 4200f;
        final float asteroidBelt1Dist = 5700f;
        SectorEntityToken argonAF1 = DomainOutpost.addTerrain(Terrain.ASTEROID_FIELD,
                new AsteroidFieldTerrainPlugin.AsteroidFieldParams(
                        300f, // min radius
                        500f, // max radius
                        10, // min asteroid count
                        24, // max asteroid count
                        4f, // min asteroid radius
                        16f, // max asteroid radius
                        "Asteroids Field")); // null for default name
        argonAF1.setCircularOrbit(relay, 130, asteroids1Dist, 240);

        //add first stable loc
        SectorEntityToken stableLoc1 = DomainOutpost.addCustomEntity("argon_stableloc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc1.setCircularOrbit(relay, MathUtils.getRandomNumberInRange(0f, 360f), stable1Dist, 520);

        //asteroid belt1 ring
        DomainOutpost.addAsteroidBelt(relay, 1000, asteroidBelt1Dist, 800, 250, 400, Terrain.ASTEROID_BELT, "Inner Band");
        DomainOutpost.addRingBand(relay, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, asteroidBelt1Dist - 200, 250f);
        DomainOutpost.addRingBand(relay, "misc", "rings_asteroids0", 256f, 0, Color.gray, 256f, asteroidBelt1Dist, 350f);
        DomainOutpost.addRingBand(relay, "misc", "rings_asteroids0", 256f, 2, Color.gray, 256f, asteroidBelt1Dist + 200, 400f);

        PlanetAPI Hunhow = DomainOutpost.addPlanet("Hunhow", relay, "Hunhow's Fall", "barren", 0, 273, 7777, 157);
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
        //the following settings must be implemented in the correct order to function properly
        //1) set the market faction ID
        //2) add industries and sub-markets to the market
        //3) add the market to the global economy

        //the markets owning faction must be set before adding sub-markets and industries or the game will crash.
        market.setFactionId("domainspecops");
        //Planet colony industries
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
        //spaceport isn't required, but lack gives -100% accessibility to the colony
        //spaceport enables repair option in the main menu
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
        market.addSubmarket(Submarkets.SUBMARKET_BLACK);
        market.addSubmarket(Submarkets.SUBMARKET_OPEN);
        //Market needs to be added to the global economy after sub-markets and industries
        //if you dont do this, at best commodities will be 1$, at worst the game will crash
        market.setEconGroup(market.getFactionId());
        market.addTag("market_no_officer_spawn");
        Global.getSector().getEconomy().addMarket(market, false);
        /*EconomyAPI globalEconomy = Global.getSector().getEconomy();
                globalEconomy.addMarket(
                        market, //the market to add obviously!
                        false //the ''WithJunkerAndChatter'' flag. it will add space debris in orbit and radio chatter sound effects.
                );
        */
        SectorEntityToken buoy = DomainOutpost.addCustomEntity("nav_buoy", "Nav Buoy", "nav_buoy", "domainspecops");
        buoy.setCircularOrbit(relay, 25, 8861, 275);

        SectorEntityToken array = DomainOutpost.addCustomEntity("sensor_array", "Sensor Array", "sensor_array", "domainspecops");
        array.setCircularOrbit(relay, 25, 3961, 95);

        SectorEntityToken domaingate = DomainOutpost.addCustomEntity("domain_ops_gate", "Domain Gate", "inactive_gate", "domainspecops");
        domaingate.setCircularOrbit(relay, 10, 6736, 233);

        OldLegionPersons.oldlegion_createCaptCharacters();

        Global.getSector().addScript(new ArtanisPersonalFleet());

        JSONObject fleetData = this.getFleetData("domain_ra_fleet_remnant");

        try {
            ImportantPeopleAPI ip = Global.getSector().getImportantPeople();
            PersonAPI capt = ip.getPerson(fleetData.getString("fleetCaptain"));
            CampaignFleetAPI domainFleet = FleetFactoryV3.createEmptyFleet("domainspecops", fleetData.getString("fleetType"), (MarketAPI)null);
            FleetMemberAPI flagShip = Global.getFactory().createFleetMember(FleetMemberType.SHIP, fleetData.getString("fleetFlagship"));
            flagShip.setShipName(fleetData.getString("fleetFlagshipName"));
            flagShip.setFlagship(true);
            domainFleet.getFleetData().addFleetMember(flagShip);
            flagShip.setCaptain(capt);
            JSONArray fleetMembers = fleetData.getJSONArray("fleetComposition");
            for(int n = 0; n < fleetMembers.length(); ++n) {
                FleetMemberAPI memberShip = Global.getFactory().createFleetMember(FleetMemberType.SHIP, (String)fleetMembers.get(n));
                domainFleet.getFleetData().addFleetMember(memberShip);
            }
            domainFleet.getFleetData().setSyncNeeded();
            domainFleet.getFleetData().syncIfNeeded();
            domainFleet.setCommander(capt);
            domainFleet.setName(fleetData.getString("fleetName"));
            domainFleet.setId(fleetData.getString("fleetId"));
            DomainOutpost.addEntity(domainFleet);
            MutableFleetStatsAPI domainFleetStats = domainFleet.getStats();
            domainFleet.getAI().addAssignment(FleetAssignment.ORBIT_AGGRESSIVE, domaingate, 9999.0F, (Script) null);
        } catch (JSONException ex) {
            log.info(ex);
        }
        //HyperspaceTerrainPlugin plugin = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin();
        //NebulaEditor editor = new NebulaEditor(plugin);
        //float minRadius = plugin.getTileSize() * 2f;

        //float radius = DomainOutpost.getMaxRadiusInHyperspace();
        //editor.clearArc(DomainOutpost.getLocation().x, DomainOutpost.getLocation().y, 0, radius + minRadius, 0, 360f);
        //editor.clearArc(DomainOutpost.getLocation().x, DomainOutpost.getLocation().y, 0, radius + minRadius, 0, 360f, 0.25f);
    }
}