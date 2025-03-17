package dsm.wemeet.domain.friend.presentation

import dsm.wemeet.domain.friend.usecase.ApplyFriendUseCase
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/friends")
class FriendController(
    private val applyFriendUseCase: ApplyFriendUseCase
) {

    @PostMapping("/{user_id}")
    fun applyFriend(@PathVariable("user_id") userId: UUID): UUID =
        applyFriendUseCase.execute(userId)
}