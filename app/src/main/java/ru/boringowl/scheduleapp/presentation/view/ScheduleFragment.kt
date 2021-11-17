package ru.boringowl.scheduleapp.presentation.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ru.boringowl.scheduleapp.R
import ru.boringowl.scheduleapp.databinding.ScheduleFragmentBinding
import ru.boringowl.scheduleapp.domain.model.LessonEntity
import ru.boringowl.scheduleapp.presentation.view.utils.OnSwipeTouchListener
import ru.boringowl.scheduleapp.presentation.view.utils.PrefsUtils
import ru.boringowl.scheduleapp.presentation.view.utils.ScheduleAnims
import ru.boringowl.scheduleapp.presentation.viewmodel.ScheduleViewModel

class ScheduleFragment : Fragment() {

    private lateinit var viewModel: ScheduleViewModel
    private lateinit var binding: ScheduleFragmentBinding
    private lateinit var root: View
    private var isMainRecycler: Boolean = true
    var isRadioGroupListened: Boolean = true
    lateinit private var prefs: PrefsUtils

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(ScheduleViewModel::class.java)
        binding = ScheduleFragmentBinding.inflate(inflater, container, false)
        root = binding.root
        prefs = PrefsUtils(requireContext())
        if (prefs.isGroupStored()) {
            viewModel.getSchedule(prefs.getGroup()!!)
        } else {
            Toast.makeText(context, "У вас не указана группа", Toast.LENGTH_LONG).show()
        }
        binding.rGroup.setOnCheckedChangeListener { group, checkedId ->
            if (isRadioGroupListened) {
                val oldDay = viewModel.dayNum.value

                if (viewModel.getDay(checkedId) != oldDay)
                    viewModel.setDay(checkedId)

                val newDay = viewModel.dayNum.value
                if (newDay != null && oldDay != null) {
                    if (newDay > oldDay)
                        rightAnim()
                    else
                        leftAnim()
                }
            }
            isRadioGroupListened = true
        }

        binding.imageButton.setOnClickListener{
            viewModel.minusWeek()
            leftAnim()
        }

        binding.imageButton2.setOnClickListener{
            viewModel.plusWeek()
            rightAnim()
        }

        binding.weekLayout.setOnTouchListener(OnSwipeTouchListener(context, {
                isRadioGroupListened = false

                viewModel.plusWeek()
                rightAnim()
            }, {
                isRadioGroupListened = false

                viewModel.minusWeek()
                leftAnim()
            }))

        binding.recyclerView.setOnTouchListener(OnSwipeTouchListener(context, {

                isRadioGroupListened = false
                viewModel.plusDay()
                rightAnim()
            }, {

                isRadioGroupListened = false
                viewModel.minusDay()
                leftAnim()
            }))

        viewModel.weekNum.observe(viewLifecycleOwner, {
            binding.monthWeek.text = "${viewModel.getMonthByWeek()}\n (${it+1} неделя)"

            if (!viewModel.weekChangedByDay)
                updateRecyclerView()
            viewModel.weekChangedByDay = false
        })
        viewModel.dayNum.observe(viewLifecycleOwner, {
            binding.rGroup.check(viewModel.getIdByDay(it))

            updateRecyclerView()
        })
        viewModel.week.observe(viewLifecycleOwner, {

            updateRecyclerView()
        })

        viewModel.message.observe(viewLifecycleOwner, {message ->
            /**
             * Сообщение для SnackBar'a
             */
            if(message.isNotEmpty() && viewModel.isShowed.value == false) {
                showMessage(message)
                viewModel.onMessageShowed()
            }
        })
        return root
    }

    private fun updateRecyclerView() {
        binding.recyclerView.also {
            var lessons = arrayListOf<LessonEntity>()
            try {
                lessons = viewModel.getLessons()
            } catch (e: IndexOutOfBoundsException) {}
            Log.d("TEXT", "asd"+lessons.toString())
            setAdapter(ScheduleAdapter(lessons))
            if(lessons.size != 0) binding.noPairs.visibility = View.GONE
            else binding.spinKit.visibility = View.GONE
        }
    }
    private fun rightAnim() {
        val animIn = ScheduleAnims.rightIn(binding.recyclerView.context)
        val animOut = ScheduleAnims.leftOut(binding.recyclerView.context)
        val animIn2 = ScheduleAnims.rightIn(binding.recyclerView2.context)
        val animOut2 = ScheduleAnims.leftOut(binding.recyclerView2.context)

        binding.recyclerView2.layoutAnimation = if (isMainRecycler) animIn2 else animOut2
        binding.recyclerView.layoutAnimation = if (isMainRecycler) animOut else animIn
    }

    private fun leftAnim() {
        val animIn = ScheduleAnims.leftIn(binding.recyclerView.context)
        val animOut = ScheduleAnims.rightOut(binding.recyclerView.context)
        val animIn2 = ScheduleAnims.leftIn(binding.recyclerView2.context)
        val animOut2 = ScheduleAnims.rightOut(binding.recyclerView2.context)

        binding.recyclerView2.layoutAnimation = if (isMainRecycler) animIn2 else animOut2
        binding.recyclerView.layoutAnimation = if (isMainRecycler) animOut else animIn
    }
    private fun setAdapter(adapter: ScheduleAdapter) {

        isMainRecycler = !isMainRecycler
        if (isMainRecycler) {
            binding.recyclerView2.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView2.setHasFixedSize(true)
            binding.recyclerView2.adapter = adapter
        } else {
            binding.recyclerView.layoutManager= LinearLayoutManager(requireContext())
            binding.recyclerView.setHasFixedSize(true)
            binding.recyclerView.adapter = adapter
        }
        binding.recyclerView.startLayoutAnimation()
        binding.recyclerView2.startLayoutAnimation()
        binding.noPairs.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE

    }
    private fun showMessage(message: String) {
        Snackbar.make(requireActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }

}