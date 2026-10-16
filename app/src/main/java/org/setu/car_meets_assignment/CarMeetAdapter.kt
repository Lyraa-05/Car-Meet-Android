package org.setu.car_meets_assignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.setu.car_meets_assignment.models.CarmeetModel

class CarMeetAdapter(
    private var meets: List<CarmeetModel>,
    private val onEdit: (CarmeetModel) -> Unit,
    private val onDelete: (CarmeetModel) -> Unit
) : RecyclerView.Adapter<CarMeetAdapter.MeetViewHolder>() {

    class MeetViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val titleText: TextView =
            itemView.findViewById(R.id.titleText)

        val descriptionText: TextView =
            itemView.findViewById(R.id.descriptionText)

        val locationText: TextView =
            itemView.findViewById(R.id.locationText)

        val dateText: TextView =
            itemView.findViewById(R.id.dateText)

        val editButton: Button =
            itemView.findViewById(R.id.editButton)

        val deleteButton: Button =
            itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MeetViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_car_meet,
                parent,
                false
            )

        return MeetViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MeetViewHolder,
        position: Int
    ) {

        val meet = meets[position]

        holder.titleText.text =
            "${meet.id}: ${meet.title}"

        holder.descriptionText.text =
            meet.description

        holder.locationText.text =
            meet.location

        holder.dateText.text = "Date: ${meet.date}"

        holder.editButton.setOnClickListener {
            onEdit(meet)
        }

        holder.deleteButton.setOnClickListener {
            onDelete(meet)
        }
    }

    override fun getItemCount(): Int {
        return meets.size
    }

    fun updateMeets(newMeets: List<CarmeetModel>) {
        meets = newMeets
        notifyDataSetChanged()
    }
}
