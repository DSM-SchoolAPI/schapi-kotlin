package parser

import datastructure.Meal
import java.util.regex.Pattern

fun parse(rawData: String): ArrayList<Meal> {
    if (rawData.isEmpty()) {
        return ArrayList()
    }

    val monthlyMenu = ArrayList<Meal>()
    monthlyMenu.add(Meal())

    val newRawData = rawData.replace("\\s+".toRegex(), "")

    val sb = StringBuilder()
    var inDiv = false
    var i = 0

    while (i < newRawData.length) {
        if (newRawData[i] == 'v') {
            if (inDiv) {
                sb.delete(sb.length - 4, sb.length)
                if (sb.isNotEmpty()) {
                    monthlyMenu.add(parseDay(sb.toString()))
                }
                sb.setLength(0)
            } else {
                i++
            }
            inDiv = !inDiv
        } else if (inDiv) {
            sb.append(newRawData[i])
        }

        i++
    }

    return monthlyMenu
}

fun parseDay(rawData: String): Meal {
    println(rawData)
    var meal = Meal()

    val newRawData = rawData.replace("(석식)", "").replace("(선)", "")
    val chunks = newRawData.split("<br/>")

    var parsingMode = 0

    for (chunk in chunks) {
        if (chunk.trim({ it <= ' ' }).isEmpty())
            continue

        if (chunk == "[조식]") {
            parsingMode = 0

            continue
        } else if (chunk == "[중식]") {
            parsingMode = 1

            continue
        } else if (chunk == "[석식]") {
            parsingMode = 2

            continue
        } else if (chunk.matches("\\d+".toRegex())) {
            continue
        }

        val matcher = Pattern.compile("[가-힇]+\\(\\[가-힇]+\\)|[가-힇]+").matcher(chunk)
        matcher.find()

        val newChunk = matcher.group()

        when (parsingMode) {
            0 -> meal.breakfast.add(newChunk)
            1 -> meal.lunch.add(newChunk)
            2 -> meal.dinner.add(newChunk)
        }
    }

    return meal
}
