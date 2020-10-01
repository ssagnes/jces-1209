package jces1209.vu

import java.io.StringReader
import javax.json.Json
import javax.json.JsonReader

class TrafficDataParser {

    companion object {

        fun parseData(hostName: String, readEnvTrafficShapeConfig: String): String {

            val jsonReader: JsonReader = Json.createReader(StringReader(readEnvTrafficShapeConfig))
            var propertiesFileName = ""
            jsonReader.use {
                val obj: javax.json.JsonObject = jsonReader.readObject()
                propertiesFileName = obj.getString(hostName)
            }
            return propertiesFileName
        }


    }
}
