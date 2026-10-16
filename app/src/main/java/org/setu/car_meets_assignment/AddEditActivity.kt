
package org.setu.car_meets_assignment

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.setu.car_meets_assignment.models.CarmeetModel
import java.util.Calendar
import java.util.Locale

class AddEditActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var locationInput: EditText
    private lateinit var dateInput: EditText
    private lateinit var formTitle: TextView

    private var editingId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        titleInput = findViewById(R.id.titleInput)
        descriptionInput = findViewById(R.id.descriptionInput)
        locationInput = findViewById(R.id.locationInput)
        dateInput = findViewById(R.id.dateInput)
        formTitle = findViewById(R.id.formTitle)

        findViewById<Button>(R.id.saveButton).setOnClickListener {
            saveCarMeet()
        }

        findViewById<Button>(R.id.cancelButton).setOnClickListener {
            finish()
        }

        dateInput.setOnClickListener {
            showDatePicker()
        }

        val id = intent.getLongExtra("id", -1L)
        editingId = if (id == -1L) null else id

        if (editingId != null) {
            formTitle.text = "Edit Car Meet"
            loadExistingCarMeet(editingId!!)
        } else {
            formTitle.text = "Add Car Meet"
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()

        val existingDate = dateInput.text.toString().trim()

        if (existingDate.isNotEmpty()) {
            try {
                val parts = existingDate.split("/")
                require(parts.size == 3)

                val day = parts[0].toInt()
                val month = parts[1].toInt()
                val year = parts[2].toInt()

                calendar.set(year, month - 1, day) // calendar num 0 - 11 months

                require(calendar.get(Calendar.YEAR) == year)
                require(calendar.get(Calendar.MONTH) == month - 1)
                require(calendar.get(Calendar.DAY_OF_MONTH) == day)
            } catch (_: Exception) {
                calendar.timeInMillis = System.currentTimeMillis()
            }
        }

        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                dateInput.setText(
                    String.format(
                        Locale.UK,
                        "%02d/%02d/%04d",
                        dayOfMonth,
                        month + 1,
                        year
                    )
                )
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
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
        dateInput.setText(cmeet.date)
    }

    private fun saveCarMeet() {
        val title = titleInput.text.toString().trim()
        val description = descriptionInput.text.toString().trim()
        val location = locationInput.text.toString().trim()
        val date = dateInput.text.toString().trim()

        if (title.isEmpty()) {
            titleInput.error = "Title is required"
            return
        }

        if (location.isEmpty()) {
            locationInput.error = "Location is required"
            return
        }

        if (date.isEmpty()) {
            dateInput.error = "Please select a date"
            return
        }

        if (editingId == null) {
            AppData.carMeets.create(
                CarmeetModel(
                    title = title,
                    description = description,
                    location = location,
                    date = date
                )
            )

            Toast.makeText(
                this,
                "Car meet created",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            AppData.carMeets.update(
                CarmeetModel(
                    id = editingId!!,
                    title = title,
                    description = description,
                    location = location,
                    date = date
                )
            )

            Toast.makeText(
                this,
                "Car meet updated",
                Toast.LENGTH_SHORT
            ).show()
        }

        finish()
    }
}
