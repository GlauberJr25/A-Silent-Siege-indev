package poggers.oldlegion.scripts;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CoreUITabId;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.ui.ButtonAPI;
import com.fs.starfarer.api.ui.UIComponentAPI;
import com.fs.starfarer.api.ui.UIPanelAPI;
import poggers.oldlegion.utils.ReflectionUtils;

import java.util.List;

public class DisableSectorMapButton implements EveryFrameScript {
    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean runWhilePaused() {
        return true;                    // Set to true since being in any Core UI pauses the game
    }

    @Override
    public void advance(float amount) {
        if (CoreUITabId.MAP.equals(Global.getSector().getCampaignUI().getCurrentCoreTab())) {   // Checks if indeed currently on map screen
            UIPanelAPI currentTab = ReflectionUtils.getCurrentTab();
            if (currentTab != null) {
                List<UIComponentAPI> components = ReflectionUtils.getChildrenCopy(currentTab);  // Get all components from the current UIPanelAPI
                if (components != null) {                                                       // Null-check in case the above fails
                    for (UIComponentAPI component : components) {                               // Iterate through all components
                        if (!(component instanceof ButtonAPI sectorMapButton)) return;          // Check if current component is a button
                        if (!sectorMapButton.getText().contains("Sector")) return;              // Check if current button has correct text
                        if (!sectorMapButton.isEnabled()) return;                               // Check if current button is disabled already
                        sectorMapButton.setEnabled(false);                                      // Disable ability to use button
                        break;
                    }
                }
            }
        }
        Global.getSector().removeTransientScriptsOfClass(this.getClass());
    }

}
