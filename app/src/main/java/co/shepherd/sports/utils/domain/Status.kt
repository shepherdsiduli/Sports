package co.shepherd.sports.utils.domain

sealed class Status {
    object SUCCESS: Status()
    object LOADING: Status()
    object ERROR: Status()
}