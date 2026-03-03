package com.flexcity.assets.fcassets.domain

interface AssetRequestStrategy {
    fun modeSupported(mode: CalculationMode): Boolean
    fun select(assets: List<Asset>, request: AssetRequest): List<AvailableAsset>
}