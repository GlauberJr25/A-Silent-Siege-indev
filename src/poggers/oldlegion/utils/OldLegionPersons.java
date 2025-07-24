package poggers.oldlegion.utils;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.PersonImportance;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.characters.FullName.Gender;

public class OldLegionPersons {

    public static String DOMAIN_CAPTAIN_ZERATUL = "domain_capt_zeratul";

    public static PersonAPI getPerson(String id) {
        return Global.getSector().getImportantPeople().getPerson(id);
    }

    public static void oldlegion_createCaptCharacters() {
        ImportantPeopleAPI ip = Global.getSector().getImportantPeople();

        PersonAPI capt_zeratul_person = Global.getFactory().createPerson();
        capt_zeratul_person.setId(DOMAIN_CAPTAIN_ZERATUL);
        capt_zeratul_person.setFaction("domainspecops");
        capt_zeratul_person.setGender(Gender.MALE);
        capt_zeratul_person.setRankId("spaceCommander");
        capt_zeratul_person.setPostId("fleetCommander");
        capt_zeratul_person.setImportance(PersonImportance.HIGH);
        capt_zeratul_person.getName().setFirst("Zeratul");
        capt_zeratul_person.getName().setLast("Khalaidar");
        capt_zeratul_person.getStats().setLevel(8);
        capt_zeratul_person.getStats().setSkillLevel("ballistic_mastery", 2.0F);
        capt_zeratul_person.getStats().setSkillLevel("gunnery_implants", 1.0F);
        capt_zeratul_person.getStats().setSkillLevel("impact_mitigation", 1.0F);
        capt_zeratul_person.getStats().setSkillLevel("target_analysis", 1.0F);
        capt_zeratul_person.getStats().setSkillLevel("helmsmanship", 1.0F);
        capt_zeratul_person.getStats().setSkillLevel("polarized_armor", 1.0F);
        capt_zeratul_person.setPortraitSprite(Global.getSettings().getSpriteName("characters", "kanta"));
        if (!ip.containsPerson(capt_zeratul_person)) {
            ip.addPerson(capt_zeratul_person);}

        /*Zeratul() {
            this.gender = Gender.MALE;
            this.faction = Global.getSector().getFaction("domainspecops");
            this.rankid = "spaceCommander";
            this.postid = "baseFleetCommander";
            this.sprite = "graphics/portraits/characters/kanta.png";
            this.personalities = "steady";
            PersonAPI Zeratul = this.faction.createRandomPerson();
            Zeratul.setId("domain_capt_zeratul");
            Zeratul.getName().setFirst("Zeratul");
            Zeratul.getName().setLast("Talandar");
            Zeratul.getName().setGender(this.gender);
            Zeratul.setRankId("spaceCommander");
            Zeratul.setPostId("baseFleetCommander");
            Zeratul.setPortraitSprite("graphics/portraits/characters/kanta.png");
            Zeratul.setPersonality("steady");
            Zeratul.getStats().setSkipRefresh(true);
            Zeratul.getStats().setLevel(8);
            Zeratul.getStats().setSkillLevel("ballistic_mastery", 2.0F);
            Zeratul.getStats().setSkillLevel("gunnery_implants", 1.0F);
            Zeratul.getStats().setSkillLevel("impact_mitigation", 1.0F);
            Zeratul.getStats().setSkillLevel("target_analysis", 1.0F);
            Zeratul.getStats().setSkillLevel("helmsmanship", 1.0F);
            Zeratul.getStats().setSkillLevel("polarized_armor", 1.0F);
            ImportantPeopleAPI ip = Global.getSector().getImportantPeople();
            ip.addPerson(Zeratul);
            PersonAPI test = ip.getPerson("domain_capt_zeratul");

            if (test == null) throw new RuntimeException("zeratul == null");

        }*/
    }
}
