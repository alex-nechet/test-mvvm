package com.example.domain.model

import androidx.annotation.StringRes

data class UserDetails(val headerInfo: User, val footerInfo : List<Data>)

data class Data(@StringRes val fieldRes: Int, val text: String)