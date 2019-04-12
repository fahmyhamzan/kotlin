/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package kotlin.jdk8.streams.test

import kotlin.streams.*
import org.junit.Test
import java.util.stream.*
import kotlin.test.*

class StreamsTest {

    @Test fun toList() {
        val data = arrayOf(1, 2L, 1.23, null)
        val streamBuilder = { Stream.of(*data) }

        assertEquals(data.asList(), streamBuilder().toList())
        assertEquals(listOf(1),     streamBuilder().filter { it is Int }.mapToInt { it as Int }.toList())
        assertEquals(listOf(2L),    streamBuilder().filter { it is Long }.mapToLong { it as Long }.toList())
        assertEquals(listOf(1.23),  streamBuilder().filter { it is Double }.mapToDouble { it as Double }.toList())
    }


    @Test fun asSequence() {
        val data = arrayOf(1, 2L, 1.23, null)

        fun<T> assertSequenceContent(expected: List<T>, actual: Sequence<T>) {
            assertEquals(expected, actual.toList())
            assertFailsWith<IllegalStateException>("Second iteration fails") { actual.toList() }

        }

        assertSequenceContent(data.asList(),    Stream.of(*data).asSequence())
        assertSequenceContent(listOf(1, 2),     IntStream.of(1, 2).asSequence())
        assertSequenceContent(listOf(1L, 2L),   LongStream.of(1L, 2L).asSequence())
        assertSequenceContent(listOf(1.0, 2.0), DoubleStream.of(1.0, 2.0).asSequence())
    }

    @Test fun asStream() {
        val sequence = generateSequence(0) { it -> it * it + 1 }
        val stream = sequence.asStream()
        val expected = Stream.iterate(0) { it -> it * it + 1 }

        assertEquals(expected.limit(7).toList(), stream.limit(7).toList())
    }

    @Test fun asParallelStream() {
        val sequence = generateSequence(0) { it + 2 } // even numbers
        val stream = sequence.asStream().parallel().map { it / 2 }

        val n = 100000
        val expected = (0 until n).toList()

        assertEquals(expected, stream.limit(n.toLong()).toList())
    }

}
