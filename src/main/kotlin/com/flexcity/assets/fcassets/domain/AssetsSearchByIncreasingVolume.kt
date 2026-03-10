package com.flexcity.assets.fcassets.domain

import com.flexcity.assets.fcassets.application.AssetSelectionService
import org.springframework.stereotype.Component

/**
 * Select the assets by sorting them by increasing volume and entering the requested volume.
 */
@Component
class AssetsSearchByIncreasingVolume(
    private val assetSelectionService: AssetSelectionService
): AssetRequestStrategy {

    override fun modeSupported(calculationMode: CalculationMode) =
        calculationMode == CalculationMode.VOLUME

    override fun select(
        assetsList: List<Asset>,
        assetRequest: AssetRequest
    ): List<AvailableAsset> {
        val availableAssets  = assetsList.filter { it.availableDates.contains(assetRequest.activationDate) }
        if (availableAssets .isEmpty()) return emptyList()

        val assetsSortedByVolume = availableAssets.sortedBy { it.volume }

        return assetSelectionService.selectAssets(assetsSortedByVolume, assetRequest.requestedVolume)
    }
}