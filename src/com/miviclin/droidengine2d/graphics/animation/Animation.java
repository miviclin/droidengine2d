/*   Copyright 2013-2014 Miguel Vicente Linares
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.miviclin.droidengine2d.graphics.animation;

import java.util.ArrayList;

/**
 * Animation.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Animation {

	private enum State {
		INITIALIZED,
		RUNNING,
		PAUSED,
		FINISHED;
	}

	private ArrayList<AnimationStateListener> listeners;
	private ArrayList<AnimationFrame> frames;
	private int currentFrameIndex;
	private float elapsedTime;
	private boolean loopModeEnabled;
	private State state;

	/**
	 * Creates a new Animation with initial capacity for 10 frames and loop mode enabled by default.
	 */
	public Animation() {
		this(10, true);
	}

	/**
	 * Creates a new Animation with initial capacity for 10 frames by default.
	 * 
	 * @param loopMode If enabled, the animation will loop forever, otherwise, it will not loop.
	 */
	public Animation(boolean loopMode) {
		this(10, loopMode);
	}

	/**
	 * Creates a new Animation.
	 * 
	 * @param initialCapacity Number of frames that can be added before resizing the array of frames.
	 * @param loopMode If true, the animation will loop, otherwise it won't.
	 */
	public Animation(int initialCapacity, boolean loopMode) {
		this.listeners = new ArrayList<AnimationStateListener>();
		this.frames = new ArrayList<AnimationFrame>(initialCapacity);
		this.currentFrameIndex = 0;
		this.elapsedTime = 0;
		this.loopModeEnabled = loopMode;
		this.state = State.INITIALIZED;
	}

	/**
	 * Adds a frame to this Animation.
	 * 
	 * @param frame AnimationFrame to be added.
	 */
	public void addFrame(AnimationFrame frame) {
		frames.add(frame);
	}

	/**
	 * Returns all the AnimationFrames of this Animation.
	 * 
	 * @return AnimationFrames
	 */
	public ArrayList<AnimationFrame> getFrames() {
		return frames;
	}

	/**
	 * Returns the current frame of this Animation.
	 * 
	 * @return AnimationFrame or null if this Animation does not contain any frame.
	 */
	public AnimationFrame getCurrentFrame() {
		if (frames.size() > 0) {
			return frames.get(currentFrameIndex);
		}
		return null;
	}

	/**
	 * Updates this Animation and returns the current AnimationFrame.
	 * 
	 * @param delta Elapsed time since the last update.
	 * @return AnimationFrame or null if this Animation does not contain any frame.
	 */
	public AnimationFrame update(float delta) {
		if (state == State.INITIALIZED) {
			state = State.RUNNING;
			notifyAnimationStarted();
		}
		if ((state == State.RUNNING) && (frames.size() > 0)) {
			elapsedTime += delta;
			if (elapsedTime > frames.get(currentFrameIndex).getDelay()) {
				elapsedTime = 0;
				currentFrameIndex++;
				notifyAnimationFrameChanged();
				if (currentFrameIndex >= frames.size()) {
					currentFrameIndex = 0;
					notifyAnimationLoopFinished();
					if (!loopModeEnabled) {
						state = State.FINISHED;
						currentFrameIndex = frames.size() - 1;
						notifyAnimationFinished();
					}
				}
			}
		}
		if (frames.size() > 0) {
			return frames.get(currentFrameIndex);
		}
		return null;
	}

	/**
	 * Resets the state of this Animation. The list of AnimationFrames will not be reset.
	 */
	public void reset() {
		currentFrameIndex = 0;
		elapsedTime = 0;
		state = State.INITIALIZED;
	}

	/**
	 * Removes all the AnimationFrames of this Animation and resets it.
	 */
	public void clear() {
		currentFrameIndex = 0;
		elapsedTime = 0;
		state = State.INITIALIZED;
		frames.clear();
	}

	/**
	 * Returns true if the loop mode is enabled.
	 * 
	 * @return true if the loop mode is enabled, false otherwise
	 */
	public boolean isLoopModeEnabled() {
		return loopModeEnabled;
	}

	/**
	 * Enables or disables the loop mode.
	 * 
	 * @param loopMode true to enable the loop mode, false to disable it.
	 */
	public void setLoopModeEnabled(boolean loopMode) {
		this.loopModeEnabled = loopMode;
		if (state == State.FINISHED) {
			state = State.RUNNING;
			currentFrameIndex = 0;
		}
	}

	/**
	 * Pauses this Animation if it has not finished (if loop mode is enabled, the Animation is never considered
	 * finished).
	 */
	public void pause() {
		if (state != State.FINISHED) {
			state = State.PAUSED;
			notifyAnimationPaused();
		}
	}

	/**
	 * Resumed this Animation if it was paused.
	 */
	public void resume() {
		if (state == State.PAUSED) {
			state = State.RUNNING;
			notifyAnimationResumed();
		}
	}

	/**
	 * Returns true if this Animation is running, and false if it is paused or finished.
	 * 
	 * @return true if this Animation is running, false otherwise
	 */
	public boolean isRunning() {
		return state == State.RUNNING;
	}

	/**
	 * Returns true if this Animation is paused.
	 * 
	 * @return true if this Animation is paused, false otherwise
	 */
	public boolean isPaused() {
		return state == State.PAUSED;
	}

	/**
	 * Returns true if this Animation is finished.<br>
	 * If loop mode is enabled, an Animation is never considered finished.
	 * 
	 * @return true if this Animation is finished, false otherwise
	 */
	public boolean isFinished() {
		return state == State.FINISHED;
	}

	/**
	 * Registers an {@link AnimationStateListener} in this Animation.
	 * 
	 * @param listener AnimationStateListener
	 */
	public void addAnimationStateListener(AnimationStateListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes the specified {@link AnimationStateListener} from the list of listeners of this animation.
	 * 
	 * @param listener AnimationStateListener
	 * @return true if the specified listener was removed, false otherwise
	 */
	public boolean removeAnimationStateListener(AnimationStateListener listener) {
		return listeners.remove(listener);
	}

	/**
	 * Notifies all AnimationStateListeners that this Animation has started.<br>
	 * Calls {@link AnimationStateListener#onAnimationStarted(Animation)} in every registered listener.
	 */
	protected void notifyAnimationStarted() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).onAnimationStarted(this);
		}
	}

	/**
	 * Notifies all AnimationStateListeners that this Animation has been paused.<br>
	 * Calls {@link AnimationStateListener#onAnimationPaused(Animation)} in every registered listener.
	 */
	protected void notifyAnimationPaused() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).onAnimationPaused(this);
		}
	}

	/**
	 * Notifies all AnimationStateListeners that this Animation has been resumed.<br>
	 * Calls {@link AnimationStateListener#onAnimationResumed(Animation)} in every registered listener.
	 */
	protected void notifyAnimationResumed() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).onAnimationResumed(this);
		}
	}

	/**
	 * Notifies all AnimationStateListeners that the AnimationFrame has changed.<br>
	 * Calls {@link AnimationStateListener#onAnimationFrameChanged(Animation)} in every registered listener.
	 */
	protected void notifyAnimationFrameChanged() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).onAnimationFrameChanged(this);
		}
	}

	/**
	 * Notifies all AnimationStateListeners that this Animation has looped.<br>
	 * Calls {@link AnimationStateListener#onAnimationLoopFinished(Animation)} in every registered listener.
	 */
	protected void notifyAnimationLoopFinished() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).onAnimationLoopFinished(this);
		}
	}

	/**
	 * Notifies all AnimationStateListeners that this Animation has finished. If the loop mode is enabled, the Animation
	 * never finishes, so this method will never be called.<br>
	 * Calls {@link AnimationStateListener#onAnimationFinished(Animation)} in every registered listener.
	 */
	protected void notifyAnimationFinished() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).onAnimationFinished(this);
		}
	}

}
