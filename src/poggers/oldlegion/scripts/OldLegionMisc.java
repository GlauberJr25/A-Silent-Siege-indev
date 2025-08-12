package poggers.oldlegion.scripts;

import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.impl.campaign.DerelictShipEntityPlugin;
import com.fs.starfarer.api.impl.campaign.procgen.themes.BaseThemeGenerator;
import com.fs.starfarer.api.impl.campaign.procgen.themes.SalvageSpecialAssigner;
import com.fs.starfarer.api.impl.campaign.rulecmd.salvage.special.ShipRecoverySpecial;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.WeightedRandomPicker;

import java.util.Random;

public class OldLegionMisc {

    public static SectorEntityToken addDerelict(StarSystemAPI system, SectorEntityToken focus, String variantId, ShipRecoverySpecial.ShipCondition condition, float orbitRadius, boolean recoverable) {
        DerelictShipEntityPlugin.DerelictShipData params = new DerelictShipEntityPlugin.DerelictShipData(new ShipRecoverySpecial.PerShipData(variantId, condition), false);
        SectorEntityToken ship = BaseThemeGenerator.addSalvageEntity(system, "wreck", "neutral", params);
        ship.setDiscoverable(true);
        float orbitDays = orbitRadius / (10.0F + (float)Math.random() * 5.0F);
        ship.setCircularOrbit(focus, (float)Math.random() * 360.0F, orbitRadius, orbitDays);
        if (recoverable) {
            SalvageSpecialAssigner.ShipRecoverySpecialCreator creator = new SalvageSpecialAssigner.ShipRecoverySpecialCreator((Random)null, 0, 0, false, (DerelictShipEntityPlugin.DerelictType)null, (WeightedRandomPicker)null);
            Misc.setSalvageSpecial(ship, creator.createSpecial(ship, (SalvageSpecialAssigner.SpecialCreationContext)null));
        }

        return ship;
    }
}
