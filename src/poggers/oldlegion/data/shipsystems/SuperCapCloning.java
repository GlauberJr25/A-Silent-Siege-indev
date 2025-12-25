package poggers.oldlegion.data.shipsystems;

import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import com.fs.starfarer.api.plugins.ShipSystemStatsScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import static com.fs.starfarer.api.combat.DamageType.HIGH_EXPLOSIVE;

import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.VectorUtils;
import org.lazywizard.lazylib.combat.CombatUtils;
import org.lazywizard.lazylib.combat.AIUtils;
import org.lazywizard.lazylib.combat.entities.SimpleEntity;
import org.lwjgl.opencl.CLCommandQueue;
import org.lwjgl.util.vector.Vector2f;
import java.awt.Color;

public class SuperCapCloning extends BaseShipSystemScript {
    private Color color = new Color(100,255,100,255);
    public static final Color VENT = new Color(89, 255, 117,155);
    public static Color EMP_CORE = new Color(152, 255, 236, 255);

    private boolean clone = false;
    boolean paradox = false;

    private boolean boom = false;

    public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {

        if (stats.getEntity() instanceof ShipAPI) {
            ShipAPI ship = (ShipAPI) stats.getEntity();

            if (!clone) {
                clone = true;
                Global.getCombatEngine().getFleetManager(ship.getOwner()).setSuppressDeploymentMessages(true);
                Global.getCombatEngine().getFleetManager(ship.getOwner()).spawnShipOrWing("Domain_SuperCap_wing", ship.getLocation(), ship.getFacing());
                Global.getCombatEngine().getFleetManager(ship.getOwner()).setSuppressDeploymentMessages(false);
                ship.getFluxTracker().setCurrFlux(ship.getCurrFlux());
                ship.getFluxTracker().setHardFlux(ship.getFluxTracker().getHardFlux());
                if (ship.getHitpoints() <= 0) {
                    Global.getCombatEngine().applyDamage (ship, ship.getLocation(), 10, HIGH_EXPLOSIVE, 9999, true, false, ship);
                }
                if (ship.isHulk()) {
                    if (!boom) {
                        boom = true;
                    }
                }
            }
        }
    }
    public void unapply(MutableShipStatsAPI stats, String id) {
        clone = false;
        paradox = false;
    }
}
