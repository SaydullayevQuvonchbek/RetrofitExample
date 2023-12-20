package iqro.mobile.retrofitexample

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import iqro.mobile.retrofitexample.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var userAdapter: UserAdapter
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        todoAdapter= TodoAdapter()
        val retrofit = Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://reqres.in/")
            .build()
        val api = retrofit.create(API::class.java)
        userAdapter=UserAdapter()
        val maneger=LinearLayoutManager(this@MainActivity)
        binding.recyclerView.apply {

            layoutManager=maneger
            adapter=userAdapter
            addItemDecoration(DividerItemDecoration(this@MainActivity,maneger.orientation))
        }

        val flow : Flow<PagingData<User>> = Pager(
            config = PagingConfig(pageSize = 3, enablePlaceholders = false),
            pagingSourceFactory = {UserPagingSource(api)}
        ).flow
lifecycleScope.launch {
    flow.collect{
        userAdapter.submitData(it)
    }
}
        lifecycleScope.launch {
            userAdapter.loadStateFlow.collect{
                binding.progressHorizontal.isVisible=it.source.append is LoadState.Loading

            }
        }





//
//        lifecycleScope.launch {
//            val respons= api.getAllUserByPage(0)
//            if(respons.isSuccessful){
//                userAdapter.submitList(respons.body()?.userList)
//                val userListResponse=respons.body()
//                Log.d("Tag","onCreate: $userListResponse")
//            }
//            else {
//                Log.d("Tag", "onCreate: Something wrong")
//            }
//        }

//        lifecycleScope.launch {
//         val response=api.cresteUser(UserCreate("","","Mobil developer","Quvonchbek Saydullayev"))
//       if(response.isSuccessful){
//           Log.d("Tag","onCreate ${response.body()}")
//       }else{
//           Log.d("Tag", "onCreate: sorry sorry")
//       }
//        }
//        lifecycleScope.launch {
//         val response=api.delateUser(2)
//            Log.d("Tag", "onCreate: ${response.body()} ${response.code()}")
//       if(response.isSuccessful){
//           Log.d("Tag","onCreate ${response.body()}")
//       }else{
//           Log.d("Tag", "onCreate: sorry sorry")
//       }
//        }
//

//        lifecycleScope.launch {
//            val response = api.getTodoList().body()
//            response?.apply {
//                todoAdapter.submitList(this)
//            }
//
//            Log.d("Tag", "onCreate: ${response?.joinToString()}")
//        }

    }
}