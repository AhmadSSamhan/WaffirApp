package com.android.waffirapp.network;


import com.android.waffirapp.model.EmptyResponse;
import com.android.waffirapp.model.GetAddressResponse;
import com.android.waffirapp.model.ListOfProductResponse;
import com.android.waffirapp.model.LoginResponse;
import com.android.waffirapp.model.OffersResponse;
import com.android.waffirapp.model.ProductsResponse;
import com.android.waffirapp.model.ProvincesResponse;
import com.android.waffirapp.model.SingleProductsResponse;
import com.android.waffirapp.model.SliderResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
/**
 * Created by Ahmad.Samhan.
 */
public interface RetrofitInterface {

    // Offers list
    @Headers("Content-Type: application/json")
    @GET("GetOffer/en")
    Call<OffersResponse> getOffersResponseCall();

    // Home page - slider image list
    @Headers("Content-Type: application/json")
    @GET("GetImagesSlider/en")
    Call<SliderResponse> getSliderImagesResponse();

    // Home page - Products list
    @Headers("Content-Type: application/json")
    @GET("GetProductsBySectionId/en")
    Call<List<ProductsResponse>> getProductsResponseCall();

    // Home page - Products list
    @Headers("Content-Type: application/json")
    @GET("GetAllProducst/en")
    Call<ListOfProductResponse> getListOfProductResponseCall(@Query("id") String id, @Query("pageSize") String pageSize, @Query("pageNumber") String pageNumber);

    // Single Products details
    @Headers("Content-Type: application/json")
    @GET("GetSingleProducts/en")
    Call<SingleProductsResponse> getSingleProductsResponseCall(@Query("id") String id, @Query("CustomrerID") String CustomerID);

    // Login
    @Headers("Content-Type: application/json")
    @GET("GetLoginCustomer/en")
    Call<LoginResponse> getLoginResponseCall(@Query("Password") String password, @Query("Username") String Username);

    // Favourite
    @Headers("Content-Type: application/json")
    @GET("PostFavorite/en")
    Call<EmptyResponse> getFavoriteProductResponse(@Query("CustomerID") String CustomerID, @Query("ProductsID") String ProductsID);

    // Get Province by user
    @Headers("Content-Type: application/json")
    @GET("GetProvince/en")
    Call<ProvincesResponse> getListOfProvincesResponseCall();

    // Get Address by user
    @Headers("Content-Type: application/json")
    @GET("GetAddress/en")
    Call<GetAddressResponse> getListOfAddressResponseCall(@Query("CustomerID") String CustomerID);

    // Delete Address by user
    @Headers("Content-Type: application/json")
    @GET("DeleteAddress/en")
    Call<EmptyResponse> getDeleteAddressResponseCall(@Query("id") String id);

    // Edit Address by user
    @Headers("Content-Type: application/json")
    @GET("EditAddress/en")
    Call<EmptyResponse> getEditAddressResponseCall(@Query("AddID") String AddID, @Query("CustomerID") String CustomerID,
                                                   @Query("Name") String Name, @Query("StreetOne") String StreetOne,
                                                   @Query("StreetTow") String StreetTow, @Query("Billlding") String Billlding,
                                                   @Query("Mobile") String Mobile, @Query("Note") String Note, @Query("ProvincesID") String ProvincesID,
                                                   @Query("latitude") String latitude, @Query("longitude") String longitude);
    // Add Address by user
    @Headers("Content-Type: application/json")
    @GET("PostAddress/en")
    Call<EmptyResponse> getAddAddressResponseCall(@Query("CustomerID") String CustomerID, @Query("Name") String Name,
                                                  @Query("StreetOne") String StreetOne, @Query("StreetTow") String StreetTow,
                                                  @Query("Billlding") String Billlding, @Query("Mobile") String Mobile,
                                                  @Query("Note") String Note, @Query("ProvincesID") String ProvincesID,
                                                  @Query("latitude") String latitude, @Query("longitude") String longitude);
}
