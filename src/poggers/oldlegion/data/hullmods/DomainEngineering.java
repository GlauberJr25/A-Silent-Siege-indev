package poggers.oldlegion.data.hullmods;

import java.awt.Color;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class DomainEngineering extends BaseHullMod {

    public static float AIM_BONUS = 1f;
    public static float MISSILE_GUIDANCE_BONUS = 1f;
    public static float SENSOR_PROFILE_MULT = 0f;
    public static float EW_PENALTY_MULT = 0.5f;

    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {

        stats.getAutofireAimAccuracy().modifyFlat(id, AIM_BONUS);
        stats.getMissileGuidance().modifyFlat(id, MISSILE_GUIDANCE_BONUS);
        stats.getSensorProfile().modifyMult(id, SENSOR_PROFILE_MULT);

        stats.getDynamic().getMod(Stats.ELECTRONIC_WARFARE_PENALTY_MOD).modifyMult(id, EW_PENALTY_MULT);
    }

    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
        float pad = 3f;
        float opad = 10f;
        Color h = Misc.getHighlightColor();
        Color bad = Misc.getNegativeHighlightColor();
        Color t = Misc.getTextColor();
        Color g = Misc.getGrayColor();

        tooltip.addSectionHeading("Campaign", Alignment.MID, opad);
        tooltip.addPara("Sensor profile reduced to %s.", opad, h, "0");

        tooltip.addSectionHeading("Combat", Alignment.MID, opad);
        tooltip.addPara("Target leading accuracy increased to maximum for all weapons, including missiles. Effect "
                + "of enemy ECM rating reduced by %s.", opad, h, "" + (int) Math.round(EW_PENALTY_MULT * 100f) + "%");
    }

    /*public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return "" + (int) (AIM_BONUS) + "%";
        if (index == 1) return "" + (int) (MISSILE_GUIDANCE_BONUS) + "%";
        if (index == 2) return "" + (int) ((1f - EW_PENALTY_MULT) * 100f) + "%";
        if (index == 3) return "" + (int) (MISSILE_GUIDANCE_BONUS) + "%";
        //if (index == 3) return "" + (int) EW_PENALTY_REDUCTION + "";
        return null;
        // Reduces the chance for missiles launched by the ship to be affected by electronic counter-measures and flares by %s (index 0).
        //
        // A CPU core adjunct in each missile increases missile top speed by %s (index 1) and missile maneuverability by %s (index 2), as well as significantly improving the guidance algorithm.
        //
        // Also reduces the weapon range reduction due to enemy ECM by %s (index 3).
        //
        // When deployed in combat, grants %s/%s/%s/%s (index 4;5;6;7)ECM rating, depending on this ship's hull size.


    }*/
}
