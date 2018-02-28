package com.alirezabdn.networking.api.offer;


import com.alirezabdn.networking.api.ResponseStatus;
import com.alirezabdn.networking.model.InputModel;
import com.alirezabdn.networking.model.ProgressDialogInterface;

/**
 * Created by shadoWalker on 5/9/17.
 */

public interface CallApi<RequestModel extends InputModel> {
    void callApi(ResponseStatus status, ProgressDialogInterface progressDialogInterface, RequestModel inputModel);
}
