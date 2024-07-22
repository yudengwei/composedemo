package com.abiao.crane.data

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class CalendarUiState(
    val selectedStartDate: LocalDate? = null,
    val selectedEndDate: LocalDate? = null,
    val animationDirection: AnimationDirection? = null
) {
    val selectedDatesFormatted: String
        get() {
            if (selectedStartDate == null) return ""
            var output = selectedStartDate.format(SHORT_DATE_FORMAT)
            if (selectedEndDate != null) {
                output += " - ${selectedEndDate.format(SHORT_DATE_FORMAT)}"
            }
            return output
        }

    val hasSelectedDates: Boolean
        get() = selectedStartDate != null || selectedEndDate != null

    companion object {
        private val SHORT_DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd")
    }
}
