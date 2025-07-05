package poggers.oldlegion.utils;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.ImportantPeopleAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.characters.FullName.Gender;

public class OldLegionPersons {
    public void create() {
        new Zeratul();

    }

    public class Zeratul {
        private String id = "domain_capt_zeratul";
        private final String firstname = "Zeratul";
        private final String lastname = "Talandar";
        private final FullName.Gender gender;
        private final FactionAPI faction;
        private final String rankid;
        private final String postid;
        private final String sprite;
        private final String personalities;

        Zeratul() {
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
        }
    }
}
