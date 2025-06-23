package poggers.oldlegion.utils;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.characters.FullName.Gender;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import org.apache.log4j.Logger;

public class oldlegion_people {

    public static String OLDLEGION_NATAH = "oldlegion_natah";
    public static String OLDLEGION_SOLAX = "oldlegion_solaxwhitemore";
    public static Logger log = Global.getLogger(oldlegion_people.class);

    public static PersonAPI getPerson(String id) {
        return Global.getSector().getImportantPeople().getPerson(id);
    }

    public static void oldlegion_createMiscCharacters() {
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

            PersonAPI oldlegion_solaxwhitemore_person = Global.getFactory().createPerson();
            oldlegion_solaxwhitemore_person.setId(OLDLEGION_SOLAX);
            oldlegion_solaxwhitemore_person.setFaction("domainspecops");
            oldlegion_solaxwhitemore_person.setGender(Gender.MALE);
            oldlegion_solaxwhitemore_person.setRankId("spaceCommander");
            oldlegion_solaxwhitemore_person.setPostId("supplyOfficer");
            oldlegion_solaxwhitemore_person.setImportance(PersonImportance.HIGH);
            oldlegion_solaxwhitemore_person.getName().setFirst("Solax Whitemore");
            oldlegion_solaxwhitemore_person.setPortraitSprite(Global.getSettings().getSpriteName("characters", "kanta"));
            if (!ip.containsPerson(oldlegion_solaxwhitemore_person)) {
                log.info("OLDLEGION_RETROGEN: Solax Person did not exist. He has been generated retroactively");
                ip.addPerson(oldlegion_solaxwhitemore_person);
                market.addPerson(oldlegion_solaxwhitemore_person);
                market.getCommDirectory().addPerson(oldlegion_solaxwhitemore_person, 0);
                market.getCommDirectory().getEntryForPerson(oldlegion_solaxwhitemore_person).setHidden(false);
                ip.addPerson(oldlegion_solaxwhitemore_person);
            } else {
                log.info("OLDLEGION_RETROGEN: Solax Person already exists. No action taken");
            }

            PersonAPI oldlegion_natah_person = Global.getFactory().createPerson();
            oldlegion_natah_person.setId(OLDLEGION_NATAH);
            oldlegion_natah_person.setFaction("domainspecops");
            oldlegion_natah_person.setGender(Gender.MALE);
            oldlegion_natah_person.setRankId("factionLeader");
            oldlegion_natah_person.setPostId("factionLeader");
            oldlegion_natah_person.setImportance(PersonImportance.HIGH);
            oldlegion_natah_person.getName().setFirst("Joel Kepler");
            oldlegion_natah_person.setPortraitSprite(Global.getSettings().getSpriteName("characters", "kanta"));
            if (!ip.containsPerson(oldlegion_natah_person)) {
                log.info("OLDLEGION_RETROGEN: Natah Person did not exist. He has been generated retroactively");
                ip.addPerson(oldlegion_natah_person);
                market.addPerson(oldlegion_natah_person);
                market.getCommDirectory().addPerson(oldlegion_natah_person, 0);
                market.getCommDirectory().getEntryForPerson(oldlegion_natah_person).setHidden(false);
                ip.addPerson(oldlegion_natah_person);
            } else {
                log.info("OLDLEGION_RETROGEN: Natah Person already exists. No action taken");
            }
        }

    }

}
