package org.techtown.project5.view.meals.title_fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import org.techtown.project5.R
import org.techtown.project5.databinding.FragmentTodayBinding
import org.techtown.project5.view.base.BaseFragment
import org.techtown.project5.viewmodel.meals.title_viewmodel.TodayViewModel

class TodayFragment : BaseFragment() {

    lateinit var binding : FragmentTodayBinding
    lateinit var viewModel : TodayViewModel

    lateinit var school_id : String
    lateinit var office_id : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today, container, false)
        viewModel = ViewModelProviders.of(this@TodayFragment).get(TodayViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner

        getData()

        viewModel.checkTime()
        viewModel.getMeals(school_id, office_id)

        today = mCalendar.time
        binding.date.text = simpleDateFormat.format(today)

        observerViewModel()
        return binding.root
    }

    fun getData(){
        val sharedPreferences = context!!.getSharedPreferences("checkSearch", Context.MODE_PRIVATE)
        school_id = sharedPreferences.getString("school_id", "").toString()
        office_id = sharedPreferences.getString("office_id", "").toString()
    }

    fun observerViewModel(){
        with(viewModel){
            onBreakfastEvent.observe(this@TodayFragment, androidx.lifecycle.Observer {
                val adapter =
                    TitleMealsAdapter(
                        viewModel.breakfastList
                    )
                binding.recyclerView.adapter = adapter

                if(adapter.itemCount == 0){
                    binding.nomealsLayout.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                } else{
                    binding.nomealsLayout.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }

                Log.e("급식 [아침]", "아침 급식을 성공적으로 조회하였습니다.")
                binding.mealsTextView.text = "아침"
                viewModel.checkCount = 1
            })
            onLunchEvent.observe(this@TodayFragment, androidx.lifecycle.Observer {
                val adapter =
                    TitleMealsAdapter(
                        viewModel.lunchList
                    )
                binding.recyclerView.adapter = adapter

                if(adapter.itemCount == 0){
                    binding.nomealsLayout.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                } else{
                    binding.nomealsLayout.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }

                Log.e("급식 [점심]", "점심 급식을 성공적으로 조회하였습니다.")
                binding.mealsTextView.text = "점심"
                viewModel.checkCount = 2
            })
            onDinnerEvent.observe(this@TodayFragment, androidx.lifecycle.Observer {
                val adapter =
                    TitleMealsAdapter(
                        viewModel.dinnerList
                    )
                binding.recyclerView.adapter = adapter

                if(adapter.itemCount == 0){
                    binding.nomealsLayout.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                } else{
                    binding.nomealsLayout.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }

                Log.e("급식 [저녁]", "저녁 급식을 성공적으로 조회하였습니다.")
                binding.mealsTextView.text = "저녁"
                viewModel.checkCount = 3
            })
        }
    }
}
