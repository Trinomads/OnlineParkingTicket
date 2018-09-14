package com.onlineparkingticket.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PaymentModel {
    @SerializedName("messages")
    @Expose
    private Messages messages;

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    public class Messages {

        @SerializedName("resultCode")
        @Expose
        private String resultCode;
        @SerializedName("message")
        @Expose
        private ArrayList<Message> message;

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public ArrayList<Message> getMessage() {
            return message;
        }

        public void setMessage(ArrayList<Message> message) {
            this.message = message;
        }

    }

    public class Message {

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("text")
        @Expose
        private String text;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

    }
}
