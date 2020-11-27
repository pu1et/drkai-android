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
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drkaiproject.Constants;
import com.drkaiproject.R;
import com.drkaiproject.chat.ChatAdapter;
import com.drkaiproject.chat.ChatItem;
import com.drkaiproject.lex.InteractiveVoiceActivity;
import com.drkaiproject.lex.TextActivity;
import com.roughike.bottombar.BottomBar;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

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

    private AppCompatImageButton btn_send;
    private EditText msg_send;
    private ChatAdapter chatAdapter;
    private ArrayList<ChatItem> chatList;


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

        chatList = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatList);

        btn_send = view.findViewById(R.id.btn_send);
        msg_send = view.findViewById(R.id.msg_send);

        btn_send.setOnClickListener(this);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        final RecyclerView recyclerView = view.findViewById(R.id.chat_list);
        final LinearLayoutManager manager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);

        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);


        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(chatAdapter);


        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String time = format.format(now);

        try {
            String openResp = new Constants.ChatBot(view.getContext()).execute((String) null).get();//AsyncTask 시작시킴
            //String sendResp = new Constants.ChatBot(getApplicationContext()).execute("너는 뭐해").get();
            if(openResp != null ){//|| sendResp != null) {
                String openMsg = Constants.ext_from_openMsg(openResp);
                //sendResp = Constants.ext_from_sendMsg(sendResp);
                chatList.add(new ChatItem("챗봇", openMsg, time, Constants.LEFT_MSG));
                Log.v("chatList","[left] msg: "+openMsg);
            }

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        final ConstraintLayout rootLayout = view.findViewById(R.id.fragment_chatbot_layout);
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                BottomBar bottomBar = getActivity().findViewById(R.id.bottombar);
                int heightdiff = rootLayout.getRootView().getHeight() - rootLayout.getHeight();
                Log.v("heightdiff",""+heightdiff);
                if(heightdiff > 500){
                        bottomBar.setVisibility(View.GONE);
                }else{
                    bottomBar.setVisibility(View.VISIBLE);
                }
            }
        });


        return view;
    }

    /**
     * On-click listener for buttons text and voice buttons.
     *
     * @param v {@link View}, instance of the button component.
     */
    public void onClick(final View v) {
        switch ((v.getId())) {
            case R.id.btn_send:
                Date now = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                String msg = msg_send.getText().toString();
                String time = format.format(now);

                chatList.add(0,new ChatItem(null, msg, time, Constants.RIGHT_MSG));
                chatAdapter.notifyDataSetChanged();
                msg_send.setText("");

                try {
                    String sendResp = new Constants.ChatBot(getView().getContext()).execute(msg).get();
                    if(sendResp != null) {
                        String sendMsg = Constants.ext_from_openMsg(sendResp);
                        if(sendMsg == null) sendMsg = Constants.ext_from_sendMsg(sendResp);
                        chatList.add(0,new ChatItem("챗봇", sendMsg, time, Constants.LEFT_MSG));
                        chatAdapter.notifyDataSetChanged();
                        Log.v("chatList","[left] msg: "+sendMsg);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.v("chatList","[right] msg: "+msg);
                break;
        }
    }
}