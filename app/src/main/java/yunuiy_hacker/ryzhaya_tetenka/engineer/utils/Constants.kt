package yunuiy_hacker.ryzhaya_tetenka.engineer.utils

object Constants {
    //OkHttp timeout in milliseconds
    const val CONNECTION_TIMEOUT: Long = 60 * 1000;
    const val READ_TIMEOUT: Long = 30 * 1000;
    const val WRITE_TIMEOUT: Long = 30 * 1000;

    const val ONE_C_API_URL = "https://preferably-brimming-drongo.cloudpub.ru/Partner/hs/repairs/"

    //one c server basic auth data
    const val ONE_C_USERNAME = "Администратор"
    const val ONE_C_PASSWORD = "1"

    //notification properties
    const val NOTIFICATION_CHANNEL_ID = "yunuiy_hacker.ryzhaya_tetenka.engineer"
    const val NOTIFICATION_ID: String = 18.toString()
}