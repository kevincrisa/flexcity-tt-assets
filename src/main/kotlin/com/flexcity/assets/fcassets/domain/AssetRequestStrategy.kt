package com.flexcity.assets.fcassets.domain

/**
 * Defines selection strategy of assets calculation mode
 */
interface AssetRequestStrategy {
    /**
     * Return true is the strategy supports this calculation mode
     */
    fun modeSupported(calculationMode: CalculationMode): Boolean
    /**
     * Selects the relevant assets according to the strategy and the query
     */
    fun select(assetsList: List<Asset>, assetRequest: AssetRequest): List<AvailableAsset>
}