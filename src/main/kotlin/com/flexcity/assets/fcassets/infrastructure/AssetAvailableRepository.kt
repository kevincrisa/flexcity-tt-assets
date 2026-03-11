package com.flexcity.assets.fcassets.infrastructure

import com.flexcity.assets.fcassets.domain.AssetAvailability
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface AssetAvailabilityRepository : JpaRepository<AssetAvailability, Long> {
    fun findByDate(date: LocalDate): List<AssetAvailability>
}