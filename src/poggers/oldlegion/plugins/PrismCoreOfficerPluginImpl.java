package poggers.oldlegion.plugins;

import java.util.Random;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.FullName.Gender;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.BaseAICoreOfficerPluginImpl;
import com.fs.starfarer.api.impl.campaign.ids.Personalities;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.impl.campaign.ids.Skills;
import poggers.oldlegion.utils.OldLegionIDs;

public class PrismCoreOfficerPluginImpl extends BaseAICoreOfficerPluginImpl {

    public static int DGT_PRISM_POINTS = 0;
    public static float DGT_PRISM_MULT = 4;

    public PersonAPI createPerson(String aiCoreId, String factionId, Random random) {
        if (random == null) random = new Random();

        PersonAPI person = Global.getFactory().createPerson();
        person.setFaction(factionId);
        person.setAICoreId(aiCoreId);

        CommoditySpecAPI spec = Global.getSettings().getCommoditySpec(aiCoreId);
        boolean dgt_prism = OldLegionIDs.PRISM_CORE_OFFICER.equals(aiCoreId);

        person.getStats().setSkipRefresh(true);

        person.setName(new FullName("Prism", "", Gender.ANY));
        int points = 0;
        float mult = 1f;
        if (dgt_prism) {
            person.setPortraitSprite("graphics/portraits/characters/prism.png");
            person.getStats().setLevel(9);
            person.getStats().setSkillLevel(Skills.HELMSMANSHIP, 2);
            person.getStats().setSkillLevel(Skills.TARGET_ANALYSIS, 2);
            person.getStats().setSkillLevel(Skills.IMPACT_MITIGATION, 2);
            person.getStats().setSkillLevel(Skills.FIELD_MODULATION, 2);
            person.getStats().setSkillLevel(Skills.GUNNERY_IMPLANTS, 2);
            person.getStats().setSkillLevel(Skills.COMBAT_ENDURANCE, 2);
            person.getStats().setSkillLevel(Skills.DAMAGE_CONTROL, 2);
            person.getStats().setSkillLevel(Skills.POINT_DEFENSE, 2);
        }

        points = DGT_PRISM_POINTS;
        mult = DGT_PRISM_MULT;
        if (points != 0) {
            person.getMemoryWithoutUpdate().set(AUTOMATED_POINTS_VALUE, points);
        }
        person.getMemoryWithoutUpdate().set(AUTOMATED_POINTS_MULT, mult);

        person.setPersonality(Personalities.AGGRESSIVE);
        person.setRankId("spaceCommander");
        person.setPostId(null);

        person.getStats().setSkipRefresh(false);

        return person;
    }

}
