package com.alirezabdn.networking.api.offer;

import android.util.Log;

import com.alirezabdn.networking.api.ReasonModel;
import com.alirezabdn.networking.api.ResponseStatus;
import com.alirezabdn.networking.model.InputModel;
import com.alirezabdn.networking.model.ProgressDialogInterface;
import com.alirezabdn.networking.model.RequestModel;
import com.alirezabdn.networking.model.ResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 6/10/2017.
 */

public abstract class API<GenericRequest extends InputModel, GenericResponse extends ResponseModel>
        extends ReasonModel implements CallApi<GenericRequest> {

    private GenericResponse responseModel;
    private boolean isRunning;
    private List<WrappedRequest> wrappedRequests;
    private ProgressDialogInterface progressDialogInterface;

    public API() {
        this.wrappedRequests = new ArrayList<>();
    }

    public API(ProgressDialogInterface progressDialogInterface) {
        this();
        this.progressDialogInterface = progressDialogInterface;
    }

    public ProgressDialogInterface getProgressDialogInterface() {
        return progressDialogInterface;
    }

    public void setProgressDialogInterface(ProgressDialogInterface progressDialogInterface) {
        this.progressDialogInterface = progressDialogInterface;
    }

    @Override
    public void callApi(ResponseStatus status, ProgressDialogInterface progressDialogInterface, GenericRequest inputModel) {
        Log.v("Sequence", "call api");
        WrappedRequest wrappedRequest = new WrappedRequest(status, progressDialogInterface, inputModel);
        wrappedRequests.add(wrappedRequest);
        resumeCalls();
    }

    public void callApi(ResponseStatus status, GenericRequest inputModel) {
        if (getProgressDialogInterface() == null)
            this.progressDialogInterface = new ProgressDialogInterface() {
                @Override
                public void showProgressDialog() {

                }

                @Override
                public void hideProgressDialog() {

                }
            };
        callApi(status, getProgressDialogInterface(), inputModel);
    }

    public void resumeCalls() {
        Log.v("Sequence", "resume calls");
        if (!wrappedRequests.isEmpty()) {
            Log.v("Sequence", "go if wrapped request is not empty");
            if (!isRunning()) {
                Log.v("Sequence", "call if is not running");
                wrappedRequests.get(0).call();
            }
        }
    }

    protected abstract Call<GenericResponse> getApi(GenericRequest inputModel);

    public GenericResponse getResponse() {
        return responseModel;
    }

    public void cancelCall() {
        isRunning = false;
        if (wrappedRequests == null)
            return;
        for (WrappedRequest wrappedRequest : wrappedRequests) {
            wrappedRequest.getResponseModelCall().cancel();
        }
        wrappedRequests.clear();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public class WrappedRequest implements Callback<GenericResponse> {
        private ResponseStatus responseStatus;
        private GenericRequest inputModel;
        private Call<GenericResponse> responseModelCall;

        public WrappedRequest(ResponseStatus responseStatus, ProgressDialogInterface progressDialogInterface, GenericRequest inputModel) {
            this.responseStatus = responseStatus;
            this.progressDialogInterface = progressDialogInterface;
            this.inputModel = inputModel;
            this.responseModelCall = getApi(inputModel);
        }

        public ResponseStatus getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(ResponseStatus responseStatus) {
            this.responseStatus = responseStatus;
        }

        public GenericRequest getInputModel() {
            return inputModel;
        }

        public void setInputModel(GenericRequest inputModel) {
            this.inputModel = inputModel;
        }

        public Call<GenericResponse> getResponseModelCall() {
            return responseModelCall;
        }

        public void setResponseModelCall(Call<GenericResponse> responseModelCall) {
            this.responseModelCall = responseModelCall;
        }

        public void call() {
            if (inputModel != null)
                Log.d("Req, API: " + API.this.getClass().getSimpleName(), new RequestModel(inputModel).toString());
            isRunning = true;
            showProgressDialog();
            this.responseModelCall = getResponseModelCall().clone();
            this.responseModelCall.enqueue(this);
        }

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                isRunning = false;
                responseModel = response.body();
                if (responseModel != null) {
                    if (!wrappedRequests.isEmpty())
                        wrappedRequests.remove(0);
                    Log.d("Res, API: " + API.this.getClass().getSimpleName(), responseModel.toString());
                    responseStatus.onSuccess(API.this, responseModel);
                    if (!wrappedRequests.isEmpty())
                        resumeCalls();
                } else {
                    Log.e("ERROR", "Response Body is null.");
                    onFailure(call, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            isRunning = false;
            if (t != null) {
                hideProgressDialog();
                try {
                    Log.e("Throw", t.getMessage());
                } catch (Exception e) {}
            }
            handleError(API.this, t, getResponseStatus());
        }

        protected ProgressDialogInterface progressDialogInterface;

        protected void showProgressDialog() {
            progressDialogInterface.showProgressDialog();
        }

        protected void hideProgressDialog() {
            progressDialogInterface.hideProgressDialog();
        }
    }
}
