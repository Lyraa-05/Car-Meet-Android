package org.setu.car_meets_assignment.models

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CarmeetStoreTest {

    private lateinit var store: CarmeetStore

    @Before
    fun setUp() {
        store = CarmeetStore()
    }

    @Test
    fun testCreateAndFindAll() {
        val meet1 = CarmeetModel(title = "Cars & Coffee", description = "Morning meet", location = "Waterford", date = "01/11/2024")
        val meet2 = CarmeetModel(title = "Night Meet", description = "Evening cruise", location = "Cork", date = "02/11/2024")

        store.create(meet1)
        store.create(meet2)

        val meets = store.findAll()
        assertEquals(2, meets.size)
        assertEquals(1L, meets[0].id)
        assertEquals(2L, meets[1].id)
        assertEquals("Cars & Coffee", meets[0].title)
    }

    @Test
    fun testFindOneById() {
        val meet = CarmeetModel(title = "Track Day", description = "Circuit racing", location = "Mondello", date = "15/11/2024")
        store.create(meet)

        val found = store.findOnebyID(1L)
        assertNotNull(found)
        assertEquals("Track Day", found?.title)

        val notFound = store.findOnebyID(99L)
        assertNull(notFound)
    }

    @Test
    fun testUpdateCarmeet() {
        val meet = CarmeetModel(title = "Old Title", description = "Old Desc", location = "Old Loc", date = "01/01/2024")
        store.create(meet)

        val updated = CarmeetModel(id = 1L, title = "New Title", description = "New Desc", location = "New Loc", date = "02/02/2024")
        val result = store.update(updated)

        assertTrue(result)
        val retrieved = store.findOnebyID(1L)
        assertEquals("New Title", retrieved?.title)
        assertEquals("New Desc", retrieved?.description)
        assertEquals("New Loc", retrieved?.location)
        assertEquals("02/02/2024", retrieved?.date)
    }

    @Test
    fun testDeleteCarmeet() {
        val meet = CarmeetModel(title = "Meet to Delete", description = "Desc", location = "Loc", date = "01/01/2024")
        store.create(meet)

        assertEquals(1, store.findAll().size)

        val deleted = store.delete(1L)
        assertTrue(deleted)
        assertEquals(0, store.findAll().size)

        val deleteNonExistent = store.delete(99L)
        assertFalse(deleteNonExistent)
    }

    @Test
    fun testFindByLocation() {
        val meet1 = CarmeetModel(title = "Meet A", description = "Desc", location = "Tramore Beach", date = "01/01/2024")
        val meet2 = CarmeetModel(title = "Meet B", description = "Desc", location = "Dublin City", date = "02/01/2024")

        store.create(meet1)
        store.create(meet2)

        val found = store.findOnebyLocation("tramore")
        assertNotNull(found)
        assertEquals("Meet A", found?.title)

        val notFound = store.findOnebyLocation("Galway")
        assertNull(notFound)
    }
}