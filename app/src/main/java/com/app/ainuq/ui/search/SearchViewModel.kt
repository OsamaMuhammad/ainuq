package com.app.ainuq.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.app.ainuq.common.AuthStore
import com.app.ainuq.ui.cart.GlassItemUiModel
import com.app.ainuq.ui.home.ProductItemUiModel
import com.app.ainuq.ui.productDetail.ColorItemUiModel
import com.app.ainuq.utils.Constants
import com.app.ainuq.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authStore: AuthStore
) : ViewModel() {

    private val _filteredProducts: MutableLiveData<List<ProductItemUiModel>> = MutableLiveData()
    val filteredProducts: LiveData<List<ProductItemUiModel>> = _filteredProducts

    private var allProducts: List<ProductItemUiModel> = listOf()

    private val _eventState: MutableLiveData<Result<Unit>> = MutableLiveData()
    val eventState: LiveData<Result<Unit>> = _eventState

    init {
        allProducts = Constants.getAllProducts()
        getProducts("")
    }


    fun getProducts(searchString: String) {
        if (searchString.trim().isEmpty() || searchString.trim().length < 2) {
            _filteredProducts.value = allProducts
            return
        }

        _filteredProducts.value = allProducts.filter {
            it.name.toLowerCase().contains(searchString.trim().toLowerCase())
        }
    }


}