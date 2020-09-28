package jces1209.vu

import java.io.StringReader
import javax.json.Json
import javax.json.JsonReader

class TrafficDataParser {

    companion object {

        fun parseData(hostName: String, readEnvTrafficShapeConfig: String): String {

            val jsonReader: JsonReader = Json.createReader(StringReader(readEnvTrafficShapeConfig))
            val obj: javax.json.JsonObject = jsonReader.readObject()
            val propertiesFileName: String = obj.getString(hostName)
            jsonReader.close()
            return propertiesFileName
        }


    }
}
