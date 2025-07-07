package dinino.marc.games.userflow.tictactoe.data

import dinino.marc.games.Database
import dinino.marc.games.userflow.common.data.DefaultRepository
import dinino.marc.games.userflow.common.data.Repository

class TicTacToeGameRepository(
    database: Database
): Repository<TicTacToeGame> by DefaultRepository<TicTacToeGame> (
    getItemsFromDatabase = {
        database.ticTacToeGameEntityQueries.in
    },
    setItemsToDatabase = {

    }
)