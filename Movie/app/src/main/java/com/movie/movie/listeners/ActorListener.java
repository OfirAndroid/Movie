package com.movie.movie.listeners;

import com.movie.movie.models.ActorItem;

/*
* The ActorListener is used for passing the actor data received from the web to the class who implements it.
* */
public interface ActorListener {
	void OnReceivedActorDetails(ActorItem response); // this method is called when we get actor data
	void OnFailedToReceiveActorDetails(String error);// this method is called when we failed to get actor data
}
