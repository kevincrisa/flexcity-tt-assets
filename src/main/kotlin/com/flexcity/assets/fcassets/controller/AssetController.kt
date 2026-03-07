package com.flexcity.assets.fcassets.controller

import com.flexcity.assets.fcassets.application.AssetService
import com.flexcity.assets.fcassets.domain.AssetRequest
import com.flexcity.assets.fcassets.domain.AvailableAsset
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST controller that exposes endpoints related to assets.
 *
 * Here, Its role is to receive HTTP request, extract the request body,
 * send the request to the service and return the response to the client
 */
@RestController
@RequestMapping("/assets")
class AssetController (private val assetService: AssetService){

    /**
     * Returns the list of assets available based to the request body.
     *
     * Example request:
     * POST /assets/available
     *
     * Body:
     * {
     *   "date": "2026-03-10",
     *   "volume": 80,
     *   "mode": "COST"
     * }
     *
     * mode is optional because the default value is "RATIO"
     *
     * @param assetRequest Body of the request which contains selection criteria
     * @return The list of available assets filtered with the request
     */
    @PostMapping("/available")
    fun getAvailableAssets(@RequestBody assetRequest: AssetRequest): List<AvailableAsset> {
        // Return the result the get available assets to the client
        return assetService.getAvailableAssets(assetRequest)
    }
}