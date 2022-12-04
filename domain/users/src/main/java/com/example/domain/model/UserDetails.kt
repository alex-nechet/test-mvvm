package com.example.domain.model

import androidx.annotation.StringRes
import com.example.domain.model.UserBaseInfo

data class UserDetails(val headerInfo: UserBaseInfo, val footerInfo : List<Data>)

data class Data(@StringRes val fieldRes: Int, val text: String)