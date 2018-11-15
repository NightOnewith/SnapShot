package com.github.florent37.camerafragment.listeners;

/*
 * Created by florentchampigny on 13/01/2017.
 */

public interface CameraFragmentResultListener {

    //called when the photo is taken
    void onPhotoTaken(byte[] bytes);
}
