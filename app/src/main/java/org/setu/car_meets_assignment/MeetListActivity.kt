package org.setu.car_meets_assignment

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
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
            listener = object : OnMeetListener {

                override fun onMeetClick(meet: CarmeetModel){
                    editMeet(meet)
                }

                override fun onMeetLongClick(meet: CarmeetModel): Boolean {
                    deleteMeet(meet)
                    return true
                }

            }

        )

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        recyclerView.adapter = adapter

        val returnButton =
            findViewById<ImageView>(R.id.returnButton)

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

    private fun deleteMeet(meet: CarmeetModel) {
        AlertDialog.Builder(this)
            .setTitle("Delete Car Meet")
            .setMessage("Are you sure you want to delete '${meet.title}'?")
            .setPositiveButton("Delete") { dialog, _ ->
                AppData.carMeets.delete(meet.id)
                adapter.updateMeets(AppData.carMeets.findAll())
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
