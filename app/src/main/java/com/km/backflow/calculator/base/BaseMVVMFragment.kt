package com.km.backflow.calculator.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider

abstract class BaseMVVMFragment<T : ViewDataBinding, V : BaseViewModel> : BaseFragment() {

    protected lateinit var binding: T
    protected lateinit var viewModel: V

    /**
     * Setup the data binding and view model connection
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        viewModel = ViewModelProvider(this)[getVMClass()]
        binding.lifecycleOwner = activity as AppCompatActivity

        initViewModelBinding()

        return binding.root
    }

    // Initialize the view model in the view data binding object here.
    abstract fun initViewModelBinding()

    // Return the View model class here.
    abstract fun getVMClass(): Class<V>

}