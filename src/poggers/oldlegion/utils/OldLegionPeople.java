package poggers.oldlegion.utils;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.characters.FullName.Gender;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.impl.campaign.missions.hub.BaseMissionHub;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OldLegionPeople {

    public static String OLDLEGION_NATAH = "oldlegion_natah";
    public static String OLDLEGION_SOLAX = "oldlegion_solaxwhitemore";
    public static String DOMAIN_CAPTAIN_ZERATUL = "domain_capt_zeratul";
    public static String ARTANIS = "oldlegion_artanis";
    public static Logger log = Global.getLogger(OldLegionPeople.class);

    public static final List<String> TAG_AS_DOMAIN_MISSION = new ArrayList<>(Arrays.asList(
            "ddro", "ssat"
    ));

    public static PersonAPI getPerson(String id) {
        return Global.getSector().getImportantPeople().getPerson(id);
    }

    public static void create() {
        createCharacters();
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

            PersonAPI oldlegion_natah_person = Global.getFactory().createPerson();
            oldlegion_natah_person.setId(OLDLEGION_NATAH);
            oldlegion_natah_person.getName().setFirst("Joel");
            oldlegion_natah_person.getName().setLast("Kepler");
            oldlegion_natah_person.setFaction("domainspecops");
            oldlegion_natah_person.setGender(Gender.MALE);
            oldlegion_natah_person.setPortraitSprite("graphics/portraits/stellaris_robot.png");
            oldlegion_natah_person.setRankId("factionLeader");
            oldlegion_natah_person.setPostId("factionLeader");
            oldlegion_natah_person.setImportance(PersonImportance.VERY_HIGH);
            oldlegion_natah_person.getStats().setSkillLevel("industrial_planning", 3.0F);
            oldlegion_natah_person.addTag("domain");
            if (!ip.containsPerson(oldlegion_natah_person)) {
                //log.info("OLDLEGION_RETROGEN: Natah Person did not exist. He has been generated retroactively");
                ip.addPerson(oldlegion_natah_person);
                market.addPerson(oldlegion_natah_person);
                market.getCommDirectory().addPerson(oldlegion_natah_person, 0);
                market.getCommDirectory().getEntryForPerson(oldlegion_natah_person).setHidden(false);
            } else {
                log.info("OLDLEGION_RETROGEN: Natah Person already exists. No action taken");
            }
            PersonAPI old_admin = market.getAdmin();
            if (old_admin != null) {
                market.getCommDirectory().removePerson(old_admin);
                market.setAdmin(oldlegion_natah_person);
            }
            if (old_admin == null) {
                market.setAdmin(oldlegion_natah_person);
            }

            PersonAPI oldlegion_solaxwhitemore_person = Global.getFactory().createPerson();
            oldlegion_solaxwhitemore_person.setId(OLDLEGION_SOLAX);
            oldlegion_solaxwhitemore_person.setFaction("domainspecops");
            oldlegion_solaxwhitemore_person.setGender(Gender.MALE);
            oldlegion_solaxwhitemore_person.setRankId("spaceCommander");
            oldlegion_solaxwhitemore_person.setPostId("supplyOfficer");
            oldlegion_solaxwhitemore_person.setImportance(PersonImportance.HIGH);
            oldlegion_solaxwhitemore_person.getName().setFirst("Solax Whitemore");
            oldlegion_solaxwhitemore_person.setPortraitSprite("graphics/portraits/stellaris_robot.png");
            oldlegion_solaxwhitemore_person.addTag("domain");
            BaseMissionHub.set(oldlegion_solaxwhitemore_person, new BaseMissionHub(oldlegion_solaxwhitemore_person));
            oldlegion_solaxwhitemore_person.getMemoryWithoutUpdate().set(BaseMissionHub.NUM_BONUS_MISSIONS, 1);
            if (!ip.containsPerson(oldlegion_solaxwhitemore_person)) {
                //log.info("OLDLEGION_RETROGEN: Solax Person did not exist. He has been generated retroactively");
                ip.addPerson(oldlegion_solaxwhitemore_person);
                market.addPerson(oldlegion_solaxwhitemore_person);
                market.getCommDirectory().addPerson(oldlegion_solaxwhitemore_person, 0);
                market.getCommDirectory().getEntryForPerson(oldlegion_solaxwhitemore_person).setHidden(false);

            } else {
                log.info("OLDLEGION_RETROGEN: Solax Person already exists. No action taken");
            }
        }
    }

    public static void createCharacters() {
        ImportantPeopleAPI ip = Global.getSector().getImportantPeople();
        if (getPerson(ARTANIS) == null) {
            PersonAPI person = genArtanis();
            ip.addPerson(person);
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
        person.setPortraitSprite("graphics/portraits/stellaris_robot.png");
        return person;
    }

    public static void setupDomainContactMissions() {
        for (String id : TAG_AS_DOMAIN_MISSION) {
            Global.getSettings().getMissionSpec(id).getTagsAny().add("domain");
        }
    }

}
