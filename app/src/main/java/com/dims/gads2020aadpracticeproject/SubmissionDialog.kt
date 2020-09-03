package com.dims.gads2020aadpracticeproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton

class SubmissionDialog(private val viewModel: SubmissionViewModel) : DialogFragment(), View.OnClickListener {

    private lateinit var cancelButton: ImageButton
    private lateinit var confirmButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_submission_dialog, container)

        view.apply {
            cancelButton = findViewById(R.id.cancel_button)
            confirmButton = findViewById(R.id.confirm_button)
        }

        cancelButton.setOnClickListener(this)
        confirmButton.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.cancel_button -> close()
            R.id.confirm_button -> {
                viewModel.submit()
                close()
            }
        }
    }

    private fun close() = dialog?.dismiss()
}