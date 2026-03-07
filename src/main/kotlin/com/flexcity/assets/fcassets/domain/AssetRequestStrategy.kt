package com.flexcity.assets.fcassets.domain

/**
 * Strategy interface used to define how assets should be selected
 * depending on the requested calculation mode.
 *
 * Each implementation represents a different selection algorithm (increasing volume, increasing cost, cost per volume)
 */
interface AssetRequestStrategy {
    /**
     * Indicates if this strategy supports the given calculation mode.
     *
     * This method allows the application to dynamically choose
     * the correct strategy depending on the request.
     *
     * @param mode The calculationMode sent by the request
     * @return Indicates if the mode sent is supported by the strategy
     */
    fun modeSupported(mode: CalculationMode): Boolean
    /**
     * Selects the most relevant assets according to the strategy logic.
     *
     * @param assets The list of all assets
     * @param request The request containing the selection criteria (date, volume requested)
     * @return The list of assets that match the selection criteria
     */
    fun select(assets: List<Asset>, request: AssetRequest): List<AvailableAsset>
}