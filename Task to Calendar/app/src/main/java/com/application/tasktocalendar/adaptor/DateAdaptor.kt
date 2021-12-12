package com.application.tasktocalendar.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.tasktocalendar.R
import com.application.tasktocalendar.databinding.CalendarRecyclerBinding
import com.application.tasktocalendar.inter.OnClickDate


// To Desiplay The date
class DateAdaptor(
    val dateOfMonth: ArrayList<String>,
    val itemoncick : OnClickDate
) : RecyclerView.Adapter<DateHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateHolder {
        val calendarRecyclerBinding: CalendarRecyclerBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.calendar_recycler, parent, false)
        return DateHolder(calendarRecyclerBinding)
    }

    override fun onBindViewHolder(holder: DateHolder, position: Int) {
        holder.calendarRecyclerBinding.tvCalendarNumber.setOnClickListener {
            itemoncick.DateClick(position,holder.calendarRecyclerBinding.tvCalendarNumber.text.toString())
        }
        holder.calendarRecyclerBinding.tvCalendarNumber.setText(dateOfMonth.get(position))
    }

    override fun getItemCount(): Int {
        return dateOfMonth.size
    }


}

class DateHolder(
    val calendarRecyclerBinding: CalendarRecyclerBinding,
) : RecyclerView.ViewHolder(calendarRecyclerBinding.root)