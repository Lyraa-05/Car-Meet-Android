package org.setu.car_meets_assignment.main

import org.setu.car_meets_assignment.models.CarmeetModel
import org.setu.car_meets_assignment.models.CarmeetStore

val store = CarmeetStore()

fun main() {
    println("=== Car Meet Console App ===")
    var input: Int
    do {
        input = menu()
        when (input) {
            1 -> addCarmeet()
            2 -> listCarmeets()
            3 -> updateCarmeet()
            4 -> deleteCarmeet()
            5 -> searchCarmeetbyID()
            6 -> searchCarmeetbyLocation()
            0 -> println("\nExiting Car meet application. Goodbye!")
            else -> println("\nInvalid option. Please try again.")
        }
    } while (input != 0)
}

fun menu(): Int {
    println("\n----------------------------------")
    println(" MAIN MENU")
    println("----------------------------------")
    println(" 1. Add Car Meet")
    println(" 2. List All Car Meets")
    println(" 3. Update a Car Meet")
    println(" 4. Delete a Car Meet")
    println(" 5. Search Car Meet by ID")
    println(" 6. Search Car Meet by Location")   // ==================> TO DO
    println(" 0. Exit")
    print("\nEnter option: ")
    return readlnOrNull()?.toIntOrNull() ?: -1
}

fun addCarmeet() {
    println("\n--- Add Carmeet ---")
    print("Enter Title: ")
    val title = readlnOrNull()?.trim().orEmpty()
    print("Enter Description: ")
    val description = readlnOrNull()?.trim().orEmpty()
    print("Enter Location: ")
    val location = readlnOrNull()?.trim().orEmpty()


    if (title.isNotEmpty()) {
        val carmeet = CarmeetModel(title = title, description = description, location = location)
        store.create(carmeet)
        println("Car meet added successfully with ID: ${carmeet.id}")
    } else {
        println("Title cannot be empty. Creation cancelled.")
    }
}

fun listCarmeets() {
    println("\n--- All Car meets ---")
    val carmeets = store.findAll()
    if (carmeets.isEmpty()) {
        println("No car meets stored yet.")
    } else {
        carmeets.forEach { println("ID: ${it.id} | Title: ${it.title} | Description: ${it.description} | Location: ${it.location}") }
    }
}

fun updateCarmeet() {
    println("\n--- Update Carmeet ---")
    listCarmeets()
    if (store.findAll().isEmpty()) return

    print("\nEnter ID of Carmeet to update: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null && store.findOnebyID(id) != null) {
        print("Enter New Title: ")
        val title = readlnOrNull()?.trim().orEmpty()
        print("Enter New Description: ")
        val description = readlnOrNull()?.trim().orEmpty()
        print("Enter New Location: ")
        val location = readlnOrNull()?.trim().orEmpty()

        if (title.isNotEmpty()) {
            val updated = store.update(
                CarmeetModel(
                    id = id,
                    title = title,
                    description = description,
                    location = location
                )
            )
            if (updated) println("Car meet updated successfully.")
        } else {
            println("Title cannot be empty. Update cancelled.")
        }
    } else {
        println("Carmeet with ID $id not found.")
    }
}

fun deleteCarmeet() {
    println("\n--- Delete Carmeet ---")
    listCarmeets()
    if (store.findAll().isEmpty()) return

    print("\nEnter ID of Car meet to delete: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null) {
        val deleted = store.delete(id)
        if (deleted) {
            println("Carmeet with ID $id deleted successfully.")
        } else {
            println("Carmeet with ID $id not found.")
        }
    } else {
        println("Invalid ID entered.")
    }
}

fun searchCarmeetbyID() {
    println("\n--- Search Carmeet ---")
    print("Enter ID: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null) {
        val carmeet = store.findOnebyID(id)
        if (carmeet != null) {
            println("Found: ID: ${carmeet.id} | Title: ${carmeet.title} | Description: ${carmeet.description} | Location: ${carmeet.location}")
        } else {
            println("No carmeet found with ID $id.")
        }
    } else {
        println("Invalid ID entered.")
    }
}

fun searchCarmeetbyLocation() {
    println("\n -- Search Carmeet ---")
    print("Enter Location: ")
    val location = readlnOrNull()?.trim().orEmpty()

    if (location.isNotEmpty()){
        val carmeet = store.findOnebyString(location)
        if (carmeet != null) {
            println("Found: ID: ${carmeet.id} | Title: ${carmeet.title} | Description: ${carmeet.description} | Location: ${carmeet.location}")
        }
        else {
            println("No carmeet found with Location $location.")
        }
    }
    else {
        println("Invalid Location entered.")
    }
}