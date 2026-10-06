package org.setu.car_meets_assignment

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.setu.car_meets_assignment.models.CarmeetModel

class AddEditActivity : AppCompatActivity() {
    private lateinit var titleInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var locationInput: EditText

    private var editingId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createUserInterface()

        editingId = intent.getLongExtra("id", -1L)

        if (editingId != -1L) {
            loadExistingCarMeet(editingId!!)
        }
    }

    private fun createUserInterface() {

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 320, 32, 32)
        }

        titleInput = EditText(this).apply {
            hint = "Title"
        }

        descriptionInput = EditText(this).apply {
            hint = "Description"
        }

        locationInput = EditText(this).apply {
            hint = "Location"
        }

        val saveButton = Button(this).apply {
            text = "Save"

            setOnClickListener {
                saveCarMeet()
            }
        }

        val cancelButton = Button(this).apply {
            text = "Cancel"

            setOnClickListener {
                finish()
            }
        }

        root.addView(titleInput)
        root.addView(descriptionInput)
        root.addView(locationInput)
        root.addView(saveButton)
        root.addView(cancelButton)

        setContentView(root)
    }

    private fun loadExistingCarMeet(id: Long) {

        val cmeet = AppData.carMeets.findOnebyID(id)

        if (cmeet == null) {
            Toast.makeText(
                this,
                "Car meet not found",
                Toast.LENGTH_SHORT
            ).show()

            finish()
            return
        }

        titleInput.setText(cmeet.title)
        descriptionInput.setText(cmeet.description)
        locationInput.setText(cmeet.location)
    }

    private fun saveCarMeet() {

        val title = titleInput.text.toString().trim()
        val description = descriptionInput.text.toString().trim()
        val location = locationInput.text.toString().trim()

        if (title.isEmpty()) {
            titleInput.error = "Title is required"
            return
        }

        if (location.isEmpty()) {
            locationInput.error = "Location is required"
            return
        }

        if (editingId == null || editingId == -1L) {

            val cmeet = CarmeetModel(
                title = title,
                description = description,
                location = location
            )

            AppData.carMeets.create(cmeet)

            Toast.makeText(
                this,
                "Mark created",
                Toast.LENGTH_SHORT
            ).show()

        } else {

            val cmeet = CarmeetModel(
                id = editingId!!,
                title = title,
                description = description,
                location = location
            )

            AppData.carMeets.update(cmeet)

            Toast.makeText(
                this,
                "Car meet updated",
                Toast.LENGTH_SHORT
            ).show()
        }

        finish()
    }
}