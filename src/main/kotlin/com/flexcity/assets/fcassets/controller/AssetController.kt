package com.flexcity.assets.fcassets.controller

import com.flexcity.assets.fcassets.application.AssetService
import com.flexcity.assets.fcassets.domain.AssetRequest
import com.flexcity.assets.fcassets.domain.AvailableAsset
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/assets")
class AssetController (private val assetService: AssetService){

    @PostMapping("/available")
    fun retrieveAvailableAssets(@Valid @RequestBody assetRequest: AssetRequest): List<AvailableAsset> {
        return assetService.retrieveAvailableAssets(assetRequest)
    }
}