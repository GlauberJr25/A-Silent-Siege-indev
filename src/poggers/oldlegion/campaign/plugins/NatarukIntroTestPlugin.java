package poggers.oldlegion.campaign.plugins;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.InteractionDialogPlugin;
import com.fs.starfarer.api.campaign.OptionPanelAPI;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.campaign.VisualPanelAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;
import java.awt.Color;
import java.util.Map;

public class NatarukIntroTestPlugin implements InteractionDialogPlugin {
    protected InteractionDialogAPI dialog;
    protected TextPanelAPI textPanel;
    protected OptionPanelAPI options;
    protected VisualPanelAPI visual;
    protected CampaignFleetAPI playerFleet;

    public void init(InteractionDialogAPI dialog) {
        this.dialog = dialog;
        this.textPanel = dialog.getTextPanel();
        this.options = dialog.getOptionPanel();
        this.visual = dialog.getVisualPanel();
        this.playerFleet = Global.getSector().getPlayerFleet();
        this.optionSelected((String)null, NatarukIntroTestPlugin.OptionId.INIT);
    }

    public Map<String, MemoryAPI> getMemoryMap() {
        return null;
    }

    public void backFromEngagement(EngagementResultAPI result) {
    }

    public void optionSelected(String text, Object optionData) {
        if (optionData != null) {
            OptionId option = (OptionId)optionData;
            if (text != null) {
                this.textPanel.addParagraph(text, Global.getSettings().getColor("buttonText"));
            }

            Color dc = Global.getSector().getFaction("domainspecops").getBaseUIColor();

            //case number refer to OptionId at the bottom
            //case 0 is INIT, following cases go down the list
            switch (option.ordinal()) {
                case 0:
                    this.textPanel.addParagraph("You receive an alert. Joel has requested a comm-link.");
                    this.options.clearOptions();
                    this.options.addOption("Accept the comms request", NatarukIntroTestPlugin.OptionId.ACCEPT, (String)null);
                    this.options.addOption("Refuse", NatarukIntroTestPlugin.OptionId.REFUSE, (String)null);
                    break;
                case 1:
                    this.textPanel.addParagraph("Joel's avatar appears on the feed. \"Hey, " + ".\" she says. \"I'm picking up odd signals in this star system. A bit like the ones back in " + ". But these ones...\" She trails off before picking up again. \"They seem to be everywhere around the fleet, not centered on a specific point.\"");
                    this.textPanel.highlightInLastPara(dc, new String[]{"\"Hey, " + ".\"", "\"I'm picking up odd signals in this star system. A bit like the ones back in " + ". But these ones...\"", "\"They seem to be everywhere around the fleet, not centered on a specific point.\""});
                    this.textPanel.addParagraph("\"Not really sure what we can do with that information,\" she admits. \"My suggestions would be... sending out some kind of signal? Scanning for something? I'm sure you'll figure it out.\"");
                    this.textPanel.highlightInLastPara(dc, new String[]{"\"Not really sure what we can do with that information,\"", "\"My suggestions would be... sending out some kind of signal? Scanning for something? I'm sure you'll figure it out.\""});
                    this.options.clearOptions();
                    this.options.addOption("Return to your duties", NatarukIntroTestPlugin.OptionId.CONT, (String)null);
                    break;
                case 2:
                    this.textPanel.addParagraph("You coldly tap the luminescent red 'deny' on your console to deny the commlink. Joel sends a message about half an hour later, apologizing for potentially sending a comms request at an inconvenient time.");
                    this.options.clearOptions();
                    this.options.addOption("Return to your duties", NatarukIntroTestPlugin.OptionId.CONT, (String)null);
                case 3:
                    Global.getSector().setPaused(false);
                    this.dialog.dismiss();
            }
        }
    }

    public void optionMousedOver(String optionText, Object optionData) {
    }

    public void advance(float amount) {
    }

    public Object getContext() {
        return null;
    }

    public static enum OptionId {
        INIT,
        ACCEPT,
        REFUSE,
        CONT;
    }
}
