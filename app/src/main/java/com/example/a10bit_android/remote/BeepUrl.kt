package com.example.a10bit_android.remote

object BeepUrl {
    const val BASE_URL = ""

    const val AUTH = "${BASE_URL}auth/"
    const val ATTENDANCE = "${BASE_URL}attendance/"
    const val CLASSROOM= "${BASE_URL}classroom/"

    object Auth {
        const val login = "${AUTH}login"
        const val signup = "${AUTH}signup"
    }

    object Attendance {
        const val reset = "${ATTENDANCE}reset"
        const val check = "${ATTENDANCE}check"
    }

    object Classroom {
        const val lookup = "${CLASSROOM}lookup"
    }

}