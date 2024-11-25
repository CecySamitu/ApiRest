package com.cecysamitu.disney.view.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cecysamitu.disney.R
import com.cecysamitu.disney.remote.model.Character
import com.cecysamitu.disney.databinding.ElementBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CharacterViewHolder(
    private val binding: ElementBinding
) : RecyclerView.ViewHolder(binding.root) {

    // Función para convertir String a Date
    fun stringToDate(dateString: String, format: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"): Date? {
        return try {
            val formatter = SimpleDateFormat(format, Locale.getDefault())
            formatter.parse(dateString)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun formatDateToString(date: Date, format: String = "d 'de' MMMM 'de' yyyy"): String {
        val formatter = SimpleDateFormat(format, Locale("es", "ES"))
        return formatter.format(date)
    }

    fun bind(character: Character) {
        binding.tvTitle.text = character.name
        binding.tvReleased.text = binding.root.context.getString(R.string.fecha)

        val date = stringToDate(character.cratedAt)
        binding.tvRating.text = if (date != null) {
            formatDateToString(date)
        } else {
            "@string/fecha_no_disponible"
        }

        Glide.with(binding.root.context)
            .load(character.imageUrl)
            .placeholder(R.drawable.bg_character)
            .error(R.drawable.nodisponible)
            .into(binding.ivThumbnail)
    }
}
