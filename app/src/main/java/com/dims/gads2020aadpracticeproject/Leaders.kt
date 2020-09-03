package com.dims.gads2020aadpracticeproject

import com.google.gson.annotations.SerializedName

open class Leader(@Transient open val name: String, @Transient open val num: Int,
                  @Transient open val country: String, @Transient open val badgeUrl: String)

data class SkillLeader(override val name: String, @SerializedName("score") override val num: Int,
                       override val country: String, override val badgeUrl: String) :
    Leader(name, num, country, badgeUrl)

data class LearningLeader(
    override val name: String, @SerializedName("hours") override val num: Int,
    override val country: String, override val badgeUrl: String) :
    Leader(name, num, country, badgeUrl)