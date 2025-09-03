package dinino.marc.games.userflow.tetris.domain

import dinino.marc.games.userflow.common.data.Repository
import dinino.marc.games.userflow.common.domain.GameUseCases
import dinino.marc.games.userflow.common.domain.GameUseCasesImpl
import dinino.marc.games.userflow.tetris.data.TetrisGameData

class TetrisUseCases(
    repository: Repository<TetrisGameData>,
): GameUseCases<Repository<TetrisGameData>, TetrisGameData>
by GameUseCasesImpl(repository = repository)