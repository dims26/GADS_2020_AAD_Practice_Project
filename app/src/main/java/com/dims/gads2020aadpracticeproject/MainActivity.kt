package com.dims.gads2020aadpracticeproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call

class MainActivity : AppCompatActivity() {

    private lateinit var fragmentAdapter: FragmentAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var toolbar: Toolbar
    private lateinit var submitButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = getString(R.string.actionbar_text)
    }

    override fun onStart() {
        super.onStart()

        val leadersService = ServiceBuilder.buildService(LeadersService::class.java)

        @Suppress("UNCHECKED_CAST")
        val callList = with(leadersService){
            listOf(getLearningLeaders(), getSkillLeaders())
        } as List<Call<List<Leader>>>

        submitButton = findViewById(R.id.submit_button)
        submitButton.setOnClickListener {
            val intent = Intent(this, SubmissionActivity::class.java)
            startActivity(intent)
        }

        fragmentAdapter = FragmentAdapter(this, callList)
        viewPager = findViewById(R.id.pager)
        tabLayout = findViewById(R.id.tab_layout)
        viewPager.adapter = fragmentAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when(position){
                0 -> "Learning Leaders"
                1 -> "Skill IQ Leaders"
                else -> ""
            }
        }.attach()
    }
}

class FragmentAdapter internal constructor(
    activity: FragmentActivity,
    private val callList: List<Call<List<Leader>>>
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        return LeadersFragment(callList[position])
    }
}
