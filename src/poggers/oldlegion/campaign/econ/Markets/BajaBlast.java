package poggers.oldlegion.campaign.econ.Markets;

import com.fs.starfarer.api.impl.campaign.econ.BaseHazardCondition;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class BajaBlast extends BaseHazardCondition {

    public static final float HAZARD_RATING = 1000f;

    public void apply(String id) {
        super.apply(id);
        market.getHazard().modifyFlat(id, 10f, condition.getName());
        //market.getHazard().modifyFlat(id, hazard, condition.getName());
    }

    public void unapply(String id) {
        super.unapply(id);
        market.getAccessibilityMod().unmodifyFlat(id);
    }
    protected void createTooltipAfterDescription(TooltipMakerAPI tooltip, boolean expanded) {
        super.createTooltipAfterDescription(tooltip, expanded);

        tooltip.addPara("%s Hazard Rating",
                10f, Misc.getHighlightColor(),
                "+" + (int)HAZARD_RATING + "%");
    }
}
