package com.alirezabdn.networking;

import ir.raha.offer.networking.api.offer.AddAlarm;
import ir.raha.offer.networking.api.offer.AddChannel;
import ir.raha.offer.networking.api.offer.BaseGetOffers;
import ir.raha.offer.networking.api.offer.GetAccessToken;
import ir.raha.offer.networking.api.offer.GetAlarm;
import ir.raha.offer.networking.api.offer.GetAutoComplete;
import ir.raha.offer.networking.api.offer.GetCarousel;
import ir.raha.offer.networking.api.offer.GetGalacticalOfferAPIRanges;
import ir.raha.offer.networking.api.offer.GetItemDetail;
import ir.raha.offer.networking.api.offer.GetStores;
import ir.raha.offer.networking.api.offer.Registration;
import ir.raha.offer.networking.api.offer.SetChannel;
import ir.raha.offer.networking.model.RequestModel;
import ir.raha.offer.networking.model.ResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by shadoWalker on 5/9/17.
 */

public interface ApiInterface {
    @POST("registration")
    Call<Registration.RegistrationResponse> registration(@Body RequestModel request);

    @POST
    Call<BaseGetOffers.GetOffersResponse> getOffers(@Header("AUTHORIZATION") String session, @Body RequestModel requestModel, @Url String url);

    @POST("get/auto_complete")
    Call<GetAutoComplete.GetAutoCompleteResponse> getAutoComplete(@Body RequestModel requestModel);

    @GET("get/galactical_ranges")
    Call<GetGalacticalOfferAPIRanges.GetGalacticalOfferRangesResponse> getGalacticalOfferRanges();

    @POST("logout")
    Call<ResponseModel> logout(@Header("AUTHORIZATION") String session);

    @POST("get/item_detail")
    Call<GetItemDetail.GetItemDetailResponse> getItem(@Header("AUTHORIZATION") String session, @Body RequestModel requestModel);

    @POST("get/access_token")
    Call<GetAccessToken.GetAccessTokenResponse> getAccessToken(@Body RequestModel request);

    @POST("add/channel")
    Call<AddChannel.AddChannelResponse> addChannel(@Body RequestModel request);

    @POST("set/channel")
    Call<SetChannel.SetChannelResponse> setChannel(@Header("AUTHORIZATION") String session, @Body RequestModel request);

    @GET("get/stores")
    Call<GetStores.GetStoresResponse> getStores();

    @POST("verification_call")
    Call<ResponseModel> verificationCall(@Body RequestModel request);

    @POST("add/alarm")
    Call<AddAlarm.AddAlarmResponse> addAlarm(@Header("AUTHORIZATION") String session, @Body RequestModel request);

    @POST("get/alarm")
    Call<GetAlarm.GetAlarmResponseModel> getAlarm(@Header("AUTHORIZATION") String session);

    @POST("remove/alarm")
    Call<ResponseModel> removeAlarm(@Header("AUTHORIZATION") String session, @Body RequestModel request);

    @POST("add/archive")
    Call<ResponseModel> addArchive(@Header("AUTHORIZATION") String session, @Body RequestModel request);

    @POST("get/archive")
    Call<BaseGetOffers.GetOffersResponse> getArchive(@Header("AUTHORIZATION") String session);

    @POST("remove/archive")
    Call<ResponseModel> removeArchive(@Header("AUTHORIZATION") String session, @Body RequestModel request);

    @POST("get/item_list")
    Call<BaseGetOffers.GetOffersResponse> getItemList(@Body RequestModel request);

    @POST("get/carousel")
    Call<GetCarousel.GetCarouselResponseModel> getCarousel(@Header("AUTHORIZATION") String session, @Body RequestModel request);
}
