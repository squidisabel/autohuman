package com.example.chatbot_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MessageListAdapter.ItemClickListener{
    private List<UserMessage> messages;

    private RecyclerView messageRecycler;
    private MessageListAdapter messageAdapter;
    private ImageButton button;
    private EditText input;
    private int nextIndex;
    private String msg;

    // Instantiate the RequestQueue.
    private RequestQueue queue;
    String url = "https://whydoesthisnotworkinaeoreinro.azurewebsites.net/response";
//    String url = "https://ed796e2e.ngrok.io/response";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        queue = Volley.newRequestQueue(this);

        nextIndex = 0;
        messages = new ArrayList<>();
        input = findViewById(R.id.chatbox);
        button = findViewById(R.id.sendbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(v);
                input.getText().clear();
            }
        });

        // set up the RecyclerView
        messageRecycler = findViewById(R.id.recyclerview_message_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        messageRecycler.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(messageRecycler.getContext(),
                layoutManager.getOrientation());
        messageRecycler.addItemDecoration(dividerItemDecoration);

        messageAdapter = new MessageListAdapter(this, messages);
        messageAdapter.setClickListener(this);
        messageRecycler.setAdapter(messageAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
    }

    public void onButtonClick(View view) {
        msg = input.getText().toString();
        UserMessage message = new UserMessage(msg, true);
        insertSingleItem(message);
        fetchReponse();
    }

    private void insertSingleItem(UserMessage message) {
        messages.add(nextIndex, message);
        messageAdapter.notifyItemInserted(nextIndex);
        ++nextIndex;
    }


    private void fetchReponse() {
        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "doc2vec.model");
            jsonBody.put("text", msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest req = new JsonObjectRequest(url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    UserMessage message = new UserMessage(response.getString("res"), false);
                    insertSingleItem(message);
                    messageRecycler.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", (Object) error.getStackTrace());
            }
        });
        req.setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);
    }
}
