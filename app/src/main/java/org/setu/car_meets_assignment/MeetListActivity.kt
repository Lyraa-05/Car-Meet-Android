package org.setu.car_meets_assignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.setu.car_meets_assignment.models.CarmeetModel

class MeetListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CarMeetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_meet_list)
        supportActionBar?.hide()

        recyclerView =
            findViewById(R.id.meetsRecyclerView)

        adapter = CarMeetAdapter(
            meets = AppData.carMeets.findAll(),

            onEdit = { meet ->
                editMeet(meet)
            },

            onDelete = { mark ->
                deleteMeet(mark)
            }
        )

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        recyclerView.adapter = adapter

        val returnButton =
            findViewById<Button>(R.id.returnButton)

        returnButton.setOnClickListener {

            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }
    }

    override fun onResume() {
        super.onResume()

        if (::adapter.isInitialized) {

            adapter.updateMeets(
                AppData.carMeets.findAll()
            )
        }
    }

    private fun editMeet(mark: CarmeetModel) {

        val intent =
            Intent(this, AddEditActivity::class.java)

        intent.putExtra("id", mark.id)

        startActivity(intent)
    }

    private fun deleteMeet(mark: CarmeetModel) {

        AppData.carMeets.delete(mark.id)

        adapter.updateMeets(
            AppData.carMeets.findAll()
        )
    }
}
