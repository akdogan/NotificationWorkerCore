package com.akdogan.androidcore.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.viewModels
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.akdogan.androidcore.R
import com.akdogan.androidcore.databinding.MainFragmentBinding
import com.google.android.material.snackbar.Snackbar


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels<MainViewModel>()
    private lateinit var binding: MainFragmentBinding
    private var toast: Toast? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.buttonToast.setOnClickListener {
            makeToast(resources.getString(R.string.toast_default_message, viewModel.counter))
            viewModel.counter++
        }
        binding.buttonSnackbar.setOnClickListener {
            makeSnackbar(it, resources.getString(R.string.snackbar_default_message))
        }
        binding.buttonNotify.setOnClickListener {
            /*with(NotificationManagerCompat.from(requireActivity())){
                notify(42, getNotificationBuilder(requireActivity().applicationContext).build())
            }*/
            sendNotification(requireActivity().application, id = 42)
        }
        binding.buttonFuture.setOnClickListener {
            scheduleNotification(requireActivity().application, 10_000)
        }
        binding.buttonBackground.setOnClickListener {
            val workRequest = OneTimeWorkRequestBuilder<FibonacciWorker>()
                .build()
            WorkManager.getInstance(requireContext()).enqueue(workRequest)
        }
    }

    private fun makeToast(msg: String){
        toast?.let{
            it.cancel()
        }
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT).also {
            it.show()
        }
    }

    private fun makeSnackbar(view: View, msg: String){
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
    }

}