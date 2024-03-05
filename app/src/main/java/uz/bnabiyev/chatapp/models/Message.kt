package uz.bnabiyev.chatapp.models

class Message {
    var message: String? = null
    var senderId: String? = null

    constructor(message: String?, senderId: String?) {
        this.message = message
        this.senderId = senderId
    }

    constructor()
}