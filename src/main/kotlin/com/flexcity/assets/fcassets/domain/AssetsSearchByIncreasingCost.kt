package com.flexcity.assets.fcassets.domain

import com.flexcity.assets.fcassets.application.AssetSelectionService
import org.springframework.stereotype.Component

/**
 * Select the assets by sorting them by increasing cost and entering the requested volume.
 */
@Component
class AssetsSearchByIncreasingCost(
    private val assetSelectionService: AssetSelectionService
): AssetRequestStrategy {

    override fun modeSupported(calculationMode: CalculationMode) =
        calculationMode == CalculationMode.COST

    override fun select(
        assetsList: List<Asset>,
        assetRequest: AssetRequest
    ): List<AvailableAsset> {
        val assetsSortedByCost = assetsList.sortedBy { it.activationCost }

        return assetSelectionService.selectAssets(assetsSortedByCost, assetRequest.requestedVolume)
    }
}