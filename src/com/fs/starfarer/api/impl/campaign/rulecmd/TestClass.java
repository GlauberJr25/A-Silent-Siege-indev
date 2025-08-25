package com.fs.starfarer.api.impl.campaign.rulecmd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.util.Misc;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class TestClass extends BaseCommandPlugin {
    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        if (dialog == null) return false;

        TextPanelAPI textPanel = dialog.getTextPanel();
        OptionPanelAPI options = dialog.getOptionPanel();
        VisualPanelAPI visual = dialog.getVisualPanel();
        CampaignFleetAPI playerFleet = Global.getSector().getPlayerFleet();
        Color dc = Global.getSector().getFaction("domainspecops").getBaseUIColor();

        textPanel.addParagraph("Joel's avatar appears on the feed. \"Hey, " + ".\" she says. \"I'm picking up odd signals in this star system. A bit like the ones back in " + ". But these ones...\" She trails off before picking up again. \"They seem to be everywhere around the fleet, not centered on a specific point.\"");
        textPanel.highlightInLastPara(dc, new String[]{"\"Hey, " + ".\"", "\"I'm picking up odd signals in this star system. A bit like the ones back in " + ". But these ones...\"", "\"They seem to be everywhere around the fleet, not centered on a specific point.\""});
        textPanel.addParagraph("\"Not really sure what we can do with that information,\" she admits. \"My suggestions would be... sending out some kind of signal? Scanning for something? I'm sure you'll figure it out.\"");
        textPanel.highlightInLastPara(dc, new String[]{"\"Not really sure what we can do with that information,\"", "\"My suggestions would be... sending out some kind of signal? Scanning for something? I'm sure you'll figure it out.\""});

        return false;
    }
}
