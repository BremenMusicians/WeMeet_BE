package dsm.wemeet.domain.friend.usecase

import dsm.wemeet.domain.friend.repository.model.Friend
import dsm.wemeet.domain.friend.service.CheckFriendService
import dsm.wemeet.domain.friend.service.CommandFriendService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class ApplyFriendUseCase(
    private val checkFriendService: CheckFriendService,
    private val commandFriendService: CommandFriendService
) {

    fun execute(userId: UUID): UUID {
        checkFriendService.apply {  }

        val friend: Friend = commandFriendService.saveFriend(
            Friend(
                userId,
                testColumn = "TEST"
            )
        )

        return friend.id!!
    }
}