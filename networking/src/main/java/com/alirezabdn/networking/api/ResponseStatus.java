package com.alirezabdn.networking.api;

import com.alirezabdn.networking.api.offer.API;
import com.alirezabdn.networking.model.ResponseModel;

/**
 * Created by shadoWalker on 5/9/17.
 */

public interface ResponseStatus {
    void onSuccess(API offerAPI,
                   ResponseModel responseModel);

    void onFail(API offerAPI,
                String message,
                String errorCode,
                boolean canTry);
}
