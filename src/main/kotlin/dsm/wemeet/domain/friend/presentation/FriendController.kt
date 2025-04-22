package dsm.wemeet.domain.friend.presentation

import dsm.wemeet.domain.friend.usecase.HandleFriendRequestUseCase
import dsm.wemeet.domain.friend.usecase.RequestFriendUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/friends")
class FriendController(
    private val requestFriendUseCase: RequestFriendUseCase,
    private val handleFriendRequestUseCase: HandleFriendRequestUseCase
) {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{account-id}")
    fun requestFriend(@PathVariable("account-id") accountId: String) {
        requestFriendUseCase.execute(accountId)
    }

    @PatchMapping("/request/{friend-id}")
    fun handleFriendRequest(@PathVariable("friend-id") friendId: UUID, @RequestParam("status") status: Boolean) {
        handleFriendRequestUseCase.execute(friendId, status)
    }
}
