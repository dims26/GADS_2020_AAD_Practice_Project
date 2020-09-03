package com.dims.gads2020aadpracticeproject

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SubmissionActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var fnameTextInputLayout: TextInputLayout
    private lateinit var fnameEditText: TextInputEditText
    private lateinit var lnameTextInputLayout: TextInputLayout
    private lateinit var lnameEditText: TextInputEditText
    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText
    private lateinit var linkTextInputLayout: TextInputLayout
    private lateinit var linkEditText: TextInputEditText
    private lateinit var submitButton: MaterialButton
    private lateinit var scrim: View
    private lateinit var checkNotAButton: MaterialButton
    private lateinit var errorNotAButton: MaterialButton
    private lateinit var loader: ProgressBar

    private lateinit var viewModel: SubmissionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submission)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onStart() {
        super.onStart()

        fnameTextInputLayout = findViewById(R.id.fname_text_input_layout)
        fnameEditText = findViewById(R.id.fname_text_input_edit_text)
        lnameTextInputLayout = findViewById(R.id.lname_text_input_layout)
        lnameEditText = findViewById(R.id.lname_text_input_edit_text)
        emailTextInputLayout = findViewById(R.id.email_text_input_layout)
        emailEditText = findViewById(R.id.email_text_input_edit_text)
        linkTextInputLayout = findViewById(R.id.link_text_input_layout)
        linkEditText = findViewById(R.id.link_text_input_edit_text)
        submitButton = findViewById(R.id.submit_button)

        scrim = findViewById(R.id.scrim)
        loader = findViewById(R.id.loading_progress_bar)
        checkNotAButton = findViewById(R.id.check_not_a_button)
        errorNotAButton = findViewById(R.id.error_not_a_button)

        submitButton.setOnClickListener {
            confirmSubmission()
        }

        val submissionService = ServiceBuilder.buildService(SubmissionService::class.java)
        val factory = ViewModelFactory(submissionService = submissionService)
        viewModel = ViewModelProvider(this, factory).get(SubmissionViewModel::class.java)

        viewModel.isSubmitableLiveData.observe(this, Observer { submitButton.isEnabled = it })
        viewModel.submissionLiveData.observe(this, Observer {
            scrim.visibility = View.VISIBLE
            loader.visibility = View.GONE
            checkNotAButton.visibility = View.GONE
            errorNotAButton.visibility = View.GONE

            when(it){
                LoadState.IDLE -> scrim.visibility = View.GONE
                LoadState.LOADING -> loader.visibility = View.VISIBLE
                LoadState.FAILED -> errorNotAButton.visibility = View.VISIBLE
                LoadState.SUCCESSFUL -> checkNotAButton.visibility = View.VISIBLE
                else -> {/**/}
            }
        })

        setTextListeners()
    }

    private fun setTextListeners() {
        fnameEditText.doAfterTextChanged {
            viewModel.fname = if (!it.isNullOrBlank()) {
                fnameTextInputLayout.error = null
                it.toString()
            }
            else {
                fnameTextInputLayout.error = getString(R.string.required_error_msg)
                ""
            }
            viewModel.checkIsSubmitable()
        }
        lnameEditText.doAfterTextChanged {
            viewModel.lname = if (!it.isNullOrBlank()) {
                lnameTextInputLayout.error = null
                it.toString()
            }
            else {
                lnameTextInputLayout.error = getString(R.string.required_error_msg)
                ""
            }
            viewModel.checkIsSubmitable()
        }
        emailEditText.doAfterTextChanged {
            viewModel.email = if (Patterns.EMAIL_ADDRESS.matcher(it.toString()).matches()) {
                emailTextInputLayout.error = null
                it.toString()
            }
            else {
                emailTextInputLayout.error = getString(R.string.email_error_msg)
                ""
            }
            viewModel.checkIsSubmitable()
        }
        linkEditText.doAfterTextChanged {
            viewModel.projectLink = if (Patterns.WEB_URL.matcher(it.toString()).matches()) {
                linkTextInputLayout.error = null
                it.toString()
            }
            else {
                linkTextInputLayout.error = "Invalid link"
                ""
            }
            viewModel.checkIsSubmitable()
        }
    }

    private fun confirmSubmission() {
        val frag: Fragment? = supportFragmentManager.findFragmentByTag("fragment_submit")
        if (frag != null) {
            supportFragmentManager.beginTransaction().remove(frag).commit()
        }
        val submitDialog = SubmissionDialog(viewModel)
        submitDialog.show(supportFragmentManager, "fragment_submit")
    }
}