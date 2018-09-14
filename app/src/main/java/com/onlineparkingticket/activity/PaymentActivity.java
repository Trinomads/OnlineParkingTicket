package com.onlineparkingticket.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.onlineparkingticket.R;
import com.onlineparkingticket.constant.AppGlobal;
import com.onlineparkingticket.constant.CommonUtils;
import com.onlineparkingticket.httpmanager.ApiHandlerToken;
import com.onlineparkingticket.model.PaymentModel;

import net.authorize.acceptsdk.AcceptSDKApiClient;
import net.authorize.acceptsdk.datamodel.common.Message;
import net.authorize.acceptsdk.datamodel.merchant.ClientKeyBasedMerchantAuthentication;
import net.authorize.acceptsdk.datamodel.transaction.CardData;
import net.authorize.acceptsdk.datamodel.transaction.EncryptTransactionObject;
import net.authorize.acceptsdk.datamodel.transaction.TransactionObject;
import net.authorize.acceptsdk.datamodel.transaction.TransactionType;
import net.authorize.acceptsdk.datamodel.transaction.callbacks.EncryptTransactionCallback;
import net.authorize.acceptsdk.datamodel.transaction.response.EncryptTransactionResponse;
import net.authorize.acceptsdk.datamodel.transaction.response.ErrorTransactionResponse;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("All")
public class PaymentActivity extends BaseActivity implements EncryptTransactionCallback {

    public static Context mContext;
    private LinearLayout lvNext;
    private EditText edCardNumber, edExp, edCVV;
    private AcceptSDKApiClient apiClient;

    private static final String API_LOGIN_ID = "2a924bVU9CnA";
    private static final String CLIENT_KEY = "3RvGyvNXG2dyv2ZwuW62ZBjW5Qn8y8Wr63eAgJ4nq998rExr75xDFJ4fwB3S7DUQv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mContext = PaymentActivity.this;
        init(PaymentActivity.this);
        setHeaderWithBack(getString(R.string.payment), true, false);
        Intent intent = getIntent();

        init();
    }

    private void init() {
        lvNext = (LinearLayout) findViewById(R.id.linear_Payment_PayNow);

        edCardNumber = (EditText) findViewById(R.id.ed_Payment_CardNumber);
        edExp = (EditText) findViewById(R.id.ed_Payment_ExpireDate);
        edCVV = (EditText) findViewById(R.id.ed_Payment_CVV);

        edExp.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0 && (editable.length() % 3) == 0) {
                    final char c = editable.charAt(editable.length() - 1);
                    if ('/' == c) {
                        editable.delete(editable.length() - 1, editable.length());
                    }
                }
                if (editable.length() > 0 && (editable.length() % 3) == 0) {
                    char c = editable.charAt(editable.length() - 1);
                    if (Character.isDigit(c) && TextUtils.split(editable.toString(), String.valueOf("/")).length <= 2) {
                        editable.insert(editable.length() - 1, String.valueOf("/"));
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        try {

            apiClient = new AcceptSDKApiClient.Builder(mContext, AcceptSDKApiClient.Environment.SANDBOX).connectionTimeout(4000) // optional connection time out in milliseconds
                    .build();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        lvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidField()) {
//                    uploadUserProfile();
                    sendPayment();
                }
            }
        });
    }

    String cardNumber, expDate, cvv;

    private boolean isValidField() {

        cardNumber = edCardNumber.getText().toString().trim();
        expDate = edExp.getText().toString().trim();
        cvv = edCVV.getText().toString().trim();

        if (!CommonUtils.isTextAvailable(cardNumber)) {
            CommonUtils.commonToast(mContext, getString(R.string.msg_plz_enter_card));
            return false;
        } else if (cardNumber.length() != 16) {
            CommonUtils.commonToast(mContext, getString(R.string.msg_plz_enter_card_valid));
            return false;
        } else if (!CommonUtils.isTextAvailable(expDate)) {
            CommonUtils.commonToast(mContext, getString(R.string.msg_plz_enter_exp_Date));
            return false;
        } else if (!validateCardExpiryDate(expDate)) {
            CommonUtils.commonToast(mContext, getString(R.string.msg_plz_enter_exp_Date_valid));
            return false;
        }/* else if (!checkFutureDate()) {
            CommonUtils.commonToast(mContext, getString(R.string.msg_plz_enter_exp_Date_future));
            return false;
        } */else if (!CommonUtils.isTextAvailable(cvv)) {
            CommonUtils.commonToast(mContext, getString(R.string.msg_plz_enter_cvv));
            return false;
        } else
            return true;
    }

    public boolean validateCardExpiryDate(String expiryDate) {
        return expiryDate.matches("(?:0[1-9]|1[0-2])/[0-9]{2}");
    }

    public boolean checkFutureDate() {
        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR) % 100;
        int month = c.get(Calendar.MONTH) + 1;

        if (Integer.parseInt(("20" + expDate.substring(3, 5))) >= year) {
            if (Integer.parseInt(expDate.substring(0, 1)) >= month) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void sendPayment() {

        try {
            EncryptTransactionObject transactionObject = prepareTransactionObject();

      /*
        Make a call to get Token API
        parameters:
          1) EncryptTransactionObject - The transactionObject for the current transaction
          2) callback - callback of transaction
       */
            apiClient.getTokenWithRequest(transactionObject, this);
        } catch (Exception e) {
            // Handle exception transactionObject or callback is null.
            AppGlobal.showLog(mContext, "Error Payment gateway : " + e.toString());
            e.printStackTrace();
        }
    }

    private EncryptTransactionObject prepareTransactionObject() {
        ClientKeyBasedMerchantAuthentication merchantAuthentication = ClientKeyBasedMerchantAuthentication.createMerchantAuthentication(API_LOGIN_ID, CLIENT_KEY);

        // create a transaction object by calling the predefined api for creation
        return TransactionObject.createTransactionObject(TransactionType.SDK_TRANSACTION_ENCRYPTION) // type of transaction object
                .cardData(prepareCardDataFromFields())// card data to get Token
                .merchantAuthentication(merchantAuthentication).build();
    }

    private CardData prepareCardDataFromFields() {
        return new CardData.Builder(cardNumber, expDate.substring(0, 1), "20" + expDate.substring(3, 5)).cvvCode(cvv).build();
//        return new CardData.Builder("4111111111111111", "10", "2018").cvvCode("100").build();
    }

    @Override
    public void onErrorReceived(ErrorTransactionResponse errorResponse) {
        Message error = errorResponse.getFirstErrorMessage();
        String errorString = error.getMessageCode() + "\n" + getString(R.string.message) + " : " + error.getMessageText();

        AppGlobal.showLog(mContext, "Error listener : " + errorString);
    }

    @Override
    public void onEncryptionFinished(EncryptTransactionResponse response) {
        String stFinish = "Descrption : " + response.getDataDescriptor() + "\n" + "Data Value : " + response.getDataValue();

        uploadUserProfile(response.getDataValue());

        AppGlobal.showLog(mContext, "Complete response : " + stFinish);
    }


    public void uploadUserProfile(String keyTrans) {
        if (CommonUtils.isConnectingToInternet(mContext)) {


            AppGlobal.showProgressDialog(mContext);

            Map<String, String> params = new HashMap<String, String>();
            params.put("transactionkey", keyTrans);
            params.put("cardnumber", "424242424242");
            params.put("expirationdate", "20/12");
            params.put("cardcode", "2151");
            params.put("taxamount", "10");
            params.put("dutyamount", "12");
            params.put("shippingamount", "50");
            params.put("userfirstname", "Test");
            params.put("userlastname", "Test 2");

            System.out.println("Map is " + params);
            new ApiHandlerToken(PaymentActivity.this).getApiService().createPayment(params).enqueue(new Callback<PaymentModel>() {
                @Override
                public void onResponse(Call<PaymentModel> call, Response<PaymentModel> response) {
                    AppGlobal.hideProgressDialog();
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        AppGlobal.showLog(mContext, "Response : " + jsonObj.getJSONObject("body").toString());

                        if (response.isSuccessful()) {
                            CommonUtils.commonToast(mContext, response.body().getMessages().getMessage().get(0).getCode());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        AppGlobal.showLog(mContext, "Error : " + e.toString());
                    }
                }

                @Override
                public void onFailure(Call<PaymentModel> call, Throwable t) {
                    AppGlobal.showLog(mContext, "Error : " + t.toString());
                    AppGlobal.hideProgressDialog();
                }
            });

        } else {
            Log.e("this", "error" + "no internet");
            CommonUtils.commonToast(mContext, getResources().getString(R.string.no_internet_exist));
        }

    }
}
