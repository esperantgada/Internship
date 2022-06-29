package eg.esperantgada.internship.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import eg.esperantgada.internship.repository.FlickrImageRepository
import javax.inject.Inject

@HiltViewModel
class FlickrImageViewModel
@Inject constructor(
    flickrImageRepository: FlickrImageRepository
) : ViewModel(){

    //Retrieves the list of photos from the repository and prepare them for the UI
    val retrievedPhotos = flickrImageRepository.getApiPhoto().cachedIn(viewModelScope)

}