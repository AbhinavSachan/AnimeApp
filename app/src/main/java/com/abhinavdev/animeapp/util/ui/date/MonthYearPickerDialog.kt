package com.abhinavdev.animeapp.util.ui.date

import android.annotation.SuppressLint
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.databinding.MonthYearPickerDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Calendar


class MonthYearPickerDialog : DialogFragment() {
    private var listener: OnDateSetListener? = null

    fun setListener(listener: OnDateSetListener?) {
        this.listener = listener
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireActivity())
        val inflater = requireActivity().layoutInflater

        builder.setBackground(
            ResourcesCompat.getDrawable(
                resources, R.drawable.bg_primary_corner_12, null
            )
        )

        val cal = Calendar.getInstance()

        val view = MonthYearPickerDialogBinding.inflate(inflater)
        val monthPicker = view.pickerMonth
        val yearPicker = view.pickerYear

        monthPicker.minValue = 1
        monthPicker.maxValue = 12
        monthPicker.value = cal[Calendar.MONTH] + 1

        val year = cal[Calendar.YEAR]
        yearPicker.minValue = 1900
        yearPicker.maxValue = 3500
        yearPicker.value = year


        view.btnOkay.setOnClickListener {
            listener!!.onDateSet(
                null, yearPicker.value, monthPicker.value, 0
            )
            this@MonthYearPickerDialog.dialog!!.cancel()
        }
        view.btnCancel.setOnClickListener {
            this@MonthYearPickerDialog.dialog!!.cancel()
        }
        builder.setView(view.root)
        return builder.create()
    }

    companion object {
        private const val MAX_YEAR = 2099
    }
}