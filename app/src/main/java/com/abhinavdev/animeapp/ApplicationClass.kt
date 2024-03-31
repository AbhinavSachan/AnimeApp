package com.abhinavdev.animeapp

import android.app.Application
import android.content.Context
import com.abhinavdev.animeapp.remote.kit.ApiClient
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.LocaleHelper
import com.abhinavdev.animeapp.util.PrefUtils
import com.google.firebase.crashlytics.BuildConfig
import com.google.firebase.crashlytics.FirebaseCrashlytics

class ApplicationClass : Application() {

	companion object {
		private lateinit var sInstance: ApplicationClass

		fun getInstance(): ApplicationClass {
			return sInstance
		}
	}

	override fun onCreate() {
		super.onCreate()
		sInstance = this

		ApiClient.init()
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)

//		Branch.getAutoInstance(this)// Branch object initialization
		//Branch.enableTestMode() // Branch logging for debugging
//		Branch.disableDebugMode()
//		Branch.disableLogging()

		// Enable verbose OneSignal logging to debug issues if needed.
//        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

		// OneSignal Initialization
//        OneSignal.initWithContext(this)
//        OneSignal.setAppId(getString(R.string.one_signal_app_id))
//		OneSignal.setAppId(getString(R.string.one_signal_app_id_dev))
//        OneSignal.setNotificationOpenedHandler(NotificationHandler(applicationContext))

		// promptForPushNotifications will show the native Android notification permission prompt.
		// We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
//		OneSignal.promptForPushNotifications()
//		Log.d(
//			"SKK",
//			"Successfully registered for push notifications with token: " + OneSignal.getDeviceState()?.pushToken
//		)
	}

	/*private fun setOneSignalClickEvent() {
		OneSignal.Notifications.addClickListener(object : INotificationClickListener {
			override fun onClick(event: INotificationClickEvent) {
				try {
					val jsonObject =
						JSONObject(event.notification.rawPayload).getString("custom")
					val additionData = JSONObject(JSONObject(jsonObject).getString("a"))
					var targetId = ""
					if (additionData.has("target_id"))
						targetId = additionData.getString("target_id")
					var nameEn = ""
					if (additionData.has("name_en"))
						nameEn = additionData.getString("name_en")
					var nameAr = ""
					if (additionData.has("name_ar"))
						nameAr = additionData.getString("name_ar")
					var target = ""
					if (additionData.has("target"))
						target = additionData.getString("target")

					val intent = Intent(applicationContext, MainActivity::class.java)
					intent.putExtra(Const.BundleExtras.FROM_PUSH, true)
					intent.putExtra(Const.BundleExtras.PUSH_TARGET, target)
					intent.putExtra(Const.BundleExtras.PUSH_TARGET_ID, targetId)
					intent.putExtra(Const.BundleExtras.PUSH_NAME_EN, nameEn)
					intent.putExtra(Const.BundleExtras.PUSH_NAME_AR, nameAr)
					intent.flags =
						Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
					applicationContext.startActivity(intent)
				} catch (e: Exception) {
					val intent = Intent(applicationContext, MainActivity::class.java)
					intent.flags =
						Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
					applicationContext.startActivity(intent)
				}
			}
		})
	}*/

	override fun attachBaseContext(base: Context) {
		var lang = PrefUtils.getStringWithContext(base, Const.SharedPrefs.SELECTED_LANGUAGE_CODE)
		if (lang == null || lang == "")
			lang = Const.Language.ENGLISH_LANG_CODE
		super.attachBaseContext(LocaleHelper.onAttach(base, lang))
	}
}