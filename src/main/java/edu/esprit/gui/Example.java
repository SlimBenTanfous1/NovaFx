package edu.esprit.gui;

import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;
public class Example {
    // Find your Account SID and Auth Token at twilio.com/console
    // and set the environment variables. See http://twil.io/secure
   
    String text;
    public void send_sms(String text) {

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+21696014188"),
                        new com.twilio.type.PhoneNumber(TWILIO_PHONE_NUMBER),
                        text)
                .create();

        System.out.println(message.getSid());
    }

    public void main(String[] args) {


        Example main=new Example();
        main.send_sms(text);
    }
}