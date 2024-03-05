package uz.bnabiyev.chatapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.bnabiyev.chatapp.ChatActivity
import uz.bnabiyev.chatapp.databinding.ItemUserBinding
import uz.bnabiyev.chatapp.models.User

class UserAdapter(private val context: Context, private val list: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.Vh>() {

    inner class Vh(private val itemUserBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {
        fun onBind(user: User) {
            itemUserBinding.tvName.text = user.name
            itemView.setOnClickListener {
                val intent = Intent(context, ChatActivity::class.java)

                intent.putExtra("name", user.name)
                intent.putExtra("uid", user.uid)

                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }
}