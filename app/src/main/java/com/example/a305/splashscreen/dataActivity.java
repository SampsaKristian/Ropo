package com.example.a305.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

public class dataActivity extends AppCompatActivity {

    MqttAndroidClient mqttAndroidClient;
    final String serverUri = "tcp://ylowmv.messaging.internetofthings.ibmcloud.com:1883";
    String clientId = "a:ylowmv:ruuuterboyz";
    final String subscriptionTopic = "iot-2/type/its-car/id/306/evt/data/fmt/json";
    static final String mqttUsername = "a-ylowmv-lr1cz1ljzg";
    static final String mqttPassword = "TWyCAdjkEDG&B8mfbg\n";
    private String JSON_TEXT;
    private String temperature, humidity, state;

    private TextView temp = null;
    private TextView humi = null;
    private TextView sta = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_main);

        temp = (TextView) findViewById(R.id.temp_text);
        humi = (TextView) findViewById(R.id.humi_text);
        sta = (TextView) findViewById(R.id.sta_text);

        clientId = clientId + System.currentTimeMillis();

        // Create the client!
        mqttAndroidClient = new MqttAndroidClient(getApplicationContext(), serverUri, clientId);

        // CALLBACKS, these will take care of the connection if something unexpected happen





        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

                if (reconnect) {
                    addToHistory("Reconnected to : " + serverURI);
                    // Because Clean Session is true, we need to re-subscribe
                    subscribeToTopic();
                } else {
                    addToHistory("Connected to: " + serverURI);
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                addToHistory("The Connection was lost.");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                JSON_TEXT = new String(message.getPayload());
                addToHistory("Incoming message: " + new String(message.getPayload()));

                try {
                    // get JSONObject from JSON file
                    JSONObject obj = new JSONObject(JSON_TEXT);
                    // fetch JSONObject named employee
                    JSONObject data = obj.getJSONObject("data");
                    // get location name and probability
                    temperature = data.getString("temperature");
                    humidity = data.getString("humidity");
                    state = data.getString("state");
                    // set location name and probability in TextView's
                    temp.setText("Temperature: "+temperature);
                    humi.setText("Humidity: "+humidity);
                    sta.setText("Car state:" +state);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // THIS VARIABLE IS THE JSON DATA. you can use GSON to get the needed
                // data (temperature for example) out of it, and show it in a textview or something else
                String result = new String(message.getPayload());
            }

            private void toString(byte[] payload) {
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });



        // set up connection settings
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(mqttUsername);
        mqttConnectOptions.setPassword(mqttPassword.toCharArray());

        try {
            addToHistory("Connecting to " + serverUri);

            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    addToHistory("Failed to connect to: " + serverUri);
                    addToHistory(exception.getMessage());
                }
            });

        } catch (MqttException ex){
            ex.printStackTrace();
        }
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

    }
    // this could do something else, like update the new data to the layout!
    private void addToHistory(String mainText){
        Log.d("MYERROR", mainText);
    }

    // subscriber method
    public void subscribeToTopic(){
        try {
            mqttAndroidClient.subscribe(subscriptionTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    addToHistory("Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    addToHistory("Failed to subscribe");
                }
            });
        } catch (MqttException ex){
            System.err.println("Exception whilst subscribing");
            ex.printStackTrace();
        }
    }


}