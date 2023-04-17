package com.example.instagramclone.main_tabs.likedprofiles_tab.messsaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.R;
import com.example.instagramclone.reusable_code.ParseUtils.UtilsClass;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Message> messages;
    private Context context;

    private final int TYPE_RECEIVED = 0;
    private final int TYPE_SENT = 1;

    public MessageAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == TYPE_RECEIVED) {
            view = LayoutInflater.from(context).inflate(R.layout.received_message, parent, false);
            return new ReceivedMessageHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.sent_message_item, parent, false);
            return new SentMessageHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        if (holder.getItemViewType() == TYPE_RECEIVED) {
            ((ReceivedMessageHolder) holder).bind((Message) message);
        } else {
            ((SentMessageHolder) holder).bind((Message) message);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getUserclassPointer()== UtilsClass.getCurrentUser()) {
            return TYPE_SENT;
        } else {
            return TYPE_RECEIVED;
        }
    }

    static class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message_body);
            timeText = itemView.findViewById(R.id.message_time);
        }

        void bind(Message message) {
            messageText.setText(message.getMessageText());
            timeText.setText(message.getMessageTime());
        }
    }

    static class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message_body);
            timeText = itemView.findViewById(R.id.message_time);
        }

        void bind(Message message) {
            messageText.setText(message.getMessageText());
            timeText.setText(message.getMessageTime());
        }
    }
}
