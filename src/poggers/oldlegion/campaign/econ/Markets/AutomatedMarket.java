package poggers.oldlegion.campaign.econ.Markets;

import com.fs.starfarer.api.impl.campaign.econ.BaseHazardCondition;
import com.fs.starfarer.api.impl.campaign.ids.Submarkets;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class AutomatedMarket extends BaseHazardCondition {

    public static float CREW_MULT_PENALTY = 0f;

    public void apply(String id) {
        super.apply(id);
        market.getSubmarket(Submarkets.SUBMARKET_OPEN).getCargo().removeCrew(10000);
        market.getSubmarket(Submarkets.SUBMARKET_OPEN).getCargo().removeMarines(10000);
        market.getCommodityData("domestic_goods").setMaxDemand(0);
        market.getCommodityData("marines").setMaxDemand(0);
        market.getCommodityData("crew").setMaxDemand(0);
        market.getCommodityData("food").setMaxDemand(1);
        market.getCommodityData("food").setStockpile(100);
        market.getIndustry("population").getSupply("crew").getQuantity().modifyMult(id, CREW_MULT_PENALTY);
        market.getIndustry("population").getDemand("domestic_goods").getQuantity().modifyMult(id, CREW_MULT_PENALTY);
        market.getIndustry("population").getDemand("food").getQuantity().modifyMult(id, CREW_MULT_PENALTY);
        market.getIndustry("megaport").getSupply("crew").getQuantity().modifyMult(id, CREW_MULT_PENALTY);
        market.getIndustry("highcommand").getSupply("crew").getQuantity().modifyMult(id, CREW_MULT_PENALTY);
        market.getIndustry("highcommand").getSupply("marines").getQuantity().modifyMult(id, CREW_MULT_PENALTY);
        market.getIndustry("starfortress_mid").getDemand("crew").getQuantity().modifyMult(id, CREW_MULT_PENALTY);
        market.getIndustry("waystation").getDemand("crew").getQuantity().modifyMult(id, CREW_MULT_PENALTY);
        market.getIndustry("heavybatteries").getSupply("marines").getQuantity().modifyMult(id, CREW_MULT_PENALTY);
    }

    public void unapply(String id) {
        super.unapply(id);
    }

    @Override
    public boolean showIcon() {
        return false;
    }
}
