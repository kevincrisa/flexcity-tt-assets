package com.flexcity.assets.fcassets.application

import com.flexcity.assets.fcassets.domain.Asset
import com.flexcity.assets.fcassets.domain.AvailableAsset
import org.springframework.stereotype.Service

@Service
class KnapsackSelectionService {

    fun selectOptimalAssets(
        assets: List<Asset>,
        requestedVolume: Int
    ): List<AvailableAsset> {

        val n = assets.size
        val maxVolume = requestedVolume

        val dp = Array(n + 1) { DoubleArray(maxVolume + 1) { Double.POSITIVE_INFINITY } }
        val take = Array(n + 1) { BooleanArray(maxVolume + 1) }

        dp[0][0] = 0.0

        for (i in 1..n) {
            val asset = assets[i - 1]

            for (v in 0..maxVolume) {

                dp[i][v] = dp[i - 1][v]

                val prevVolume = maxOf(0, v - asset.volume)

                val newCost = dp[i - 1][prevVolume] + asset.activationCost

                if (newCost < dp[i][v]) {
                    dp[i][v] = newCost
                    take[i][v] = true
                }
            }
        }

        return reconstructSolution(assets, take, requestedVolume)
    }

    private fun reconstructSolution(
        assets: List<Asset>,
        take: Array<BooleanArray>,
        requestedVolume: Int
    ): List<AvailableAsset> {

        var volume = requestedVolume
        val result = mutableListOf<AvailableAsset>()

        for (i in assets.size downTo 1) {

            if (take[i][volume]) {

                val asset = assets[i - 1]
                val usedVolume = minOf(asset.volume, volume)

                result.add(
                    AvailableAsset(asset.code, usedVolume, asset.activationCost)
                )

                volume = maxOf(0, volume - asset.volume)
            }
        }

        return result
    }
}