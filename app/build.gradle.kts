import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android.buildFeatures.buildConfig = true

val properties = Properties()
properties.load(project.rootProject.file("local.properties").reader())

android {
    signingConfigs {
        create("release") {
            storeFile = rootProject.file("AnimeApp_jks.jks")
            storePassword = "Abhinav786"
            keyAlias = "AnimeApp"
            keyPassword = "Abhinav786"
        }
    }
    namespace = "com.abhinavdev.animeapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.abhinavdev.animeapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "CLIENT_ID", properties.getProperty("CLIENT_ID"))
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "CLIENT_ID", properties.getProperty("CLIENT_ID"))
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        //to enable coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4") this library below at line 126
        //we have to enable this option if we don't do this there will be a red carpet (effective warnings) in AppUtils from line 419 to last line
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
    packaging {
        resources {
            excludes.apply {
                add("META-INF/INDEX.LIST")
                add("META-INF/io.netty.versions.properties")
            }
        }
    }
    lint {
        checkReleaseBuilds = false
        // Or, if you prefer, you can continue to check for errors in release builds,
        // but continue the build even when errors are found:
//        abortOnError false
    }
}

dependencies {
    val okhttpVersion = "4.12.0"
    val lifecycleVersion = "2.7.0"
    val navigationVersion = "2.7.7"
    val dimensionVersion = "1.1.1"
    val glideVersion = "4.16.0"
    val retrofitVersion = "2.11.0"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.browser:browser:1.8.0")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.work:work-runtime:2.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    implementation("com.google.android.material:material:1.11.0")

    //Life Cycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

    //Gson
    implementation("com.google.code.gson:gson:2.10.1")

    //okhttp and logging interceptor
    implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttpVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion") {
        exclude(module = "okhttp")
    }

    //Dimension
    implementation("com.intuit.sdp:sdp-android:$dimensionVersion")
    implementation("com.intuit.ssp:ssp-android:$dimensionVersion")

    //json loader
    implementation("com.airbnb.android:lottie:6.4.0")

    //Glide
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    //noinspection KaptUsageInsteadOfKsp
    kapt("com.github.bumptech.glide:compiler:$glideVersion")

    //calendar de-sugaring library
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")


    //firebase libs
//    implementation ("com.google.firebase:firebase-messaging-ktx")
//    implementation ("com.google.firebase:firebase-installations-ktx")
//    implementation ("com.google.firebase:firebase-messaging")
//    implementation ("com.google.firebase:firebase-core")
//    implementation ("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-crashlytics:18.6.4")
    implementation("com.google.firebase:firebase-analytics:21.6.2")


    //page indicator
    implementation("com.tbuonomo:dotsindicator:5.0")

    //play video
//    implementation ("com.google.android.exoplayer:exoplayer:2.18.7")

    //flexbox
//    implementation ("com.google.android.flexbox:flexbox:3.0.0")

    //recyclerview with indicator
//    implementation ("ru.tinkoff.scrollingpagerindicator:scrollingpagerindicator:1.2.3")

    //Force Update Google Play Store
//    implementation ("com.google.android.play:core:1.10.3")

    //Google
//    implementation ("com.google.android.gms:play-services-auth:20.7.0")

    //Facebook
//    implementation ("com.facebook.android:facebook-android-sdk:13.0.0")
//    implementation ("com.facebook.android:facebook-login:13.0.0")

    //One Signal SDK
//    implementation ("com.onesignal:OneSignal:[5.0.0, 5.99.99]")

    //Image Crop Picker
//    implementation("com.vanniktech:android-image-cropper:4.5.0")

    //Camera Preview
//    api ("com.otaliastudios:cameraview:2.7.2")

    //snap time picker
//    implementation ("com.akexorcist:snap-time-picker:1.0.3")

    //tab sync
//    implementation ("io.github.ahmad-hamwi:tabsync:1.0.1")

    //calender
//    implementation("com.github.prolificinteractive:material-calendarview:2.0.1")

    // The view calendar library
//    implementation("com.kizitonwose.calendar:view:2.3.0")

    //otp view
//    implementation("com.github.appsfeature:otp-view:1.0")

    //sliding button
//    implementation("com.ncorti:slidetoact:0.11.0")

    implementation("com.localebro:okhttpprofiler:1.0.8")
    //micro animations util library
    implementation("com.daimajia.androidanimations:library:2.4@aar")
    implementation("androidx.palette:palette-ktx:1.0.0")
    //shimmer loading library
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    //auto image slider library
}
//https://docs.consumet.org/#tag/anilist