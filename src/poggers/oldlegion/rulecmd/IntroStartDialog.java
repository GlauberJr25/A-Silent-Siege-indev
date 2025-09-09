package poggers.oldlegion.rulecmd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class IntroStartDialog extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        if (dialog == null) return false;

        TextPanelAPI textPanel = dialog.getTextPanel();
        OptionPanelAPI options = dialog.getOptionPanel();
        VisualPanelAPI visual = dialog.getVisualPanel();
        CampaignFleetAPI playerFleet = Global.getSector().getPlayerFleet();
        Color dc = Global.getSector().getFaction("domainspecops").getBaseUIColor();
        Color pc = Global.getSector().getPlayerFaction().getColor();

        textPanel.addParagraph("The bridge briefly falls silent in stupor before the navigation team scrambles to triple checks their calculations, you can hear the low grumble of them calmly but firmly arguing among themselves. They are interrupted shortly after by the sensors team. \"We've got unknown signatures on an interception course, sir.\" The sensors officer says, with only a hint of worry in their voice.");

        return false;
    }
}
