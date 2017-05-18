package com.kotovdv.sandbox.multithreading

import com.kotovdv.sandbox.multithreading.first.collection.BinarySortedIntSet
import com.tngtech.java.junit.dataprovider.DataProvider
import com.tngtech.java.junit.dataprovider.DataProviderRunner
import com.tngtech.java.junit.dataprovider.UseDataProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.ThreadLocalRandom

@RunWith(DataProviderRunner::class)

class SortedSetTest {

    companion object Scenarios {
        @DataProvider
        @JvmStatic
        fun scenarios(): Array<Array<out Any>> {
            return arrayOf(
                    arrayOf(arrayOf(3, 5, 7), arrayOf(3, 5, 7)),
                    arrayOf(arrayOf(3, 3, 5, 5, 7, 7), arrayOf(3, 5, 7)),
                    arrayOf(arrayOf(7, 5, 3), arrayOf(3, 5, 7)),
                    arrayOf(arrayOf(7, 5, 3, 2, 1, 0), arrayOf(0, 1, 2, 3, 5, 7)),
                    arrayOf(arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)),
                    arrayOf(arrayOf(9, 8, 7, 6, 5, 4, 3, 2, 1, 0), arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))
            )
        }

        @DataProvider
        @JvmStatic
        fun randomScenarios(): Array<Array<out Any>> {
            val scenariosSize = 42

            val initialScenarios = arrayOfNulls<Array<out Any>>(scenariosSize)

            for (i in 0 until scenariosSize) {
                val randomScenario = createRandomScenario()
                initialScenarios[i] = arrayOf(randomScenario.first, randomScenario.second)
            }

            return initialScenarios.requireNoNulls()
        }


        fun createRandomScenario(): Pair<Array<Int>, Array<Int>> {
            val size = random(5, 25)
            val array = arrayOfNulls<Int>(size)

            for (i in 0 until size) {
                array[i] = random(Integer.MIN_VALUE, Integer.MAX_VALUE)
            }

            val initialScenario = array.requireNoNulls()

            return Pair(initialScenario, createSortedSetArray(initialScenario))
        }

        private fun createSortedSetArray(initialArrayNonNull: Array<Int>): Array<Int> {
            val linkedHashSet = LinkedHashSet<Int>()
            linkedHashSet += initialArrayNonNull

            return linkedHashSet.toTypedArray().sortedArray()
        }

        private fun random(from: Int, to: Int) = ThreadLocalRandom.current().nextInt(from, to)
    }

    @Test
    @UseDataProvider("scenarios")
    fun testPredefinedScenarios(initialArray: Array<Int>, sortedArray: Array<Int>) {
        assertSortedIntSet(initialArray, sortedArray)
    }

    @Test
    @UseDataProvider("randomScenarios")
    fun testRandomScenarios(initialArray: Array<Int>, sortedArray: Array<Int>) {
        assertSortedIntSet(initialArray, sortedArray)
    }


    private fun assertSortedIntSet(initialArray: Array<Int>, sortedArray: Array<Int>) {
        val set = BinarySortedIntSet(8)

        initialArray.forEach { value -> set.put(value) }

        assertThat(set.toArray()).isEqualTo(sortedArray)
    }
}