package poggers.oldlegion.plugins

import poggers.oldlegion.utils.OldLegionIDs.PRISM_CORE_OFFICER
import com.fs.starfarer.api.PluginPick
import com.fs.starfarer.api.campaign.*
import com.fs.starfarer.api.campaign.CampaignPlugin.PickPriority

class CampaignPluginImpl: BaseCampaignPlugin() {//Loaded on game in the mod plugin, applies officer stats to my Prism Cores

    override fun pickAICoreOfficerPlugin(commodityId: String): PluginPick<AICoreOfficerPlugin>? {
        return when (commodityId) {
            PRISM_CORE_OFFICER -> PluginPick<AICoreOfficerPlugin>(
                PrismCoreOfficerPluginImpl(),
                CampaignPlugin.PickPriority.MOD_SET
            )
            else -> null
        }
    }
}