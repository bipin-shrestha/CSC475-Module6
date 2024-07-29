package com.business.fitrack.ui.composables.meal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.business.fitrack.domain.product.Product
import com.business.fitrack.domain.product.ProductRepository
import com.business.fitrack.domain.product.Statistic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    //TODO : stateFlow -> collectAsFlow with lifecycle
    val products: Flow<List<Product>> = flow {
        emitAll(productRepository.getAllProducts())
    }

    var stats = MutableStateFlow(Statistic())

    init {
        viewModelScope.launch {
            products.collect { list ->
                stats.value = stats.value.copy(
                    list.sumOf { it.fatsByQuantity() }.toInt(),
                    list.sumOf { it.carbsByQuantity() }.toInt(),
                    list.sumOf { it.proteinsByQuantity() }.toInt(),
                    list.sumOf { it.caloriesByQuantity() }.toInt(),
                )
            }
        }
    }

    fun removeProduct(product: Product) {
        viewModelScope.launch {
            productRepository.removeProduct(product)
        }
    }
}