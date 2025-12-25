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
                if (ship.getVariant().hasHullMod("frame7s")) {

                    Global.getSoundPlayer().playSound("realitydisruptor_emp_impact", 1.6f, 3f, ship.getLocation(), ship.getVelocity());

                    for (int i = 0; i < (1 + ship.getSystem().getAmmo()); i++) {
                        Vector2f point1 = MathUtils.getRandomPointInCircle(ship.getLocation(), ship.getCollisionRadius() * 1.5f);
                        Vector2f point2 = MathUtils.getRandomPointInCircle(ship.getLocation(), ship.getCollisionRadius() * 2.5f);
                        Global.getCombatEngine().spawnEmpArc(ship,
                                point1,
                                new SimpleEntity(point1),
                                new SimpleEntity(point2),
                                DamageType.FRAGMENTATION,
                                0f,
                                0f,
                                9999f,
                                null,
                                10f,
                                EMP_CORE, //Central color
                                VENT);//Fringe Color);
                    }
                    for (CombatEntityAPI Ships : AIUtils.getNearbyEnemies(ship,400f))
                        Global.getCombatEngine().spawnEmpArc(ship, MathUtils.getRandomPointOnCircumference(MathUtils.getRandomPointInCircle(ship.getLocation(), 125f), 125f),
                                new SimpleEntity(MathUtils.getRandomPointInCircle(ship.getLocation(), ship.getCollisionRadius() * 1.5f)),
                                Ships,
                                DamageType.ENERGY, //Damage type
                                250f * ship.getSystem().getAmmo(),
                                250f * ship.getSystem().getAmmo(),
                                9999f, //Max range
                                "realitydisruptor_emp_impact", //Impact sound
                                4f * ship.getSystem().getMaxAmmo(), // thickness of the lightning bolt
                                EMP_CORE, //Central color
                                VENT //Fringe Color);
                        );
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
