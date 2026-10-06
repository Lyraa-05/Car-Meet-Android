package org.setu.car_meets_assignment.models

import java.util.concurrent.atomic.AtomicLong

class CarmeetStore {
    private val carmeets = ArrayList<CarmeetModel>()
    private val lastId = AtomicLong(0L)


    fun findAll(): List<CarmeetModel> {
        return carmeets
    }

    fun create(carmeet: CarmeetModel) {
        carmeet.id = lastId.incrementAndGet()
        carmeets.add(carmeet)
    }

    fun update(carmeet: CarmeetModel): Boolean {
        val foundCarmeet = findOnebyID(carmeet.id)
        return if (foundCarmeet != null) {
            foundCarmeet.title = carmeet.title;
            foundCarmeet.description = carmeet.description;
            foundCarmeet.location = carmeet.location;
            true
        } else {
            false
        }
    }

    fun delete(id: Long): Boolean {
        val foundCarmeet = findOnebyID(id)
        return if (foundCarmeet != null) {
            carmeets.remove(foundCarmeet)
            true
        } else {
            false
        }
    }

    fun findOnebyID(id: Long): CarmeetModel? {
        return carmeets.find { p -> p.id == id }
    }

    fun findOnebyString(location: String): CarmeetModel? {
        return carmeets.find { p ->
            p.location.contains(location, ignoreCase = true)
        }
    }
}

