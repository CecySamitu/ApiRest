package com.cecysamitu.disney.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cecysamitu.disney.R
import com.cecysamitu.disney.remote.CharacterAPI
import com.cecysamitu.disney.remote.model.ResponseDetail
import com.cecysamitu.disney.databinding.FragmentCharacterDetailBinding
import com.cecysamitu.disney.util.Constants
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val ARG_ID = "id"

class DetailFragment : Fragment() {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private var id: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString(ARG_ID)
            Log.d("DetailFragment", "Received ID: $id")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val characterApi = retrofit.create(CharacterAPI::class.java)

        val call = characterApi.getCharacterDetail(id)

        call.enqueue(object: Callback<ResponseDetail>{
            override fun onResponse(call: Call<ResponseDetail>, response: Response<ResponseDetail>) {
                binding.pbLoading.visibility = View.INVISIBLE
                Log.d(Constants.LOGTAG, response.toString())
                Log.d(Constants.LOGTAG, response.body().toString())

                if (response.isSuccessful) {
                    val apiResponseDetail = response.body()
                    if (apiResponseDetail != null) {
                        val characterDetail = apiResponseDetail.data

                        binding.tvTitle.text = characterDetail.name
                        if (characterDetail.films.isNotEmpty()) {
                            binding.tvLongDesc.text = characterDetail.films.joinToString(separator = "\n") {
                                "- $it"
                            }
                        } else {
                            binding.tvLongDesc.text = getString(R.string.noFilms)
                        }
                        Picasso.get()
                            .load(characterDetail.imageUrl)
                            .placeholder(R.drawable.bg_character)
                            .error(R.drawable.nodisponible)
                            .into(binding.ivImage)
                    } else {
                        binding.tvLongDesc.text = getString(R.string.noDataAvailable)
                    }
                    binding.pbLoading.visibility = View.INVISIBLE
                } else {
                    binding.tvLongDesc.text = getString(R.string.errorResponse)
                }
            }


            override fun onFailure(p0: Call<ResponseDetail>, t: Throwable) {
                Log.e("DetailFragment", "API call failed: ${t.message}")
                binding.tvTitle.text = getString(R.string.errorResponse)
            }
        })

    }

    companion object {
        @JvmStatic
        fun newInstance(id: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ID, id)
                }
            }
    }
}