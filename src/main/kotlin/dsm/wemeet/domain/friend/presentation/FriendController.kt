package dsm.wemeet.domain.friend.presentation

import dsm.wemeet.domain.friend.usecase.RequestFriendUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/friends")
class FriendController(
    private val requestFriendUseCase: RequestFriendUseCase
) {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{account-id}")
    fun requestFriend(@PathVariable("account-id") accountId: String) {
        requestFriendUseCase.execute(accountId)
    }
}
