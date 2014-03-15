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
package com.miviclin.droidengine2d;

import android.util.Log;

import com.miviclin.droidengine2d.graphics.GLView;
import com.miviclin.droidengine2d.util.MutexLock;

/**
 * GameThread.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class GameThread implements Runnable {

	private enum State {
		INITIALIZING,
		RUNNING,
		PAUSED,
		TERMINATED;
	}

	private final int maxSkippedFrames;
	private final float idealTimePerFrame;
	private final AbstractGame game;
	private final EngineLock engineLock;
	private final MutexLock pauseLock;
	private final MutexLock terminateLock;
	private GLView glView;

	private State currentState;
	private boolean started;

	/**
	 * Creates a new GameThread. The max number of FPS the game will be able to run at is 30 by default and the max
	 * number of frames that will be updated before rendering once in case performance is bad is 5 by default.
	 * 
	 * @param game Game that this thread will manage.
	 * @param glView GLView where the Game is rendered.
	 * @param engineLock EngineLock used to synchronize both threads.
	 */
	public GameThread(AbstractGame game, GLView glView, EngineLock engineLock) {
		this(30, 5, game, glView, engineLock);

	}

	/**
	 * Creates a new GameThread. The max number of frames that will be updated before rendering once in case performance
	 * is bad is 5 by default.
	 * 
	 * @param maxFPS Max number of FPS the game will be able to run at.
	 * @param game Game that this thread will manage.
	 * @param glView GLView where the Game is rendered.
	 * @param engineLock EngineLock used to synchronize both threads.
	 */
	public GameThread(int maxFPS, AbstractGame game, GLView glView, EngineLock engineLock) {
		this(maxFPS, 5, game, glView, engineLock);
	}

	/**
	 * Creates a new GameThread.
	 * 
	 * @param maxFPS Max number of FPS the game will be able to run at.
	 * @param maxSkippedFrames Max number of frames that will be updated before rendering once in case performance is
	 *            bad.
	 * @param game Game that this thread will manage.
	 * @param glView GLView where the Game is rendered.
	 * @param engineLock EngineLock used to synchronize both threads.
	 */
	public GameThread(int maxFPS, int maxSkippedFrames, AbstractGame game, GLView glView, EngineLock engineLock) {
		if (game == null) {
			throw new IllegalArgumentException("The Game can not be null");
		}
		if (maxFPS <= 0) {
			throw new IllegalArgumentException("maxFPS must be greater than 0");
		}
		this.currentState = State.INITIALIZING;
		this.maxSkippedFrames = maxSkippedFrames;
		this.idealTimePerFrame = 1000 / maxFPS;
		this.game = game;
		this.glView = glView;
		this.engineLock = engineLock;
		this.pauseLock = new MutexLock();
		this.terminateLock = new MutexLock();
		this.started = false;
	}

	@Override
	public void run() {
		long startingTime;
		long frameTime;
		long waitingTime;
		int skippedFrames;

		while (currentState != State.TERMINATED) {
			if (currentState == State.PAUSED) {
				game.onEnginePaused();
				pauseLock.lock();
			}
			if (currentState == State.RUNNING) {
				startingTime = System.currentTimeMillis();
				skippedFrames = 0;
				if (engineLock.getAllowUpdateFlag().get()) {
					synchronized (engineLock.getLock()) {
						game.update(idealTimePerFrame);
						engineLock.getAllowUpdateFlag().set(false);
						glView.requestRender();
					}
				}
				frameTime = System.currentTimeMillis() - startingTime;
				waitingTime = (long) (idealTimePerFrame - frameTime);

				if (waitingTime > 0) {
					sleep(waitingTime, 0.333f);
				}
				while ((waitingTime < 0) && (skippedFrames < maxSkippedFrames) && (currentState == State.RUNNING)) {
					game.update(idealTimePerFrame);
					waitingTime += idealTimePerFrame;
					skippedFrames++;
				}
				if (BuildConfig.DEBUG) {
					if (skippedFrames > 0) {
						Log.d("SkippedFrames", skippedFrames + "");
					}
				}
			}
		}
		game.onEngineDisposed();
		terminateLock.unlock();
	}

	/**
	 * Causes the thread which sent this message to sleep for the given interval of time (given in milliseconds). This
	 * method is more precise than {@link Thread#sleep(long)} at the cost of more CPU load.<br>
	 * This method calls {@code Thread#sleep(1)} until the specified percentage of the specified time is reached. Then,
	 * {@code Thread#yield()} is called multiple times until the target time is reached.
	 * 
	 * @param sleepTimeMillis Target sleep time.
	 * @param sleepTimePercentage Percentage of the target time that {@link Thread#sleep(long)} will be excuted. This
	 *            should be a value between 0 and 1. For example: 0.333f.
	 * @see Thread#sleep(long)
	 * @see Thread#yield()
	 */
	private void sleep(long sleepTimeMillis, float sleepTimePercentage) {
		long diff;
		long prev = System.nanoTime();
		long sleep = sleepTimeMillis * 1000000;
		long limit = (long) (sleep * sleepTimePercentage);
		while ((diff = System.nanoTime() - prev) < sleep) {
			if (diff < limit) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					Log.w(GameThread.class.getSimpleName(), e.getMessage());
				}
			}
			else {
				Thread.yield();
			}
		}
	}

	/**
	 * Starts the GameThread.
	 */
	public void start() {
		Thread thread;
		if (started) {
			throw new IllegalThreadStateException("Thread already started.");
		}
		thread = new Thread(this);
		thread.setDaemon(true);
		started = true;
		currentState = State.RUNNING;
		thread.start();
	}

	/**
	 * Terminates the GameThread. This method should be called from the UI thread before the activity is destroyed.
	 */
	public void terminate() {
		currentState = State.TERMINATED;
		pauseLock.unlock();
		engineLock.getAllowUpdateFlag().set(true);
		terminateLock.lock();
	}

	/**
	 * Pauses the GameThread.
	 */
	public void pause() {
		currentState = State.PAUSED;
		engineLock.getAllowUpdateFlag().set(true);
	}

	/**
	 * Resumes the GameThread.
	 */
	public void resume() {
		if (currentState == State.PAUSED) {
			currentState = State.RUNNING;
			game.onEngineResumed();
			engineLock.getAllowUpdateFlag().set(true);
			pauseLock.unlock();
		}
	}

	/**
	 * Sets the GLView where the game is rendered.<br>
	 * This method is called by the framework.
	 * 
	 * @param glView GLView.
	 */
	void setGLView(GLView glView) {
		this.glView = glView;
	}

}
