package com.flexcity.assets.fcassets.controller

import com.flexcity.assets.fcassets.application.AssetService
import com.flexcity.assets.fcassets.domain.AssetRequest
import com.flexcity.assets.fcassets.domain.AvailableAsset
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import tools.jackson.databind.ObjectMapper

import java.time.LocalDate

@WebMvcTest(AssetController::class)
class AssetControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var assetService: AssetService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun testPostRequestToGetListOfAvailableAssets() {
        val assetRequest = AssetRequest(LocalDate.of(2026,3,10), 50)

        val mockResponse = listOf(
            AvailableAsset("A1", 50, 100.0),
            AvailableAsset("A2", 30, 80.0)
        )

        `when` (assetService.retrieveAvailableAssets(assetRequest)).thenReturn(mockResponse)

        mockMvc.perform(
            post("/assets/available")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(assetRequest))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].code").value("A1"))
            .andExpect(jsonPath("$[0].volume").value(50))
            .andExpect(jsonPath("$[0].activationCost").value(100.0))
            .andExpect(jsonPath("$[1].code").value("A2"))
    }

    @Test
    fun testPostRequestToGetEmptyList() {
        val assetRequest = AssetRequest(LocalDate.of(2026,3,10), 50)

        `when` (assetService.retrieveAvailableAssets(assetRequest)).thenReturn(emptyList())

        mockMvc.perform(
            post("/assets/available")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(assetRequest))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(0))
    }

    @Test
    fun testPostRequestWithEmptyBody() {
        val invalidJson = "{}"

        mockMvc.perform(
            post("/assets/available")
                .contentType("application/json")
                .content(invalidJson)
        )
            .andExpect(status().isBadRequest)
    }


}