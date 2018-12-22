package com.example.eebri.mqttjoystick;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import at.markushi.ui.CircleButton;
import de.hdodenhof.circleimageview.CircleImageView;
import io.github.controlwear.virtual.joystick.android.JoystickView;

public class MainActivity extends AppCompatActivity {
    public String TAG = "MainActivity";
    private MqttHelper mqttHelper;

    CircleImageView button1, button2, button3, button4, button5, button6;
    Button editButton;
    boolean editable;
    ConstraintLayout constraintLayout;
    SharedPreferences sharedPreferences;
    CircleButton connectButton;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startMqtt();

        constraintLayout = findViewById(R.id.constraint_layout);
        button1 = findViewById(R.id.image_button_1);
        button2 = findViewById(R.id.image_button_2);
        button3 = findViewById(R.id.image_button_3);
        button4 = findViewById(R.id.image_button_4);
        button5 = findViewById(R.id.image_button_5);
        button6 = findViewById(R.id.image_button_6);
        editButton = findViewById(R.id.edit_button);
        connectButton = findViewById(R.id.connect_button);
        connectButton.setColor(Color.RED);
        findViewById(R.id.reconnect_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mqttHelper != null) {
                    mqttHelper.destroy();
                }
                startMqtt();

            }
        });
        sharedPreferences = getSharedPreferences("MainActivity", MODE_PRIVATE);
        setButtons();
        JoystickView joystick = findViewById(R.id.joystick);

        joystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                Log.d(TAG, angle + "  " + strength);
                if (!editable) {
                    if (mqttHelper != null && mqttHelper.mqttAndroidClient.isConnected()) {
                        if (strength != 0) {
                            if (45 < angle && angle < 135 && strength > 50) {
                                mqttHelper.publish("w");
                            } else if (135 < angle && angle < 225 && strength > 50) {
                                mqttHelper.publish("a");
                            } else if (225 < angle && angle < 315 && strength > 50) {
                                mqttHelper.publish("s");
                            } else if (315 < angle || angle < 45 && strength > 50) {
                                mqttHelper.publish("d");
                            } else {
                                mqttHelper.publish("p");
                            }
                        } else {
                            mqttHelper.publish("p");
                        }
                    }
                }
            }
        }, 70);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editable) {
                    if (mqttHelper != null && mqttHelper.mqttAndroidClient.isConnected()) {
                        mqttHelper.publish(button1.getContentDescription().toString());
                    }
                } else {
                    Toast.makeText(MainActivity.this, button1.getContentDescription().toString() + " 長按編輯"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editable) {
                    if (mqttHelper != null && mqttHelper.mqttAndroidClient.isConnected()) {
                        //    vibrator.vibrate(100);
                        mqttHelper.publish(button2.getContentDescription().toString());
                    }
                } else {
                    Toast.makeText(MainActivity.this, button2.getContentDescription().toString() + " 長按編輯"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editable) {
                    if (mqttHelper != null && mqttHelper.mqttAndroidClient.isConnected()) {
                        //   vibrator.vibrate(100);
                        mqttHelper.publish(button3.getContentDescription().toString());
                    }
                } else {
                    Toast.makeText(MainActivity.this, button3.getContentDescription().toString() + " 長按編輯"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editable) {
                    if (mqttHelper != null && mqttHelper.mqttAndroidClient.isConnected()) {
                        // vibrator.vibrate(100);
                        mqttHelper.publish(button4.getContentDescription().toString());
                    }
                } else {
                    Toast.makeText(MainActivity.this, button4.getContentDescription().toString() + " 長按編輯"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editable) {
                    if (mqttHelper != null && mqttHelper.mqttAndroidClient.isConnected()) {
                        // vibrator.vibrate(100);
                        mqttHelper.publish(button5.getContentDescription().toString());
                    }
                } else {
                    Toast.makeText(MainActivity.this, button5.getContentDescription().toString() + " 長按編輯"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editable) {
                    if (mqttHelper != null && mqttHelper.mqttAndroidClient.isConnected()) {
                        // vibrator.vibrate(100);
                        mqttHelper.publish(button6.getContentDescription().toString());
                    }
                } else {
                    Toast.makeText(MainActivity.this, button6.getContentDescription().toString() + " 長按編輯"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editable = !editable;
                if (editable) {
                    editButton.setText("編輯中");
                    constraintLayout.setAlpha(0.5f);

                } else {
                    editButton.setText("編輯");
                    constraintLayout.setAlpha(0.9f);
                }
            }
        });

        button1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (editable) {
                    final EditText editText = new EditText(MainActivity.this);
                    editText.setText(button1.getContentDescription().toString());
                    editText.setSelection(button1.getContentDescription().toString().length());
                    AlertDialog alertDialog =
                            new AlertDialog.Builder(MainActivity.this)
                                    .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            sharedPreferences.edit().putString("button1", editText.getText().toString().trim()).apply();
                                            setButtons();
                                        }
                                    })
                                    .setView(editText).create();

                    alertDialog.show();
                }
                return true;
            }
        });

        button2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (editable) {
                    final EditText editText = new EditText(MainActivity.this);
                    editText.setText(button2.getContentDescription().toString());
                    editText.setSelection(button2.getContentDescription().toString().length());
                    AlertDialog alertDialog =
                            new AlertDialog.Builder(MainActivity.this)
                                    .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            sharedPreferences.edit().putString("button2", editText.getText().toString().trim()).apply();
                                            setButtons();
                                        }
                                    })
                                    .setView(editText).create();

                    alertDialog.show();
                }
                return true;
            }
        });

        button3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (editable) {
                    final EditText editText = new EditText(MainActivity.this);
                    editText.setText(button3.getContentDescription().toString());
                    editText.setSelection(button3.getContentDescription().toString().length());
                    AlertDialog alertDialog =
                            new AlertDialog.Builder(MainActivity.this)
                                    .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            sharedPreferences.edit().putString("button3", editText.getText().toString().trim()).apply();
                                            setButtons();
                                        }
                                    })
                                    .setView(editText).create();

                    alertDialog.show();
                }
                return true;
            }
        });

        button4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (editable) {
                    final EditText editText = new EditText(MainActivity.this);
                    editText.setText(button4.getContentDescription().toString());
                    editText.setSelection(button4.getContentDescription().toString().length());
                    AlertDialog alertDialog =
                            new AlertDialog.Builder(MainActivity.this)
                                    .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            sharedPreferences.edit().putString("button4", editText.getText().toString().trim()).apply();
                                            setButtons();
                                        }
                                    })
                                    .setView(editText).create();

                    alertDialog.show();
                }
                return true;
            }
        });
        button5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (editable) {
                    final EditText editText = new EditText(MainActivity.this);
                    editText.setText(button5.getContentDescription().toString());
                    editText.setSelection(button5.getContentDescription().toString().length());
                    AlertDialog alertDialog =
                            new AlertDialog.Builder(MainActivity.this)
                                    .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            sharedPreferences.edit().putString("button5", editText.getText().toString().trim()).apply();
                                            setButtons();
                                        }
                                    })
                                    .setView(editText).create();

                    alertDialog.show();
                }
                return true;
            }
        });
        button6.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (editable) {
                    final EditText editText = new EditText(MainActivity.this);
                    editText.setText(button6.getContentDescription().toString());
                    editText.setSelection(button6.getContentDescription().toString().length());
                    AlertDialog alertDialog =
                            new AlertDialog.Builder(MainActivity.this)
                                    .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            sharedPreferences.edit().putString("button6", editText.getText().toString().trim()).apply();
                                            setButtons();
                                        }
                                    })
                                    .setView(editText).create();

                    alertDialog.show();
                }
                return true;
            }
        });


    }

    private void setButtons() {
        String button1String = sharedPreferences.getString("button1", "");
        String button2String = sharedPreferences.getString("button2", "");
        String button3String = sharedPreferences.getString("button3", "");
        String button4String = sharedPreferences.getString("button4", "");
        String button5String = sharedPreferences.getString("button5", "");
        String button6String = sharedPreferences.getString("button6", "");
        if (button1String.isEmpty()) {
            button1.setContentDescription("1");
        } else {
            button1.setContentDescription(button1String);
        }
        if (button2String.isEmpty()) {
            button2.setContentDescription("2");
        } else {
            button2.setContentDescription(button2String);
        }
        if (button3String.isEmpty()) {
            button3.setContentDescription("3");
        } else {
            button3.setContentDescription(button3String);
        }
        if (button4String.isEmpty()) {
            button4.setContentDescription("4");
        } else {
            button4.setContentDescription(button4String);
        }
        if (button5String.isEmpty()) {
            button5.setContentDescription("5");
        } else {
            button5.setContentDescription(button5String);
        }
        if (button6String.isEmpty()) {
            button6.setContentDescription("6");
        } else {
            button6.setContentDescription(button6String);
        }


    }

    private void startMqtt() {

        Intent intent = getIntent();
        String host = intent.getStringExtra("host");
        String port = intent.getStringExtra("port");
        String topic = intent.getStringExtra("topic");


        mqttHelper = new MqttHelper(getApplicationContext(), host, port, topic);
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                connectButton.setColor(Color.GREEN);
            }

            @Override
            public void connectionLost(Throwable throwable) {
                connectButton.setColor(Color.RED);
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug", mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
        try {
            mqttHelper.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mqttHelper != null) {
            mqttHelper.destroy();
        }
    }
}
