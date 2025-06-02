package poggers.oldlegion.campaign.econ.industry;

import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.util.Pair;

public class GateResupplyRun extends BaseIndustry {
    public void apply() {
        super.apply(true);
        int size = this.market.getSize();
        this.demand("heavy_machinery", size + 1);
        this.demand("metals", 3 + size - 2);
        this.supply("heavy_machinery", size + 2);
        this.supply("supplies", size + 2);
        this.supply("metals", size + 1);
        this.supply("fuel", size + 2);
        this.supply("food", size);
        this.supply("organics", size - 1);
        this.supply("volatiles", size - 5);
        this.supply("rare_metals", size - 2);
        this.supply("ships", size + 2);
        this.supply("domestic_goods", size - 1);
        this.supply("luxury_goods", size - 3);
        Pair<String, Integer> deficit = this.getMaxDeficit(new String[]{"metals", "heavy_machinery"});
        int maxDeficit = size - 3;
        if ((Integer)deficit.two > maxDeficit) {
            deficit.two = maxDeficit;
        }

        this.applyDeficitToProduction(2, deficit, new String[]{"heavy_machinery", "supplies"});
        if (!this.isFunctional()) {
            this.supply.clear();
            this.unapply();
        }

    }

    public boolean isAvailableToBuild() {
        return true;
    }

    public boolean isDemandLegal(CommodityOnMarketAPI com) {
        return true;
    }

    public boolean isSupplyLegal(CommodityOnMarketAPI com) {
        return true;
    }

    public boolean showWhenUnavailable() {
        return true;
    }

    public String getUnavailableReason() {
        return "Needs to be allied with Domain FOB";
    }

    protected boolean canImproveToIncreaseProduction() {
        return true;
    }
}