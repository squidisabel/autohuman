package com.example.chatbot_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MessageListAdapter.ItemClickListener{
    private List<UserMessage> messages;

    private RecyclerView messageRecycler;
    private MessageListAdapter messageAdapter;
    private ImageButton button;
    private EditText input;
    private int nextIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        nextIndex = 0;

        messages = new ArrayList<>();

        input = findViewById(R.id.chatbox);

        // set up the RecyclerView
        messageRecycler = findViewById(R.id.recyclerview_message_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        messageRecycler.setLayoutManager(layoutManager);
        button = findViewById(R.id.sendbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(v);
                input.getText().clear();
            }
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(messageRecycler.getContext(),
                layoutManager.getOrientation());
        messageRecycler.addItemDecoration(dividerItemDecoration);

        messageAdapter = new MessageListAdapter(this, messages);
        messageAdapter.setClickListener(this);
        messageRecycler.setAdapter(messageAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + messageAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    public void onButtonClick(View view) {
        UserMessage message = new UserMessage(input.getText().toString(), true);
        insertSingleItem(message);
        UserMessage message1 = getReply(message);
        insertSingleItem(message1);
    }

    private UserMessage getReply(UserMessage message) {
        return new UserMessage("reply" + message.getText(), false);
    }

    private void insertSingleItem(UserMessage message) {
        messages.add(nextIndex, message);
        messageAdapter.notifyItemInserted(nextIndex);
        ++nextIndex;
    }
}
