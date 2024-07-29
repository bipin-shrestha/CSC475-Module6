package com.business.fitrack.ui.composables.product

import com.business.fitrack.domain.product.Product

data class ProductState(
    val product : Product? = null,
    val isLoading : Boolean = false,
    val isRefreshing : Boolean = false,
)
