package com.vitaz.ricklepick.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vitaz.ricklepick.model.Character

class CharacterDetailsViewModel: ViewModel() {

    val characters = MutableLiveData<List<Character>>()

}