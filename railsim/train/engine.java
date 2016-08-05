/*
 * $Revision: 18 $
 * Copyright 2008 js-home.org
 * $Name: not supported by cvs2svn $
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.railsim.train;

/**
 *
 * @author js
 */
public class engine {

	idtype id = null;
	String name = "";

	/**
	 * Creates a new instance of engine
	 */
	public engine(idtype _id, String n) {
		id = _id;
		name = n;
	}
	/**
	 * Holds value of property power.
	 */
	private int power;

	/**
	 * Getter for property power.
	 *
	 * @return Value of property power.
	 */
	public int getPower() {
		return this.power;
	}

	/**
	 * Setter for property power.
	 *
	 * @param power New value of property power.
	 */
	public void setPower(int power) {
		this.power = power;
	}
	/**
	 * Holds value of property maxspeed.
	 */
	private int maxspeed;

	/**
	 * Getter for property maxspeed.
	 *
	 * @return Value of property maxspeed.
	 */
	public int getMaxspeed() {
		return this.maxspeed;
	}

	/**
	 * Setter for property maxspeed.
	 *
	 * @param maxspeed New value of property maxspeed.
	 */
	public void setMaxspeed(int maxspeed) {
		this.maxspeed = maxspeed;
	}
	/**
	 * Holds value of property acceleration.
	 */
	private float acceleration;

	/**
	 * Getter for property acceleration.
	 *
	 * @return Value of property acceleration.
	 */
	public float getAcceleration() {
		return this.acceleration;
	}

	/**
	 * Setter for property acceleration.
	 *
	 * @param acceleration New value of property acceleration.
	 */
	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}
	/**
	 * Holds value of property deceleration.
	 */
	private float deceleration;

	/**
	 * Getter for property deceleration.
	 *
	 * @return Value of property deceleration.
	 */
	public float getDeceleration() {
		return this.deceleration;
	}

	/**
	 * Setter for property deceleration.
	 *
	 * @param deceleration New value of property deceleration.
	 */
	public void setDeceleration(float deceleration) {
		this.deceleration = deceleration;
	}

	/**
	 * Getter for property name.
	 *
	 * @return Value of property name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Getter for property id.
	 *
	 * @return Value of property id.
	 */
	public idtype getId() {
		return this.id;
	}
}
