package com.drkaiproject.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.drkaiproject.R;
import com.drkaiproject.lex.InteractiveVoiceActivity;
import com.drkaiproject.lex.TextActivity;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatbotFragment extends Fragment implements View.OnClickListener{


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private JSONObject jsonObject;
    private static final String TAG = "MainActivity";
    private static final int REQUEST_RECORDING_PERMISSIONS_RESULT = 75;
    private Button textDemoButton;
    private Button speechDemoButton;


    public ChatbotFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.w("fragment changed","oncreateview");
        View view = inflater.inflate(R.layout.fragment_chatbot, container, false);
        init(view);




        return view;
    }

    /**
     * Initializes the application.
     */
    private void init(View view) {
        Log.e(TAG, "Initializing app");

        textDemoButton = (Button) view.findViewById(R.id.button_select_text);
        speechDemoButton = (Button) view.findViewById(R.id.button_select_voice);
        textDemoButton.setOnClickListener(this);
        speechDemoButton.setOnClickListener(this);

        // Starting with Marshmallow we need to explicitly ask if we can record audio
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.RECORD_AUDIO) ==
                    PackageManager.PERMISSION_GRANTED) {
                speechDemoButton.setEnabled(true);
            } else {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORDING_PERMISSIONS_RESULT);
            }
        } else {
            speechDemoButton.setEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_RECORDING_PERMISSIONS_RESULT) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(),
                        "LexSample will not be able to use the voice feature", Toast.LENGTH_SHORT).show();

                // Disable the button
                speechDemoButton.setEnabled(false);
            } else {
                speechDemoButton.setEnabled(true);
            }
        }
    }

    /**
     * On-click listener for buttons text and voice buttons.
     *
     * @param v {@link View}, instance of the button component.
     */
    public void onClick(final View v) {
        switch ((v.getId())) {
            case R.id.button_select_text:
                Intent textIntent = new Intent(getContext(), TextActivity.class);
                startActivity(textIntent);
                break;
            case R.id.button_select_voice:
                Intent voiceIntent = new Intent(getContext(), InteractiveVoiceActivity.class);
                startActivity(voiceIntent);
                break;
        }
    }
}