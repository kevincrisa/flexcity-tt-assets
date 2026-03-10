package com.flexcity.assets.fcassets.application

import com.flexcity.assets.fcassets.domain.Asset
import com.flexcity.assets.fcassets.domain.AvailableAsset
import org.springframework.stereotype.Service

/**
 * Service to select assets from a sorted list to fill a requested volume.
 */
@Service
class AssetSelectionService {

    fun selectAssets(assets: List<Asset>, requestedVolume: Int): List<AvailableAsset> {
        var remainingVolume = requestedVolume
        val selectedAssets = mutableListOf<AvailableAsset>()

        for (asset in assets) {
            if (remainingVolume <= 0) break
            val volumeToTake = minOf(asset.volume, remainingVolume)
            selectedAssets.add(AvailableAsset(asset.code, volumeToTake, asset.activationCost))
            remainingVolume -= volumeToTake
        }

        require(remainingVolume == 0) {
            "The available assets volume is insufficient for the requested volume"
        }

        return selectedAssets
    }
}