package com.example.tabbedactivity1.ui.main.gallery_fragment.gallery_utils;

/**
 * This interface helps us to ensure communication between our PictureBrowserFragment and it
 * recyclerView which acts as and indicator to it ViewPager of images
 * each time and item in the recyclerView is clicked we use the
 * OnImageIndicationClicked method to signal the fragment for changes
 */
public interface imageIndicatorListener {

    /**
     *
     * @param ImagePosition position of an item in the RecyclerView Adapter
     */
    void onImageIndicatorClicked(int ImagePosition);
}
