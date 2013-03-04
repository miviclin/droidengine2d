package com.miviclin.droidengine2d.graphics.sprites;

import java.util.ArrayList;

public class Animation {
	
	private ArrayList<AnimationFrame> frames;
	private int currentFrameIndex;
	private int elapsedTime;
	private boolean repeat;
	private boolean updateCompleted;
	
	public Animation() {
		this(10, true);
	}
	
	public Animation(boolean repeat) {
		this(10, repeat);
	}
	
	public Animation(int initialCapacity, boolean repeat) {
		this.frames = new ArrayList<AnimationFrame>(initialCapacity);
		this.currentFrameIndex = 0;
		this.elapsedTime = 0;
		this.repeat = repeat;
		this.updateCompleted = false;
	}
	
	public void addFrame(AnimationFrame frame) {
		frames.add(frame);
	}
	
	public void update(float delta) {
		if (frames.size() > 1) {
			elapsedTime += delta;
			if (elapsedTime > frames.get(currentFrameIndex).getTime()) {
				elapsedTime = 0;
				currentFrameIndex++;
				if (currentFrameIndex >= frames.size()) {
					currentFrameIndex = 0;
				}
			}
		}
	}
	
	public boolean updateNoRepeat(float delta) {
		if (!updateCompleted) {
			if (frames.size() > 1) {
				elapsedTime += delta;
				if (elapsedTime > frames.get(currentFrameIndex).getTime()) {
					elapsedTime = 0;
					currentFrameIndex++;
					if (currentFrameIndex >= frames.size()) {
						currentFrameIndex = 0;
						updateCompleted = true;
					}
				}
			}
		}
		return updateCompleted;
	}
	
	public void reset() {
		currentFrameIndex = 0;
		elapsedTime = 0;
		updateCompleted = false;
	}
	
	public AnimationFrame getCurrentFrame() {
		if (currentFrameIndex < frames.size()) {
			return frames.get(currentFrameIndex);
		}
		return null;
	}
	
}
