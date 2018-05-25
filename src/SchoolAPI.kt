import datastructure.Meal

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.ArrayList

class SchoolAPI(private val region: Region, private val schoolCode: String, private val type: Type) {
    enum class Region constructor(val value: String) {
        SEOUL("stu.sen.go.kr"),
        BUSAN("stu.pen.go.kr"),
        DAEGU("stu.dge.go.kr"),
        INCHEON("stu.ice.go.kr"),
        GWANGJU("stu.gen.go.kr"),
        DAEJEON("stu.dje.go.kr"),
        ULSAN("stu.use.go.kr"),
        SEJONG("stu.sje.go.kr"),
        GYEONGGI("stu.cbe.go.kr"),
        KANGWON("stu.kwe.go.kr"),
        CHUNGBUK("stu.cbe.go.kr"),
        CHUNGNAM("stu.cne.go.kr"),
        JEONBUK("stu.jbe.go.kr"),
        JEONNAM("stu.jne.go.kr"),
        GYEONGBUK("stu.gbe.go.kr"),
        GYEONGNAM("stu.gne.go.kr"),
        JEJU("stu.jje.go.kr")
    }

    enum class Type constructor(val value: Int) {
        KINDERGARTEN(1),
        ELEMENTARY(2),
        MIDDLE(3),
        HIGH(4)
    }

    private fun getFormattedURL(year: Int, month: Int): String {
        return String.format(URL_FORMAT, this.region.value, this.schoolCode, this.type.value, this.type.value, year, month)
    }

    fun getMonthlyMenus(year: Int, month: Int): ArrayList<Meal> {
        val url = URL(getFormattedURL(year, month))

        return parser.parse(getResponseDataFromConnection(url))
    }

    companion object {
        private val URL_FORMAT = "https://%s/sts_sci_md00_001.do?schulCode=%s&schulCrseScCode=%d&schulKndScScore=0%d&schYm=%d%02d"

        private fun getResponseDataFromConnection(url: URL): String {
            val reader = BufferedReader(InputStreamReader(url.openStream()))

            val buffer = StringBuilder()

            var reading = false

            while (true) {
                val inputLine = reader.readLine()

                if (inputLine == null) {
                    break
                }

                if (reading) {
                    if (inputLine.contains("</tbody>"))
                        break
                    buffer.append(inputLine)
                } else {
                    if (inputLine.contains("<tbody>"))
                        reading = true
                }
            }

            reader.close()

            return buffer.toString()
        }
    }
}

