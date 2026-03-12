package com.flexcity.assets.fcassets.domain

import com.flexcity.assets.fcassets.application.KnapsackSelectionService
import org.springframework.stereotype.Component


/**
 * Select the assets by using Knapsack.
 */
@Component
class AssetsSearchByKnapsack(
    private val knapsackSelectionService: KnapsackSelectionService
): AssetRequestStrategy {

    override fun modeSupported(calculationMode: CalculationMode) =
        calculationMode == CalculationMode.OPTIMAL

    override fun select(
        assetsList: List<Asset>,
        assetRequest: AssetRequest
    ): List<AvailableAsset> {

        return knapsackSelectionService.selectOptimalAssets(
            assetsList,
            assetRequest.requestedVolume
        )
    }

}