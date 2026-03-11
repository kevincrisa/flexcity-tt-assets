package com.flexcity.assets.fcassets.domain

import jakarta.persistence.*

/**
 * Represents an asset in the system.
 */
@Entity
data class Asset (

    @Id
    val code: String,

    val name: String,

    val activationCost: Double,

    val volume: Int,

    @OneToMany(mappedBy = "asset", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val availableDates: List<AssetAvailability> = emptyList()
)