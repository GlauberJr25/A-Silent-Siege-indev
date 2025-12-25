package poggers.oldlegion.data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;

public class CloneTimeout extends BaseHullMod {

    public void advanceInCombat(ShipAPI ship, float amount){
        if (ship.getCurrentCR() == 0) {
            ship.setHitpoints(0);
        }
        //for Supercap clone
        if (ship.getHullLevel() <= 0.2f) {
            Global.getCombatEngine().removeEntity(ship);
        }
    }
}