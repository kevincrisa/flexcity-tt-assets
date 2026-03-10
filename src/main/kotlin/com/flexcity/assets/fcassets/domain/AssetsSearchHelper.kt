package com.flexcity.assets.fcassets.domain

/**
 * Utility object to select assets from a sorted list to fill a requested volume.
 */
object AssetsSearchHelper {

    fun selectAssetsToFillVolume(sortedAssets: List<Asset>, requestedVolume: Int): List<AvailableAsset> {
        var remainingVolume = requestedVolume
        val selectedAssets = mutableListOf<AvailableAsset>()

        for (asset in sortedAssets) {
            if (remainingVolume <= 0) break
            val volumeToTake = minOf(asset.volume, remainingVolume)
            selectedAssets.add(AvailableAsset(asset.code, volumeToTake, asset.activationCost))
            remainingVolume -= volumeToTake
        }

        if (remainingVolume > 0) {
            throw IllegalArgumentException(
                "The available assets volume is insufficient for the requested volume"
            )
        }

        return selectedAssets
    }

}