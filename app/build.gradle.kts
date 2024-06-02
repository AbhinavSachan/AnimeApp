import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.devtools.ksp")
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").reader())

android {
    signingConfigs {
        create("release") {
            storeFile = rootProject.file(properties.getProperty("file_name"))
            storePassword = properties.getProperty("password")
            keyAlias = properties.getProperty("alias")
            keyPassword = properties.getProperty("password")
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

        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            buildConfigField("String", "CLIENT_ID", properties.getProperty("CLIENT_ID"))
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
        buildConfig = true
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
    bundle{
        language {
            @Suppress("UnstableApiUsage")
            enableSplit = false
        }
    }
    lint {
        checkReleaseBuilds = true
        // Or, if you prefer, you can continue to check for errors in release builds,
        // but continue the build even when errors are found:
        abortOnError = false
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.browser)
    implementation(libs.core.splashscreen)
    implementation(libs.appcompat)
    implementation(libs.asynclayoutinflater)
    implementation(libs.work.runtime)
    implementation(libs.constraintlayout)
    implementation(libs.swiperefreshlayout)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    implementation(libs.material)

    //Life Cycle
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.extensions)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)

    //Gson
    implementation(libs.gson)

    //okhttp and logging interceptor
    implementation(libs.okhttp)
    implementation(libs.converter.gson)
    implementation(libs.retrofit) {
        exclude(module = "okhttp")
    }

    //Dimension
    implementation(libs.sdp.android)
    implementation(libs.ssp.android)

    //json loader
    implementation(libs.lottie)

    //Glide image library
    implementation(libs.glide)
    ksp(libs.glide.compiler)

    //calendar de-sugaring library
    coreLibraryDesugaring(libs.desugar.jdk.libs)


    //firebase libs
//    implementation ("com.google.firebase:firebase-messaging-ktx")
//    implementation ("com.google.firebase:firebase-installations-ktx")
//    implementation ("com.google.firebase:firebase-messaging")
//    implementation ("com.google.firebase:firebase-core")
//    implementation ("com.google.firebase:firebase-auth")
    implementation(libs.firebase.crashlytics)

    //play video
//    implementation ("com.google.android.exoplayer:exoplayer:2.18.7")

    //recyclerview with indicator
//    implementation ("ru.tinkoff.scrollingpagerindicator:scrollingpagerindicator:1.2.3")

    //Force Update Google Play Store
//    implementation ("com.google.android.play:core:1.10.3")

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

    implementation(libs.okhttpprofiler)
    implementation(libs.palette.ktx)
    //shimmer loading library
    implementation(libs.shimmer)
    //some fancy switch
    implementation(libs.switcher)
    //zoom image view
    implementation(libs.zoomage)
    implementation(libs.expandabletextview)
    implementation(libs.flycotablayout)
}
//https://docs.consumet.org/#tag/anilist