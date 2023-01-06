package com.ersubhadip.helpers

import kotlin.random.Random

class RandomQouteProvider {
    private val list = listOf(
        "A woman with a voice is, by definition, a strong woman.",
        "Behind every great woman... is another great woman.",
        "No woman should be made to fear that she was not enough.",
        "I never dreamed about success. I worked for it.",
        "A girl should be two things: who and what she wants.",
        "Good girls go to heaven, bad girls go everywhere.",
        "In life there is no real safety except for self-belief.",
        "No one can make you feel inferior without your consent.",
        "Sí se puede!” (“Yes we can!”)",
        "I have a dream."
    )

    fun getRandomQuotes(): String {
        return list[(0..list.size).random()]
    }
}