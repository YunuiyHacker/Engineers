package yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
object OneCDateFormat: SimpleDateFormat("dd.MM.yyyy HH:mm:ss") {
    private fun readResolve(): Any = OneCDateFormat
}

@SuppressLint("SimpleDateFormat")
object OneCDateFormatWithPlus: SimpleDateFormat("dd.MM.yyyy+HH:mm:ss") {
    private fun readResolve(): Any = OneCDateFormatWithPlus
}


@SuppressLint("SimpleDateFormat")
object HomeFilteringDateFormat: SimpleDateFormat("dd.MM.yyyy") {
    private fun readResolve(): Any = HomeFilteringDateFormat
}