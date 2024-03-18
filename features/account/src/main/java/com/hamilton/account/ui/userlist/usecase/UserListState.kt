package com.hamilton.account.ui.userlist.usecase

import com.hamilton.entity.users.User

sealed class UserListState(){
    data object NoDataError: UserListState()
    data object Success:UserListState()
}