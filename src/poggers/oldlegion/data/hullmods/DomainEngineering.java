package poggers.oldlegion.data.hullmods;

import java.awt.Color;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.CombatEngineAPI;
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

        //tooltip.addPara("Threat hulls have a number of shared properties.", opad);
        //
        //		tooltip.addSectionHeading("Campaign", Alignment.MID, opad);
        //		tooltip.addPara("Sensor profile reduced to %s.", opad, h, "0");
        //
        //		tooltip.addSectionHeading("Combat", Alignment.MID, opad);
        //		tooltip.addPara("Target leading accuracy increased to maximum for all weapons, including missiles. Effect "
        //				+ "of enemy ECM rating reduced by %s.", opad, h, "" + (int) Math.round(EW_PENALTY_MULT * 100f) + "%");
        //		tooltip.addPara("Weapon and engine damage taken is reduced by %s. EMP damage taken is reduced by %s. In "
        //				+ "addition, repairs of damaged but functional weapons and engines can continue while they are under fire.",
        //				opad, h,
        //				"" + (int) Math.round((1f - MODULE_DAMAGE_TAKEN_MULT) * 100f) + "%",
        //				"" + (int) Math.round((1f - EMP_DAMAGE_TAKEN_MULT) * 100f) + "%");
    }
}
