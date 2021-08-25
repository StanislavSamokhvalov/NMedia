package ru.netology.nmedia.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object AndroidUtils {
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun counterNumber(amount: Double): String {
    var count = ""
    if (amount < 1000)
        count = String.format("%.0f", amount)
    else if (amount >= 1000 && amount < 10000)
        count = String.format("%.1f", (amount / 1000)) + "K"
    else if (amount >= 10000 && amount < 1000000)
        count = String.format("%.0f", (amount / 1000)) + "K"
    else if (amount >= 1000000 && amount < 1000000000)
        count = String.format("%.1f", (amount / 1000000)) + "M"
    return count

}