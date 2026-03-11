package com.flexcity.assets.fcassets.domain

import com.flexcity.assets.fcassets.application.AssetSelectionService
import org.springframework.stereotype.Component

/**
 * Select the assets by sorting them by increasing cost-per-volume ratio and entering the requested volume.
 */
@Component
class AssetsSearchByCostPerVolume(
    private val assetSelectionService: AssetSelectionService
): AssetRequestStrategy {

    override fun modeSupported(calculationMode: CalculationMode) =
        calculationMode == CalculationMode.RATIO

    override fun select(
        assetsList: List<Asset>,
        assetRequest: AssetRequest
    ): List<AvailableAsset> {
        val assetsSortedByCostPerVolume =
            assetsList.sortedBy { it.activationCost / it.volume.toDouble() }

        return assetSelectionService.selectAssets(assetsSortedByCostPerVolume, assetRequest.requestedVolume)
    }
}