package com.example.evcarui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ActivityLogAdapter(
    private val activityList: List<ActivityLog>
) : RecyclerView.Adapter<ActivityLogAdapter.ActivityViewHolder>() {

    // ViewHolder: Holds references to the item views
    class ActivityViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val tvActivityIcon: TextView =
            itemView.findViewById(R.id.tvActivityIcon)

        val tvActivityName: TextView =
            itemView.findViewById(R.id.tvActivityName)

        val tvActivityTime: TextView =
            itemView.findViewById(R.id.tvActivityTime)

        val tvActivityStatus: TextView =
            itemView.findViewById(R.id.tvActivityStatus)
    }

    // Create a new activity item view
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActivityViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_activity_log, parent, false)

        return ActivityViewHolder(view)
    }

    // Display activity information in each item
    override fun onBindViewHolder(
        holder: ActivityViewHolder,
        position: Int
    ) {

        val currentActivity = activityList[position]

        holder.tvActivityIcon.text = currentActivity.icon
        holder.tvActivityName.text = currentActivity.activityName
        holder.tvActivityTime.text = currentActivity.activityTime
        holder.tvActivityStatus.text = currentActivity.activityStatus
    }

    // Return the total number of activity records
    override fun getItemCount(): Int {
        return activityList.size
    }
}
