package poggers.oldlegion.data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipSystemAPI;
import com.fs.starfarer.api.util.IntervalUtil;

import java.awt.*;

public class CloneActivation extends BaseHullMod {

    public static Color JITTER = new Color(151, 207, 130, 160);
    private int index7 = 0;
    public static Color SMOKING_2 = new Color(134, 186, 166, 90);
    public static Color SMOKING = new Color(141, 165, 141, 2);
    private IntervalUtil Interval = new IntervalUtil(10f, 12f);

    public void advanceInCombat(ShipAPI ship, float amount) {
        MutableShipStatsAPI stats = ship.getMutableStats();
        if (ship.isAlive()) {
            if (!ship.getSystem().isStateActive()) {
                ship.getSystem().forceState(ShipSystemAPI.SystemState.ACTIVE,0f);
            }
            if (ship.getFluxLevel() >= 0.99f) {
                Global.getCombatEngine().removeEntity(ship);
            }
        }
    }
}