package com.flexcity.assets.fcassets.domain

import jakarta.persistence.*
import java.time.LocalDate

@Entity
data class AssetAvailability(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val date: LocalDate,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_code")
    val asset: Asset
)
