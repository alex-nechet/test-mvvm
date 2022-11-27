package com.example.details.model

import androidx.annotation.StringRes
import com.example.domain.model.BriefInfo

data class UserDetails(val headerInfo: BriefInfo, val footerInfo : List<Data>)

data class Data(@StringRes val fieldRes: Int, val text: String)