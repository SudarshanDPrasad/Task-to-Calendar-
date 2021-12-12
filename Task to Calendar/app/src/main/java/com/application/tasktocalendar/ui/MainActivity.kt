package com.application.tasktocalendar.ui

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.tasktocalendar.R
import com.application.tasktocalendar.adaptor.DateAdaptor
import com.application.tasktocalendar.data.*
import com.application.tasktocalendar.inter.OnClickDate
import com.application.tasktocalendar.model.TaskViewModel
import com.application.tasktocalendar.response.CreateTaskClass
import com.application.tasktocalendar.response.GetResponseDto
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_task_dialgoue.view.*
import java.lang.Math.abs
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity(), OnClickDate, GestureDetector.OnGestureListener {
    private lateinit var selectedDate: LocalDate
    lateinit var gestureDetector: GestureDetector

    var x2: Float = 0.0f
    var x1: Float = 0.0f


    lateinit var taskRoomDataBase: TaskRoomDataBase
    lateinit var taskDao: TaskDao
    lateinit var taskViewModel: TaskViewModel


    companion object {
        const val MIN_DISTANCE = 150
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            selectedDate = LocalDate.now()
            setMonthView()
            gestureDetector = GestureDetector(this, this)
            taskRoomDataBase = TaskRoomDataBase.getDataBaseObject(this)
            taskDao = taskRoomDataBase.getDao()

            taskViewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)

            // To go for the activity of list task
            btn_add_task.setOnClickListener {
                val intent: Intent = Intent(this, TaskDisplayActivity::class.java)
                startActivity(intent)
            }
        }
    }

    // Swipe function to change the month
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        gestureDetector.onTouchEvent(event)

        when (event?.action) {
            0 -> {
                x1 = event.x
            }

            1 -> {
                x2 = event.x

                val valueX: Float = x2 - x1

                if (abs(valueX) > MIN_DISTANCE) {
                    if (x2 > x1) {
                        selectedDate = selectedDate.minusMonths(1)
                        setMonthView()
                    } else {
                        selectedDate = selectedDate.plusMonths(1)
                        setMonthView()
                    }
                }
            }
        }
        return true
    }

    // setting the date in recycler view
    private fun setMonthView() {
        tv_Month_and_year.setText(monthdate(selectedDate))
        val daysInMonth: ArrayList<String> = daysInMonth(selectedDate)
        val dateAdaptor: DateAdaptor = DateAdaptor(daysInMonth, this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 7)
        Rv_Calendar_Date.adapter = dateAdaptor
        Rv_Calendar_Date.layoutManager = layoutManager
    }

    private fun monthdate(requiredDate: LocalDate): String {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return requiredDate.format(formatter)
    }

    //calculating the date and store in the array
    private fun daysInMonth(date: LocalDate): ArrayList<String> {

        val dayInArray: ArrayList<String> = ArrayList()
        val year: YearMonth = YearMonth.from(date)

        val days: Int = year.lengthOfMonth()
        val month: LocalDate = selectedDate.withDayOfMonth(1)
        val week: Int = month.dayOfWeek.value

        for (i in 1..42) {
            if (i <= week || i > days + week) {
                dayInArray.add("")
            } else {

                dayInArray.add((i - week).toString())
            }
        }
        return dayInArray
    }

    // on click for adding the task
    override fun DateClick(position: Int, dayText: String) {

        var datetoStore = "$dayText ${monthdate(selectedDate)}"

        //Adding the Task Through Dialgoue Box
        val mDialog = LayoutInflater.from(this).inflate(R.layout.add_task_dialgoue, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialog)
        val mAlertDialog = mBuilder.show()

        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        mDialog.tv_Date_To_Save.setText(datetoStore)
        val title = mDialog.et_Task_to_added.text
        val descri = mDialog.et_Descripition_to_added.text

        mDialog.btn_toSaveTheTask.setOnClickListener {
            // Adding Details to Api
            val task = TaskTable(datetoStore,
                title.toString(),
                descri.toString())
            val response = CreateTaskClass(1001, task)
            taskViewModel.createNewTask(response)
            mAlertDialog.dismiss()
        }
    }

    override fun onDown(p0: MotionEvent?): Boolean {
//        TODO("Not yet implemented")
        return false
    }

    override fun onShowPress(p0: MotionEvent?) {
//        TODO("Not yet implemented")
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
//        TODO("Not yet implemented")
        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
//        TODO("Not yet implemented")
        return false
    }

    override fun onLongPress(p0: MotionEvent?) {
//        TODO("Not yet implemented")
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
//        TODO("Not yet implemented")
        return false
    }

}