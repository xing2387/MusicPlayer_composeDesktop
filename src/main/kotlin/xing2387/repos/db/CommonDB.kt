package xing2387.repos.db

import xing2387.repos.bean.UserInfoEntity
import java.util.*

object CommonDB {
    val database by lazy { CommonDatabase(CommonDatabaseDriver()) }
    val queries by lazy { database.commonDatabaseQueries }

    fun insert(id: String, userName: String, avatar: String = "", token: String = "", ps: String = "") {
        System.out.println("insert $userName $id")
        queries.insert(id, userName, avatar, token, Base64.getEncoder().encode(ps.toByteArray()))
    }

    fun mapper(id: String, userName: String, avatar: String?, token: String?, ps: ByteArray?): UserInfoEntity {
        return UserInfoEntity(id, userName, avatar, token, Base64.getDecoder().decode(ps).toString())
    }

    fun getUserById(id: String) = queries.getUserById(id, mapper = ::mapper)

    fun selectAll() = queries.selectAll(mapper = ::mapper)
}