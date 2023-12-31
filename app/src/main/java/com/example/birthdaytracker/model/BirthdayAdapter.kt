package com.example.birthdaytracker.model

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdaytracker.R
import com.google.gson.Gson

class BirthdayAdapter(
    private val birthdays: MutableList<BirthdayModel>,
    private val context: Context
) :
    RecyclerView.Adapter<BirthdayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.birthday_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(birthdays[position])
        Log.d("ViewHolder: OnBindViewHolder", "onBindViewHolder: ${birthdays[position]}")
    }

    override fun getItemCount(): Int {
        Log.d("DataCount: getItemCount", "getItemCount: ${birthdays.size}")
        return birthdays.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deleteImageView: ImageView = itemView.findViewById(R.id.delete_img_view)

        init {
            deleteImageView.id = View.generateViewId()
        }

        fun bind(birthday: BirthdayModel) {
            deleteImageView.setOnClickListener {
                Log.d("ViewHolder: bind", "Clicked on delete icon for position $adapterPosition")
                deleteBirthday(adapterPosition)
            }
            itemView.findViewById<TextView>(R.id.textViewPersonName).text = birthday.personName
            itemView.findViewById<TextView>(R.id.textViewLabel).text = birthday.label
            itemView.findViewById<TextView>(R.id.textViewDate).text = birthday.date
        }
    }

    private fun deleteBirthday(position: Int) {
        birthdays.removeAt(position)
        saveToSharedPreferences()
        notifyItemRemoved(position)
    }

    private fun saveToSharedPreferences() {
        val sharedPreferences = context.getSharedPreferences("birthDaySharedPref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("birthdayList", Gson().toJson(birthdays))
        editor.apply()
        Toast.makeText(context, "Data Deleted successfully", Toast.LENGTH_SHORT).show()
    }
}
