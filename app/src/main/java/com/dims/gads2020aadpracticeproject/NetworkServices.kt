package com.dims.gads2020aadpracticeproject

import retrofit2.Call
import retrofit2.http.*

interface LeadersService {
    //previously called by landingActivity, changed to test altUrlMethod
    @GET("/api/hours")
    fun getLearningLeaders() : Call<List<LearningLeader>>
    @GET("/api/skilliq")
    fun getSkillLeaders() : Call<List<SkillLeader>>
}

interface SubmissionService{
    @FormUrlEncoded
    @POST
    // pass in the alternate submission url and handle submission
    fun postSubmission(@Url altUrl: String,
                       @Field("entry.1824927963")email: String,
                       @Field("entry.1877115667") fname: String,
                       @Field("entry.2006916086") lname: String,
                       @Field("entry.284483984") link: String) : Call<Void>
}