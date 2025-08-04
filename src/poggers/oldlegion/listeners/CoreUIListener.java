package poggers.oldlegion.listeners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.CoreUITabId;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.listeners.CoreUITabListener;
import poggers.oldlegion.scripts.DisableSectorMapButton;
import poggers.oldlegion.scripts.EnableSectorMapButton;

public class CoreUIListener implements CoreUITabListener {
    @Override
    public void reportAboutToOpenCoreTab(CoreUITabId tab, Object param) {

        /// Uncomment the code below and change "corvus" into the system id of the checkpoint
//        CampaignFleetAPI playerFleet = Global.getSector().getPlayerFleet();
//        StarSystemAPI currSystem = playerFleet.getStarSystem();
//        boolean inHyperspace = playerFleet.isInHyperspace();
//        boolean isInCheckpointSystem = currSystem != null && currSystem.getId().equals("corvus");
//
//        if (tab.equals(CoreUITabId.MAP) && isInCheckpointSystem) {
//            Global.getSector().addTransientScript(new DisableSectorMapButton());
//        }
//        if (tab.equals(CoreUITabId.MAP) && (inHyperspace || !isInCheckpointSystem)) {
//            Global.getSector().addTransientScript(new EnableSectorMapButton());
//        }

    }
}
