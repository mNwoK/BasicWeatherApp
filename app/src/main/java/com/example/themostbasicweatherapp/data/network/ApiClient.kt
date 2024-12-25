package com.example.themostbasicweatherapp.data.network

import android.util.Log
import com.example.themostbasicweatherapp.data.network.model.CurrentDTO
import com.example.themostbasicweatherapp.data.network.model.Forecast
import com.example.themostbasicweatherapp.data.network.model.ForecastDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.http.parameters

class ApiClient(private val httpClient: HttpClient) {
  val baseURL = "https://api.weatherapi.com/v1"

  suspend fun getCurrent(token: String, city: String): CurrentDTO? {
    Log.d("tema", "i tried")
    return try {
      httpClient
          .get(urlString = "$baseURL/current.json") {
            // headers { append("Authorization", "Bearer $token") }
            url {
              parameters.append("q", city)
              parameters.append("lang", "ru")
              parameters.append("key", token)
            }
          }
          .body()
    } catch (e: Exception) {
      e.printStackTrace()
        Log.d("tema", "exeption")
        null
    }
  }

  suspend fun getForecast(token: String, city: String): ForecastDTO? {
    return try {
      httpClient
        .get(urlString = "$baseURL/forecast.json") {
          // headers { append("Authorization", "Bearer $token") }
          url {
            parameters.append("q", city)
            parameters.append("days", "10")
            parameters.append("lang", "ru")
            parameters.append("key", token)
          }
        }
        .body()
    } catch (e: Exception) {
      e.printStackTrace()
      Log.d("tema", "exeption")
      null
    }
  }
}
/*
suspend fun getCurrencies(token: String, url: ApiBaseUrl): CurrencyDTO {
        return client.post(url.url + "tinkoff.public.invest.api.contract.v1.InstrumentsService/Currencies"){
            contentType(ContentType.Application.Json)
            setBody(InstrumentRequestBody(InstrumentStatus.INSTRUMENT_STATUS_UNSPECIFIED))
            headers {
                append("Authorization", "Bearer $token")
            }
        }.body()
 */


// https://api.weatherapi.com/v1/forecast.json?q=&days=5&lang=ru&key=6e6412f2a1ad47bda2c121751242412
// https://api.weatherapi.com/v1/forecast.json?q=moscow&
// lang=ru&
// key=6e6412f2a1ad47bda2c121751242412&
// days=7