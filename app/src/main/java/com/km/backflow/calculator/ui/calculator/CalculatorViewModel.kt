package com.km.backflow.calculator.ui.calculator


import com.km.backflow.calculator.base.BaseViewModel
import com.km.backflow.calculator.form.CalculatorForm

class CalculatorViewModel: BaseViewModel() {
    val calculatorForm = CalculatorForm()

    fun resetForm() {
        calculatorForm.reset()
    }

}
