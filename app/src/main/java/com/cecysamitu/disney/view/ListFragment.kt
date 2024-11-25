package com.cecysamitu.disney.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cecysamitu.disney.R
import com.cecysamitu.disney.remote.CharacterAPI
import com.cecysamitu.disney.remote.model.APIResponse
import com.cecysamitu.disney.databinding.FragmentCharactersListBinding
import com.cecysamitu.disney.util.Constants
import com.cecysamitu.disney.view.adapters.CharacterAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ListFragment : Fragment() {

    private var _binding: FragmentCharactersListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharactersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCharacters.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvCharacters.adapter = CharacterAdapter(mutableListOf()) { character ->
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val characterApi = retrofit.create(CharacterAPI::class.java)

        val call: Call<APIResponse> = characterApi.getCharacter("character?pageSize=7438")

        call.enqueue(object : Callback<APIResponse> {
            override fun onResponse(p0: Call<APIResponse>, response: Response<APIResponse>) {
                binding.pbLoading.visibility = View.INVISIBLE
                Log.d(Constants.LOGTAG, response.toString())
                Log.d(Constants.LOGTAG, response.body().toString())

                if (response.isSuccessful && response.body() != null) {
                    val characterList = response.body()?.data
                    if (characterList != null) {
                        binding.rvCharacters.adapter = CharacterAdapter(characterList) { character ->
                            character.id?.let { id ->
                                requireActivity().supportFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, DetailFragment.newInstance(id))
                                    .addToBackStack(null)
                                    .commit()
                            }
                        }
                        binding.pbLoading.visibility = View.INVISIBLE
                    }
                    Toast.makeText(requireActivity(),
                        getString(R.string.conexi_n_exitosa), Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("ListFragment", "Error de respuesta: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(p0: Call<APIResponse>, p1: Throwable) {
                binding.pbLoading.visibility = View.INVISIBLE
                Toast.makeText(requireActivity(),
                    getString(R.string.error_de_conexi_n), Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
