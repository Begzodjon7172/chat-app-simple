package uz.bnabiyev.chatapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth
import uz.bnabiyev.chatapp.databinding.ItemReceiveBinding
import uz.bnabiyev.chatapp.databinding.ItemSendBinding
import uz.bnabiyev.chatapp.models.Message

class MessageAdapter(private val context: Context, private val list: ArrayList<Message>) :
    RecyclerView.Adapter<ViewHolder>() {

    val ITEM_SEND = 1
    val ITEM_RECEIVE = 2

    inner class SendViewHolder(private val itemSendBinding: ItemSendBinding) :
        ViewHolder(itemSendBinding.root) {
        fun onBind(message: Message) {
            itemSendBinding.tvMessage.text = message.message
        }
    }

    inner class ReceiveViewHolder(private val itemReceiveBinding: ItemReceiveBinding) :
        ViewHolder(itemReceiveBinding.root) {
        fun onBind(message: Message) {
            itemReceiveBinding.tvMessage.text = message.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == 1) {
            return SendViewHolder(
                ItemSendBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return ReceiveViewHolder(
                ItemReceiveBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = list[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) {
            return ITEM_SEND
        } else {
            return ITEM_RECEIVE
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.javaClass == SendViewHolder::class.java) {
            (holder as SendViewHolder).onBind(list[position])
        } else {
            (holder as ReceiveViewHolder).onBind(list[position])
        }
    }
}