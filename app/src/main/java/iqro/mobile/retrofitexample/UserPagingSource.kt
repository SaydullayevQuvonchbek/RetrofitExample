package iqro.mobile.retrofitexample

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay


private const val STARTING_PAGE=0

class UserPagingSource(val api:API):PagingSource<Int,User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
      return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val startKey = params.key ?: STARTING_PAGE
        return try {
if (startKey!= STARTING_PAGE) delay(3000)

            val response = api.getAllUserByPage(startKey,params.loadSize)

            val userList = response.body()?.userList
            LoadResult.Page(
                userList ?: emptyList(),
                prevKey = if (startKey == STARTING_PAGE) null else startKey - 1,
                nextKey = if (userList == null || userList.isEmpty()) null else startKey + 1
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

    }
}