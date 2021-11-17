package ru.boringowl.scheduleapp.domain.model


class UsersEntity {



    var usersId: Long? = null
    

    var login: String? = null


    var password: String? = null


    var email: String? = null


    var usersName: String? = null


    var roles: List<UserRoleEntity>? = null

}

