/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea

import com.intellij.concurrency.IdeaForkJoinWorkerThreadFactory
import com.intellij.testFramework.ThreadTracker
import java.lang.reflect.Modifier
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.atomic.AtomicBoolean

/*
Workaround for ThreadTracker.checkLeak() failures on TeamCity.

Currently TeamCity runs tests in classloader without boot.jar where IdeaForkJoinWorkerThreadFactory is defined. That
makes ForkJoinPool.commonPool() silently ignores java.util.concurrent.ForkJoinPool.common.threadFactory
(because of java.lang.ClassNotFoundException) option during factory initialization.

Standard names for ForkJoinPool threads doesn't pass ThreadTracker.checkLeak() check and that ruins tests constantly.
As it's allowed to reorder tests on TeamCity and any test can be first at some point, this patch should be applied at
some common place.
 */
object ThreadTrackerPatcherForTeamCityTesting {
    private val patched = AtomicBoolean(false)

    fun patchThreadTracker() {
        if (patched.get()) return

        patched.compareAndSet(false, true)

        IdeaForkJoinWorkerThreadFactory.setupForkJoinCommonPool(true)

        // Check setup was successful and patching isn't needed
        val commonPoolFactoryName = ForkJoinPool.commonPool().factory::class.java.name
        if (commonPoolFactoryName == IdeaForkJoinWorkerThreadFactory::class.java.name) {
            return
        }

        try {
            val wellKnownOffendersField = try {
                ThreadTracker::class.java.getDeclaredField("wellKnownOffenders")
            }
            catch (communityPropertyNotFoundEx: NoSuchFieldException) {
                ThreadTracker::class.java.declaredFields.single {
                    Modifier.isStatic(it.modifiers) && MutableSet::class.java.isAssignableFrom(it.type)
                }
            }

            wellKnownOffendersField.isAccessible = true

            @Suppress("UNCHECKED_CAST")
            val wellKnownOffenders = wellKnownOffendersField.get(null) as MutableSet<String>

            wellKnownOffenders.add("ForkJoinPool.commonPool-worker-")
            println("Patching ThreadTracker was successful")
        }
        catch (e: NoSuchFieldException) {
            println("Patching ThreadTracker failed: " + e)
        }
        catch (e: IllegalAccessException) {
            println("Patching ThreadTracker failed: " + e)
        }
    }
}