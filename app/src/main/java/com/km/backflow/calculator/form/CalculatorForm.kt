package com.km.backflow.calculator.form

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kotlin.math.roundToInt

class CalculatorForm {

    // Field Values
    val availableStock = MutableLiveData<Int?>()
    val sgf = MutableLiveData<Int?>()
    val onTheFloor = MediatorLiveData<Int?>()

    val assq = MutableLiveData<Int?>()
    val flex = MutableLiveData<Int?>()
    val fillAmount = MediatorLiveData<Int?>()

    val backflow = MediatorLiveData<Int?>()
    val mupqPerBox = MutableLiveData<Int?>()
    val returnBoxes = MediatorLiveData<Int?>()

    init {
        // Add sources available stock and sgf to on the floor.
        onTheFloor.addSource(availableStock) { t -> updateOnTheFloor() }
        onTheFloor.addSource(sgf) { t -> updateOnTheFloor() }

        // Add sources assq and flex to fill amount.
        fillAmount.addSource(assq) { t -> updateFillAmount() }
        fillAmount.addSource(flex) { t -> updateFillAmount() }

        // Add sources on the floor and fill amount to backflow
        backflow.addSource(onTheFloor) { t -> updateBackflow() }
        backflow.addSource(fillAmount) { t -> updateBackflow() }

        // Add sources backflow and mupq per box to return boxes
        returnBoxes.addSource(backflow) { t -> updateReturnBoxes() }
        returnBoxes.addSource(mupqPerBox) { t -> updateReturnBoxes() }
    }

    fun updateOnTheFloor() {
        val availableStock = availableStock.value
        val sgf = sgf.value

        if (availableStock == null || sgf == null) {
            this.onTheFloor.value = null
            return
        }

        val onTheFloor = availableStock.minus(sgf)

        this.onTheFloor.value = if (onTheFloor > 0) onTheFloor else 0
    }

    fun updateFillAmount() {
        val assq = assq.value
        val flex = flex.value

        if (assq == null || flex == null) {
            this.fillAmount.value = null
            return
        }

        val fillAmount = assq.plus(flex)

        this.fillAmount.value = if (fillAmount > 0) fillAmount else 0
    }

    fun updateBackflow() {
        val onTheFloor = onTheFloor.value
        val fillAmount = fillAmount.value

        if (onTheFloor == null || fillAmount == null) {
            this.backflow.value = null
            return
        }

        val backflow = onTheFloor.minus(fillAmount)

        this.backflow.value = if (backflow > 0) backflow else 0
    }

    fun updateReturnBoxes() {
        val backflow = backflow.value
        val mupqPerBox = mupqPerBox.value

        if (backflow == null || mupqPerBox == null) {
            this.returnBoxes.value = null
            return
        }

        if (mupqPerBox == 0) {
            this.returnBoxes.value = 0
            return
        }

        val returnBoxes = backflow.toDouble().div(mupqPerBox)

        this.returnBoxes.value = if (returnBoxes > 0) returnBoxes.roundToInt() else 0
    }

    fun reset() {
        availableStock.value = null
        sgf.value = null
        onTheFloor.value = null

        assq.value = null
        flex.value = null
        fillAmount.value = null

        backflow.value = null
        mupqPerBox.value = null
        returnBoxes.value = null
    }

}