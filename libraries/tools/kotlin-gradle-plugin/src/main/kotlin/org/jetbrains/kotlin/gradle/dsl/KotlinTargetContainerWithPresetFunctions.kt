/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.dsl

import groovy.lang.Closure
import org.gradle.util.ConfigureUtil
import org.jetbrains.kotlin.gradle.plugin.KotlinTargetsContainerWithPresets
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTargetPreset
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinJsCompilation
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinJsTargetPreset
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinJvmCompilation
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinJvmTargetPreset
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetPreset
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinOnlyTarget

// DO NOT EDIT MANUALLY! Generated by org.jetbrains.kotlin.generators.gradle.dsl.MppPresetFunctionsCodegenKt

interface KotlinTargetContainerWithPresetFunctions : KotlinTargetsContainerWithPresets {

    fun jvm(
        name: String = "jvm",
        configure: KotlinOnlyTarget<KotlinJvmCompilation>.() -> Unit = { }
    ): KotlinOnlyTarget<KotlinJvmCompilation> =
        configureOrCreate(
            name,
            presets.getByName("jvm") as KotlinJvmTargetPreset,
            configure
        )

    fun jvm() = jvm("jvm") { }
    fun jvm(name: String) = jvm(name) { }
    fun jvm(name: String, configure: Closure<*>) = jvm(name) { ConfigureUtil.configure(configure, this) }
    fun jvm(configure: Closure<*>) = jvm { ConfigureUtil.configure(configure, this) }

    fun js(
        name: String = "js",
        configure: KotlinOnlyTarget<KotlinJsCompilation>.() -> Unit = { }
    ): KotlinOnlyTarget<KotlinJsCompilation> =
        configureOrCreate(
            name,
            presets.getByName("js") as KotlinJsTargetPreset,
            configure
        )

    fun js() = js("js") { }
    fun js(name: String) = js(name) { }
    fun js(name: String, configure: Closure<*>) = js(name) { ConfigureUtil.configure(configure, this) }
    fun js(configure: Closure<*>) = js { ConfigureUtil.configure(configure, this) }

    fun android(
        name: String = "android",
        configure: KotlinAndroidTarget.() -> Unit = { }
    ): KotlinAndroidTarget =
        configureOrCreate(
            name,
            presets.getByName("android") as KotlinAndroidTargetPreset,
            configure
        )

    fun android() = android("android") { }
    fun android(name: String) = android(name) { }
    fun android(name: String, configure: Closure<*>) = android(name) { ConfigureUtil.configure(configure, this) }
    fun android(configure: Closure<*>) = android { ConfigureUtil.configure(configure, this) }

    fun androidNativeArm32(
        name: String = "androidNativeArm32",
        configure: KotlinNativeTarget.() -> Unit = { }
    ): KotlinNativeTarget =
        configureOrCreate(
            name,
            presets.getByName("androidNativeArm32") as KotlinNativeTargetPreset,
            configure
        )

    fun androidNativeArm32() = androidNativeArm32("androidNativeArm32") { }
    fun androidNativeArm32(name: String) = androidNativeArm32(name) { }
    fun androidNativeArm32(name: String, configure: Closure<*>) = androidNativeArm32(name) { ConfigureUtil.configure(configure, this) }
    fun androidNativeArm32(configure: Closure<*>) = androidNativeArm32 { ConfigureUtil.configure(configure, this) }

    fun androidNativeArm64(
        name: String = "androidNativeArm64",
        configure: KotlinNativeTarget.() -> Unit = { }
    ): KotlinNativeTarget =
        configureOrCreate(
            name,
            presets.getByName("androidNativeArm64") as KotlinNativeTargetPreset,
            configure
        )

    fun androidNativeArm64() = androidNativeArm64("androidNativeArm64") { }
    fun androidNativeArm64(name: String) = androidNativeArm64(name) { }
    fun androidNativeArm64(name: String, configure: Closure<*>) = androidNativeArm64(name) { ConfigureUtil.configure(configure, this) }
    fun androidNativeArm64(configure: Closure<*>) = androidNativeArm64 { ConfigureUtil.configure(configure, this) }

    fun iosArm32(
        name: String = "iosArm32",
        configure: KotlinNativeTarget.() -> Unit = { }
    ): KotlinNativeTarget =
        configureOrCreate(
            name,
            presets.getByName("iosArm32") as KotlinNativeTargetPreset,
            configure
        )

    fun iosArm32() = iosArm32("iosArm32") { }
    fun iosArm32(name: String) = iosArm32(name) { }
    fun iosArm32(name: String, configure: Closure<*>) = iosArm32(name) { ConfigureUtil.configure(configure, this) }
    fun iosArm32(configure: Closure<*>) = iosArm32 { ConfigureUtil.configure(configure, this) }

    fun iosArm64(
        name: String = "iosArm64",
        configure: KotlinNativeTarget.() -> Unit = { }
    ): KotlinNativeTarget =
        configureOrCreate(
            name,
            presets.getByName("iosArm64") as KotlinNativeTargetPreset,
            configure
        )

    fun iosArm64() = iosArm64("iosArm64") { }
    fun iosArm64(name: String) = iosArm64(name) { }
    fun iosArm64(name: String, configure: Closure<*>) = iosArm64(name) { ConfigureUtil.configure(configure, this) }
    fun iosArm64(configure: Closure<*>) = iosArm64 { ConfigureUtil.configure(configure, this) }

    fun iosX64(
        name: String = "iosX64",
        configure: KotlinNativeTarget.() -> Unit = { }
    ): KotlinNativeTarget =
        configureOrCreate(
            name,
            presets.getByName("iosX64") as KotlinNativeTargetPreset,
            configure
        )

    fun iosX64() = iosX64("iosX64") { }
    fun iosX64(name: String) = iosX64(name) { }
    fun iosX64(name: String, configure: Closure<*>) = iosX64(name) { ConfigureUtil.configure(configure, this) }
    fun iosX64(configure: Closure<*>) = iosX64 { ConfigureUtil.configure(configure, this) }

    fun linuxX64(
        name: String = "linuxX64",
        configure: KotlinNativeTarget.() -> Unit = { }
    ): KotlinNativeTarget =
        configureOrCreate(
            name,
            presets.getByName("linuxX64") as KotlinNativeTargetPreset,
            configure
        )

    fun linuxX64() = linuxX64("linuxX64") { }
    fun linuxX64(name: String) = linuxX64(name) { }
    fun linuxX64(name: String, configure: Closure<*>) = linuxX64(name) { ConfigureUtil.configure(configure, this) }
    fun linuxX64(configure: Closure<*>) = linuxX64 { ConfigureUtil.configure(configure, this) }

    fun linuxArm32Hfp(
        name: String = "linuxArm32Hfp",
        configure: KotlinNativeTarget.() -> Unit = { }
    ): KotlinNativeTarget =
        configureOrCreate(
            name,
            presets.getByName("linuxArm32Hfp") as KotlinNativeTargetPreset,
            configure
        )

    fun linuxArm32Hfp() = linuxArm32Hfp("linuxArm32Hfp") { }
    fun linuxArm32Hfp(name: String) = linuxArm32Hfp(name) { }
    fun linuxArm32Hfp(name: String, configure: Closure<*>) = linuxArm32Hfp(name) { ConfigureUtil.configure(configure, this) }
    fun linuxArm32Hfp(configure: Closure<*>) = linuxArm32Hfp { ConfigureUtil.configure(configure, this) }

    fun linuxMips32(
        name: String = "linuxMips32",
        configure: KotlinNativeTarget.() -> Unit = { }
    ): KotlinNativeTarget =
        configureOrCreate(
            name,
            presets.getByName("linuxMips32") as KotlinNativeTargetPreset,
            configure
        )

    fun linuxMips32() = linuxMips32("linuxMips32") { }
    fun linuxMips32(name: String) = linuxMips32(name) { }
    fun linuxMips32(name: String, configure: Closure<*>) = linuxMips32(name) { ConfigureUtil.configure(configure, this) }
    fun linuxMips32(configure: Closure<*>) = linuxMips32 { ConfigureUtil.configure(configure, this) }

    fun linuxMipsel32(
        name: String = "linuxMipsel32",
        configure: KotlinNativeTarget.() -> Unit = { }
    ): KotlinNativeTarget =
        configureOrCreate(
            name,
            presets.getByName("linuxMipsel32") as KotlinNativeTargetPreset,
            configure
        )

    fun linuxMipsel32() = linuxMipsel32("linuxMipsel32") { }
    fun linuxMipsel32(name: String) = linuxMipsel32(name) { }
    fun linuxMipsel32(name: String, configure: Closure<*>) = linuxMipsel32(name) { ConfigureUtil.configure(configure, this) }
    fun linuxMipsel32(configure: Closure<*>) = linuxMipsel32 { ConfigureUtil.configure(configure, this) }

    fun mingwX64(
        name: String = "mingwX64",
        configure: KotlinNativeTarget.() -> Unit = { }
    ): KotlinNativeTarget =
        configureOrCreate(
            name,
            presets.getByName("mingwX64") as KotlinNativeTargetPreset,
            configure
        )

    fun mingwX64() = mingwX64("mingwX64") { }
    fun mingwX64(name: String) = mingwX64(name) { }
    fun mingwX64(name: String, configure: Closure<*>) = mingwX64(name) { ConfigureUtil.configure(configure, this) }
    fun mingwX64(configure: Closure<*>) = mingwX64 { ConfigureUtil.configure(configure, this) }

    fun mingwX86(
        name: String = "mingwX86",
        configure: KotlinNativeTarget.() -> Unit = { }
    ): KotlinNativeTarget =
        configureOrCreate(
            name,
            presets.getByName("mingwX86") as KotlinNativeTargetPreset,
            configure
        )

    fun mingwX86() = mingwX86("mingwX86") { }
    fun mingwX86(name: String) = mingwX86(name) { }
    fun mingwX86(name: String, configure: Closure<*>) = mingwX86(name) { ConfigureUtil.configure(configure, this) }
    fun mingwX86(configure: Closure<*>) = mingwX86 { ConfigureUtil.configure(configure, this) }

    fun macosX64(
        name: String = "macosX64",
        configure: KotlinNativeTarget.() -> Unit = { }
    ): KotlinNativeTarget =
        configureOrCreate(
            name,
            presets.getByName("macosX64") as KotlinNativeTargetPreset,
            configure
        )

    fun macosX64() = macosX64("macosX64") { }
    fun macosX64(name: String) = macosX64(name) { }
    fun macosX64(name: String, configure: Closure<*>) = macosX64(name) { ConfigureUtil.configure(configure, this) }
    fun macosX64(configure: Closure<*>) = macosX64 { ConfigureUtil.configure(configure, this) }

    fun wasm32(
        name: String = "wasm32",
        configure: KotlinNativeTarget.() -> Unit = { }
    ): KotlinNativeTarget =
        configureOrCreate(
            name,
            presets.getByName("wasm32") as KotlinNativeTargetPreset,
            configure
        )

    fun wasm32() = wasm32("wasm32") { }
    fun wasm32(name: String) = wasm32(name) { }
    fun wasm32(name: String, configure: Closure<*>) = wasm32(name) { ConfigureUtil.configure(configure, this) }
    fun wasm32(configure: Closure<*>) = wasm32 { ConfigureUtil.configure(configure, this) }

}