package com.example.details.mappers

import androidx.annotation.StringRes
import com.example.details.R
import com.example.domain.common.model.ErrorType


@StringRes
fun ErrorType.toErrorResource() = when(this){
    ErrorType.UNKNOWN_ERROR -> R.string.unknown_error
    else -> throw Exception("Error resource not not handled for:$this")
}