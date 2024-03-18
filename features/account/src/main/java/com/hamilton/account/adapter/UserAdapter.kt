package com.hamilton.account.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hamilton.account.databinding.LayoutUserItemBinding
import com.hamilton.entity.users.User

class UserAdapter(
    private val listener: OnUserClick,
) : ListAdapter<User,UserAdapter.UserViewHolder>(USER_COMPARATOR) {

    /**
     * Esta interfaz es el contrato entre el adapter y el fragmento que lo contiene
     */

    interface OnUserClick {
        fun userClick(user: User) //Pulsacion corta
        fun userOnLongClick(user: User) //Pulsacion larga
        //fun deleteClick(user:User) //Eliminar usuario
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UserViewHolder(LayoutUserItemBinding.inflate(layoutInflater, parent, false))
    }
    /**
     * Función que ordena el dataset en base a una propiedad personalizado
     * */
    fun sort(){
        //Orden personalizado se establece mediante una propiedad
        val sortedUserList = currentList.sortedBy { it.email }
        submitList(sortedUserList)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        //Se accede a un elemento de la lista interna de ListAdapter mediante el método
        //getItem(position)
        val item = getItem(position)
        holder.bind(item)
    }

    /**
     * La clase ViewHolder contiene todos los elementos de View o del layout XML que sea ha inflado
     */

    inner class UserViewHolder(private val binding: LayoutUserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            with(binding) {
                tvUserName.text = user.displayName
                tvUserEmail.text = user.email

                //Manejar la pulsacion larga
                root.setOnLongClickListener { _ ->
                    listener.userOnLongClick(user)
                    //Se debe indicar al framework/android que se consume el evento
                    true
                    //return@setOnLongClickListener true

                }
            }
        }
    }

    companion object{
        private val USER_COMPARATOR = object:DiffUtil.ItemCallback<User>(){
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return  oldItem.email == newItem.email
            }
        }
    }
}