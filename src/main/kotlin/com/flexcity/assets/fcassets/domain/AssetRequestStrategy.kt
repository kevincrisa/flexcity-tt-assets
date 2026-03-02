package com.flexcity.assets.fcassets.domain

interface AssetRequestStrategy {
    fun select(assets: List<Asset>, request: AssetRequest): List<AvailableAsset>
}