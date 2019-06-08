package gb.stoliarenkoas.ru.material03;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import gb.stoliarenkoas.ru.material03.touch.TouchAdapter;

public class Adapter extends RecyclerView.Adapter implements TouchAdapter {
    private static final int SELF = 0;
    private static final int FRIEND = 1;
    private final List<Message> messages;

    public Adapter(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case SELF: {
                return new HolderSelf(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_self, parent, false));
            }
            default: {
                return new HolderFriend(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_friend, parent, false));
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Message message = messages.get(position);
        switch (message.getType()) {
            case SELF: {
                ((HolderSelf)holder).text.setText(message.getText());
                ((HolderSelf)holder).name.setText(message.getName());
                break;
            }
            case FRIEND: {
                ((HolderFriend)holder).text.setText(message.getText());
                ((HolderFriend)holder).name.setText(message.getName());
            }
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).getType().ordinal();
    }

    public static class HolderSelf extends RecyclerView.ViewHolder {
        TextView name;
        TextView text;

        public HolderSelf(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.chat_self_text);
            name = itemView.findViewById(R.id.chat_self_name);
        }
    }

    public static class HolderFriend extends RecyclerView.ViewHolder {
        TextView name;
        TextView text;

        public HolderFriend(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.chat_friend_text);
            name = itemView.findViewById(R.id.chat_friend_name);
        }
    }

    @Override
    public void onItemDelete(int position) {
        messages.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int from, int to) {
        Collections.swap(messages, from, to);
        notifyItemMoved(from, to);
    }
}
