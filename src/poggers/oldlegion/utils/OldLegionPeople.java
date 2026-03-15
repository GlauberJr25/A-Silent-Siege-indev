package poggers.oldlegion.utils;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.characters.FullName.Gender;
import com.fs.starfarer.api.impl.campaign.ids.Personalities;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.impl.campaign.ids.Skills;
import com.fs.starfarer.api.impl.campaign.missions.hub.BaseMissionHub;
import data.scripts.campaign.ids.SotfIDs;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OldLegionPeople {
    public static String OLDLEGION_NATAH = "oldlegion_natah";
    public static String OLDLEGION_SOLAX = "oldlegion_solaxwhitemore";
    public static String ARTANIS = "oldlegion_artanis";
    public static String PRISM = "oldlegion_prism";
    public static Logger log = Global.getLogger(OldLegionPeople.class);

    public static final List<String> TAG_AS_DOMAIN_MISSION = new ArrayList<>(Arrays.asList(
            "ddro", "ssat"
    ));

    public static PersonAPI getPerson(String id) {
        return Global.getSector().getImportantPeople().getPerson(id);
    }

    public static void oldlegion_createStoryCharacters() {
        ImportantPeopleAPI ip = Global.getSector().getImportantPeople();
        MarketAPI market = null;

        market = Global.getSector().getEconomy().getMarket("Hunhow_market");
        if (market != null) {
            for (PersonAPI p : market.getPeopleCopy()) {
                if (p.getPostId().equals(Ranks.POST_PORTMASTER) || p.getPostId().equals(Ranks.POST_BASE_COMMANDER) ||
                        p.getPostId().equals(Ranks.POST_STATION_COMMANDER) || p.getPostId().equals(Ranks.POST_SUPPLY_OFFICER) ||
                        p.getPostId().equals(Ranks.POST_ADMINISTRATOR)) {
                    market.removePerson(p);
                    ip.removePerson(p);
                    market.getCommDirectory().removePerson(p);
                }
            }

            //Create Joel Kepler, outpost boss and admin
            PersonAPI natah_person = Global.getFactory().createPerson();
            natah_person.setId(OLDLEGION_NATAH);
            natah_person.getName().setFirst("Joel");
            natah_person.getName().setLast("Kepler");
            natah_person.setFaction("domainspecops");
            natah_person.setGender(Gender.MALE);
            natah_person.setPortraitSprite("graphics/portraits/domint_proxy_badge.png");
            natah_person.setRankId("factionLeader");
            natah_person.setPostId("factionLeader");
            natah_person.setImportance(PersonImportance.VERY_HIGH);
            natah_person.getStats().setSkillLevel("industrial_planning", 3.0F);
            natah_person.addTag("domain");
            BaseMissionHub.set(natah_person, new BaseMissionHub(natah_person));
            natah_person.getMemoryWithoutUpdate().set(BaseMissionHub.NUM_BONUS_MISSIONS, 1);
            if (!ip.containsPerson(natah_person)) {
                ip.addPerson(natah_person);}
            else {log.info("OLDLEGION_RETROGEN: Natah Person already exists. No action taken");}
            PersonAPI old_admin = market.getAdmin();
            if (old_admin != null) {
                market.getCommDirectory().removePerson(old_admin);
                market.setAdmin(natah_person);}
            if (old_admin == null) {market.setAdmin(natah_person);}

            //create Solax, outpost quartermaster
            PersonAPI solaxwhitemore_person = Global.getFactory().createPerson();
            solaxwhitemore_person.setId(OLDLEGION_SOLAX);
            solaxwhitemore_person.setFaction("domainspecops");
            solaxwhitemore_person.setGender(Gender.MALE);
            solaxwhitemore_person.setRankId("spaceCommander");
            solaxwhitemore_person.setPostId("supplyOfficer");
            solaxwhitemore_person.setImportance(PersonImportance.HIGH);
            solaxwhitemore_person.getName().setFirst("Solax Whitemore");
            solaxwhitemore_person.setPortraitSprite("graphics/portraits/domint_proxy.png");
            solaxwhitemore_person.addTag("domain");
            //BaseMissionHub.set(solaxwhitemore_person, new BaseMissionHub(solaxwhitemore_person));
            //solaxwhitemore_person.getMemoryWithoutUpdate().set(BaseMissionHub.NUM_BONUS_MISSIONS, 1);
            if (!ip.containsPerson(solaxwhitemore_person)) {
                ip.addPerson(solaxwhitemore_person);}
            else {log.info("OLDLEGION_RETROGEN: Solax Person already exists. No action taken");}
            market.addPerson(solaxwhitemore_person);
            market.getCommDirectory().addPerson(solaxwhitemore_person, 0);
            market.getCommDirectory().getEntryForPerson(solaxwhitemore_person).setHidden(false);
            market.addPerson(natah_person);
            market.getCommDirectory().addPerson(natah_person, 1);
            market.getCommDirectory().getEntryForPerson(natah_person).setHidden(false);
        }
    }

    public static void createCharacters() {
        ImportantPeopleAPI ip = Global.getSector().getImportantPeople();
//        if (getPerson(ARTANIS) == null) {
//            PersonAPI person = genArtanis();
//            ip.addPerson(person);
//        }
        //Prism AI
        if (getPerson(PRISM) == null) {
            PersonAPI Prism = Global.getFactory().createPerson();
            Prism.setId(PRISM);
            Prism.setAICoreId(OldLegionIDs.PRISM_CORE_OFFICER);
            Prism.setFaction(OldLegionIDs.PRISM_FACTION);
            Prism.setGender(Gender.MALE);
            Prism.setRankId("spaceCommander");
            Prism.setPostId("supplyOfficer");
            Prism.setImportance(PersonImportance.VERY_HIGH);
            Prism.getName().setFirst("Prism");
            Prism.getName().setLast("");
            Prism.setPortraitSprite("graphics/portraits/prism.png");
            Prism.addTag("omega");
            Prism.setPersonality(Personalities.STEADY);
            Prism.getStats().setLevel(8);
            Prism.getStats().setSkillLevel(Skills.FIELD_MODULATION, 2);
            Prism.getStats().setSkillLevel(Skills.SYSTEMS_EXPERTISE, 2);
            Prism.getStats().setSkillLevel(Skills.HELMSMANSHIP, 2);
            Prism.getStats().setSkillLevel(Skills.ENERGY_WEAPON_MASTERY, 2);
            Prism.getStats().setSkillLevel(Skills.ORDNANCE_EXPERTISE, 2);
            Prism.getStats().setSkillLevel(Skills.TARGET_ANALYSIS, 2);
            Prism.getStats().setSkillLevel(Skills.GUNNERY_IMPLANTS, 2);
            Prism.getStats().setSkillLevel(Skills.POLARIZED_ARMOR, 2);

            Prism.getStats().setSkillLevel(Skills.COORDINATED_MANEUVERS, 1);
            Prism.getStats().setSkillLevel(Skills.PHASE_CORPS, 1);
            Prism.getStats().setSkillLevel(Skills.FLUX_REGULATION, 1);

            if (!ip.containsPerson(Prism)) {
                ip.addPerson(Prism);}
            else {log.info("OLDLEGION_RETROGEN: Prism already exists. No action taken");}
        }
    }

    public static PersonAPI genArtanis() {
        PersonAPI person = Global.getFactory().createPerson();
        person.setId(ARTANIS);
        person.setFaction("domainspecops");
        person.setGender(Gender.MALE);
        person.setRankId(Ranks.SPACE_COMMANDER);
        person.setPostId(Ranks.POST_FLEET_COMMANDER);
        person.getStats().setLevel(8);
        person.setPersonality("aggressive");
        person.getName().setFirst("Joel");
        person.getName().setLast("Kepler");
        person.getStats().setSkillLevel("helmsmanship", 2.0F);
        person.getStats().setSkillLevel("target_analysis", 2.0F);
        person.getStats().setSkillLevel("impact_mitigation", 2.0F);
        person.getStats().setSkillLevel("field_modulation", 2.0F);
        person.getStats().setSkillLevel("gunnery_implants", 2.0F);
        person.getStats().setSkillLevel("polarized_armor", 2.0F);
        person.getStats().setSkillLevel("systems_expertise", 2.0F);
        person.getStats().setSkillLevel("tactical_drills", 1.0F);
        person.getStats().setSkillLevel("support_doctrine", 1.0F);
        person.getStats().setSkillLevel("electronic_warfare", 1.0F);
        person.getStats().setSkillLevel("coordinated_maneuvers", 1.0F);

        person.setPortraitSprite("graphics/portraits/portrait_generic_grayscale.png");
        return person;
    }

    public static void setupDomainContactMissions() {
        for (String id : TAG_AS_DOMAIN_MISSION) {
            Global.getSettings().getMissionSpec(id).getTagsAny().add("domain");
        }
    }

}
