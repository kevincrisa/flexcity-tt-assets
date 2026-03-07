package com.flexcity.assets.fcassets.domain

/**
 * Utility object to select assets from a sorted list
 */
object AssetsSearchHelper {

    /**
     * Fill the requested volume from the sorted list of assets
     *
     * @param sortedAssets already sorted according to strategy
     * @param requestedVolume total volume requested
     * @return List of AvailableAsset fulfilling the volume
     * @throws IllegalArgumentException if volume cannot be fulfilled
     */
    fun selectAssetsToFillVolume(sortedAssets: List<Asset>, requestedVolume: Int): List<AvailableAsset> {
        // Requested volume will be used for check if we need to fill the assets selected list
        var remainingVolume = requestedVolume
        // List which will contain assets selected
        val selectedAssets = mutableListOf<AvailableAsset>()

        // Iteration in sorted available assets to fill the list
        for (asset in sortedAssets) {
            // If the request volume has been satisfied, stop the loop
            if (remainingVolume <= 0) break
            val volumeToTake = minOf(asset.volume, remainingVolume)
            // Add current asset in the assets selected list
            selectedAssets.add(AvailableAsset(asset.code, volumeToTake, asset.activationCost))
            // Decrease the asset volume to requested volume
            remainingVolume -= volumeToTake
        }

        // If after selecting all possible assets we still need volume,
        // it means the request cannot be satisfied
        if (remainingVolume > 0) {
            throw IllegalArgumentException(
                "The available assets volume is insufficient for the requested volume"
            )
        }

        // Return list of assets selected
        return selectedAssets
    }

}