package dsm.wemeet.domain.friend.presentation

import dsm.wemeet.domain.friend.presentation.dto.response.UserListResponse
import dsm.wemeet.domain.friend.usecase.DeleteFriendUseCase
import dsm.wemeet.domain.friend.usecase.HandleFriendRequestUseCase
import dsm.wemeet.domain.friend.usecase.QueryMyFriendListUseCase
import dsm.wemeet.domain.friend.usecase.QueryUserListUseCase
import dsm.wemeet.domain.friend.usecase.RequestFriendUseCase
import jakarta.validation.constraints.Min
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
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
    private val handleFriendRequestUseCase: HandleFriendRequestUseCase,
    private val queryUserListUseCase: QueryUserListUseCase,
    private val queryMyFriendListUseCase: QueryMyFriendListUseCase,
    private val deleteFriendUseCase: DeleteFriendUseCase
) {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{account-id}")
    fun requestFriend(@PathVariable("account-id") accountId: String) {
        requestFriendUseCase.execute(accountId)
    }

    @PatchMapping("/request/{friend-id}")
    fun handleFriendRequest(@PathVariable("friend-id") friendId: UUID, @RequestParam("accept") accept: Boolean) {
        handleFriendRequestUseCase.execute(friendId, accept)
    }

    @GetMapping
    fun queryFriendCandidateList(
        @RequestParam(value = "page")
        @Min(0)
        page: Int,
        @RequestParam(value = "name", required = false, defaultValue = "") name: String
    ): UserListResponse {
        return queryUserListUseCase.execute(page, name)
    }

    @GetMapping("/my")
    fun getMyFriendList(
        @RequestParam(value = "page")
        @Min(0)
        page: Int,
        @RequestParam(value = "name", required = false, defaultValue = "") name: String
    ): UserListResponse {
        return queryMyFriendListUseCase.execute(page, name)
    }

    @DeleteMapping("/{account-id}")
    fun delteFriend(
        @PathVariable("account-id") accountId: String
    ) {
        deleteFriendUseCase.execute(accountId)
    }
}
