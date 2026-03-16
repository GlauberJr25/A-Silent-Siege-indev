package poggers.oldlegion.utils;

import com.fs.starfarer.api.Global;
import data.scripts.campaign.ids.SotfIDs;

import java.awt.*;

public class OldLegionIDs {

    //public static final String  = " ";
    public static final String DOMINT = "domainspecops";

    public static final String PRISM_CORE = "dgt_prismcore"; //prism core not sure if im gonna use it? maybe for the character instead of the officer, who knows im learning
    public static final String PRISM_CORE_OFFICER = "dgt_prism_officer"; //prism core copy for officer usage
    public static final String PRISM_FACTION = "oldlegion_prism_faction";
    public static Color PRISM_COLOR = Global.getSettings().getFactionSpec(OldLegionIDs.PRISM_FACTION).getBaseUIColor();

    public static final String ARTANIS_FLEET = "$oldlegion_artanisFleet";
}
