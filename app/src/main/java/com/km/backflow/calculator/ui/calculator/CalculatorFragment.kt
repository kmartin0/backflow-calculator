package com.km.backflow.calculator.ui.calculator

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import com.km.backflow.calculator.R
import com.km.backflow.calculator.base.BaseMVVMFragment
import com.km.backflow.calculator.databinding.FragmentCalculatorBinding
import com.km.backflow.calculator.helpers.hideKeyboard

class CalculatorFragment : BaseMVVMFragment<FragmentCalculatorBinding, CalculatorViewModel>() {

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		this.initViews()
	}

	private fun initViews() {
		binding.toolbar.title = getString(R.string.app_name)

		binding.calculatorFormLayout.etFlex.setOnEditorActionListener { v, actionId, event ->
			if (actionId == EditorInfo.IME_ACTION_DONE) {
				binding.calculatorFormLayout.etFlex.clearFocus()
				hideKeyboard()
				true
			} else {
				false
			}
		}
	}

	override fun initViewModelBinding() {
		binding.viewModel = viewModel
	}

	override fun getVMClass(): Class<CalculatorViewModel> {
		return CalculatorViewModel::class.java
	}

	override fun getLayoutId(): Int {
		return R.layout.fragment_calculator
	}

}