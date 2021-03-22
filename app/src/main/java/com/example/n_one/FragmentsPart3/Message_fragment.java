package com.example.n_one.FragmentsPart3;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.n_one.Activites.MainNavigationActivity;
import com.example.n_one.R;
import com.example.n_one.objects.Chat;
import com.example.n_one.objects.Message;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Message_fragment extends Fragment {

    private static final String TAG = "Message_fragment";

    LinearLayout layoutJobs;
    RelativeLayout tab1,tab2,not_found,count_msg;
    TextView txt1,txt2;
    View v1,v2;

    List<Chat> mChats;

    private MainNavigationActivity baseActivity;
    public Message_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_message_fragment, container, false);
        tab1=view.findViewById(R.id.tab1);
        tab2=view.findViewById(R.id.tab2);
        txt1=view.findViewById(R.id.tab1_txt);
        txt2=view.findViewById(R.id.tab2_txt);
        v1=view.findViewById(R.id.tab1_line);
        v2=view.findViewById(R.id.tab2_line);
        not_found=view.findViewById(R.id.not_found);
        layoutJobs=view.findViewById(R.id.layoutJobs);

        count_msg=view.findViewById(R.id.count_msg);
        count_msg.bringToFront();
        layoutJobs.removeAllViews();



        try {
            fetchMessagesFromCSV();

            for (Chat chat: mChats) {
                Message lastMessage = chat.getLastMessage();

                LinearLayout layoutChatCard = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_chat_card, null);
                CircleImageView imageViewContactPhoto = layoutChatCard.findViewById(R.id.contact_photo_imageview);
                TextView textViewContactName = layoutChatCard.findViewById(R.id.contact_name_textview);
                TextView textViewJobTitle = layoutChatCard.findViewById(R.id.job_title_textview);
                TextView textViewLastMessage = layoutChatCard.findViewById(R.id.last_message_textview);
                TextView textViewTime = layoutChatCard.findViewById(R.id.chat_time_text_view);
                String participantKey;
                if (lastMessage.getSenderKey().equals("1")) {
                    participantKey = lastMessage.getReceiverKey();
                } else {
                    participantKey = lastMessage.getSenderKey();
                }
                textViewContactName.setText(participantKey);
                textViewLastMessage.setText(lastMessage.getText());

//                long time = Long.valueOf(lastMessage.getTimeStamp());
                String strTime = lastMessage.getTimeStamp();
                SimpleDateFormat parsingFormat2 = new SimpleDateFormat("yyyy/M/d\nHH:mmaa");
                SimpleDateFormat parsingFormat = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat timeOnlyFormat = new SimpleDateFormat("hh:mm aa");
                SimpleDateFormat withDayFormat = new SimpleDateFormat("MM/dd");
                SimpleDateFormat withYearFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date;
                try {
                    date = parsingFormat.parse(strTime);
                } catch (ParseException e) {
                    date = null;
                }

                if (date != null) {

                    String time;
                    if (date.getDay() == (new Date()).getDay()) {
                        time = timeOnlyFormat.format(date);
                    } else if (date.getDay() != (new Date()).getDay() &&
                    date.getYear() == (new Date()).getYear()) {
                        time = withDayFormat.format(date);
                    } else {
                        time = withYearFormat.format(date);
                    }
                    time = parsingFormat2.format(date);
                    textViewTime.setText(time);
                }

                layoutJobs.addView(layoutChatCard);

            }

            String me = "1";
            String chatsString = "\n";
            for (Chat chat: mChats) {
                if (chat.getParticipants().contains(me)) {
                    String participant = "";
                    for (String part: chat.getParticipants()) {
                        if (!part.equals(me)) {
                            participant = part;
                        }
                    }
                    chatsString+= "A Chat With Contact: " + participant + "\n";
                    for (Message message: chat.getMessages()) {
                        chatsString+= "Time: " + message.getTimeStamp() + " - ";
                        if (message.getSenderKey().equals(me)) {
                            chatsString += "me: ";
                        } else {
                            chatsString += participant + ": ";
                        }

                        chatsString+= message.getText() + "\n";
                    }
                    chatsString+= "\n---------------------------\n";
                }
            }

            Log.d(TAG, chatsString);

        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            fetchChatsFromJSON();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        (new DatabaseUtil()).fetchMessages();

        hideNoti();

        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt1.setTextColor(getResources().getColor(R.color.light_blue_menu));
                txt2.setTextColor(getResources().getColor(R.color.new_text_color));
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.GONE);
            }
        });
        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt1.setTextColor(getResources().getColor(R.color.new_text_color));
                txt2.setTextColor(getResources().getColor(R.color.light_blue_menu));
                v1.setVisibility(View.GONE);
                v2.setVisibility(View.VISIBLE);


            }
        });

        layoutJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                baseActivity.bottomNavigation(6);
            }
        });
        return view;
    }

    private void fetchChatsFromJSON() throws JSONException {
        JSONObject obj = new JSONObject(loadJSONFromAsset());
        List<String> keys = new ArrayList<>();
        for (int i = 0; i<obj.names().length();i++) {
            String key = (String) obj.names().get(i);
            keys.add(key);
        }

        Log.d(TAG, keys.toString());
//        JSONArray jsonArrayCities = obj.k;
//        List<List<Message>> chats = new ArrayList<>();
//        if (cities == null) {
//            cities = new ArrayList<>();
//        }
//        for (int j = 0; j < jsonArrayCities.length() ; j++) {
//            cities.add(jsonArrayCities.getString(j));
//        }
    }

    private void fetchMessagesFromCSV() throws IOException {
        InputStream inputStream = getActivity().getAssets().open("messages.csv");
//        List<Chat> chats = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String csvLine;
            boolean firstIter = true;
            while ((csvLine = reader.readLine()) != null) {
                if (firstIter) {
                    firstIter = false;
                    continue;
                }
                String[] row = csvLine.split(",,,");
                String sender = row[0];
                String receiver = row[1];
                String timestamp = row[3];
                HashSet<String> participants = new HashSet<>();
                participants.add(sender);
                participants.add(receiver);
                if (participants.contains("1")) {
                    Message message = new Message();
                    message.setSenderKey(sender);
                    message.setReceiverKey(receiver);
                    message.setText(row[2]);
                    message.setTimeStamp(row[3]);

                    boolean added = false;

                    if (mChats == null) mChats = new ArrayList<>();
                    outerloop:
                    for (Chat chat : mChats) {
                        if (chat.getParticipants().equals(participants)) {

                            chat.getMessages().add(message);
                            added = true;

                        }
                    }
                    if (!added) {
                        Chat chat = new Chat();
                        chat.setMessages(new ArrayList<>());
                        chat.getMessages().add(message);
                        mChats.add(chat);
                    }
                }
            }


            for (Chat chat: mChats) {
                Collections.sort(chat.getMessages());
            }
        Collections.sort(mChats, Collections.reverseOrder());

    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("messages.csv");
            int size = is.available();
            byte[]buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void hideNoti() {
        FrameLayout frameLayout = getActivity().findViewById(R.id.fLayoutNotif);
        frameLayout.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();

        FrameLayout frameLayout = getActivity().findViewById(R.id.fLayoutNotif);
        frameLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        baseActivity = (MainNavigationActivity) activity;
    }




}
