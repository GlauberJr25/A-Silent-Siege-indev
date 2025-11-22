package poggers.oldlegion.campaign.econ.industry;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.util.Pair;

public class GateResupplyRun extends BaseIndustry {
    boolean isAOTDVoKEnabled = Global.getSettings().getModManager().isModEnabled("aotd_vok");

    public void apply() {
        super.apply(true);
        int size = this.market.getSize();
        demand("heavy_machinery", size + 1);
        demand("metals", 3 + size - 2);
        supply("heavy_machinery", size + 2);
        supply("supplies", size + 2);
        supply("metals", size + 1);
        supply("fuel", size + 2);
        supply("food", size);
        supply("organics", size - 1);
        supply("volatiles", size - 5);
        supply("rare_metals", size - 2);
        supply("ships", size + 2);
        supply("domestic_goods", size - 1);
        supply("luxury_goods", size - 3);
        supply("volatiles", size + 4);
        if (isAOTDVoKEnabled) {
            supply("purified_rare_metal", size + 1);
        }
        Pair<String, Integer> deficit = getMaxDeficit(new String[]{"metals", "heavy_machinery"});
        int maxDeficit = size - 3;
        if ((Integer)deficit.two > maxDeficit) {
            deficit.two = maxDeficit;
        }

        this.applyDeficitToProduction(2, deficit, new String[]{"heavy_machinery", "supplies"});
        if (!isFunctional()) {
            supply.clear();
            unapply();
        }

    }

    public boolean isAvailableToBuild() {
        return false;
    }

    public boolean isDemandLegal(CommodityOnMarketAPI com) {
        return true;
    }

    public boolean isSupplyLegal(CommodityOnMarketAPI com) {
        return true;
    }

    public boolean showWhenUnavailable() {
        return false;
    }

    //public String getUnavailableReason() {
    //return "Needs to be allied with Domain FOB";
    //}

    protected boolean canImproveToIncreaseProduction() {
        return true;
    }
}