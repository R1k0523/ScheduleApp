package ru.boringowl.scheduleapp.presentation.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.boringowl.scheduleapp.R
import ru.boringowl.scheduleapp.databinding.ScheduleFragmentBinding
import ru.boringowl.scheduleapp.databinding.SettingsFragmentBinding
import ru.boringowl.scheduleapp.presentation.view.utils.PrefsUtils
import ru.boringowl.scheduleapp.presentation.viewmodel.SettingsViewModel

class SettingsFragment : Fragment() {

    private lateinit var viewModel: SettingsViewModel
    private lateinit var binding: SettingsFragmentBinding
    private lateinit var prefs: PrefsUtils
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SettingsFragmentBinding.inflate(inflater, container, false)
        prefs = PrefsUtils(requireContext())
        if (prefs.isGroupStored()) {
            binding.thirdNameEditText.setText(prefs.getGroup())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        binding.saveButton.setOnClickListener {
            prefs.provideGroup(binding.thirdNameEditText.text.toString())
        }
    }


}