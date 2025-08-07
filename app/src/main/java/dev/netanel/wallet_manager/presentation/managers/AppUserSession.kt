package dev.netanel.wallet_manager.presentation.managers

import dev.netanel.wallet_manager.domain.models.AppUser

object AppUserSession {
    private var _appUser: AppUser? = null

    val appUser: AppUser?
        get() = _appUser

    fun setUser(user: AppUser?) {
        _appUser = user
    }

    fun clear() {
        _appUser = null
    }

    fun isLoggedIn(): Boolean = _appUser != null
}