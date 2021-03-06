/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6824.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	private DifferentialDrive m_robotDrive
			= new DifferentialDrive(new Spark(0), new Spark(1));
	private Joystick m_stick = new Joystick(0);
	private Joystick m_controller = new Joystick(1);
	private Timer m_timer = new Timer();
	private int direction = 1;
	boolean xTurningControls = true;
	
	double linear_velocity;
	double turning_freq = .21;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
	}

	/**
	 * This function is run once each time the robot enters autonomous mode.
	 */
	@Override
	public void autonomousInit() {
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if (gameData.length() > 0)
		{
			if (gameData.charAt(0) == 'L')
			{
				// do this
				System.out.println("Switch on LEFT side!");
			} else {
				// do this 
				System.out.println("Switch on RIGHT side!");
			}
		}
		
		m_timer.reset();
		m_timer.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		// Drive for 2 seconds
		if (m_timer.get() < 2.0) {
			m_robotDrive.arcadeDrive(0.0, 0.0); // drive forwards half speed
		} else {
			m_robotDrive.stopMotor(); // stop robot
		}
	}

	/**
	 * This function is called once each time the robot enters teleoperated mode.
	 */
	@Override
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during teleoperated mode.
	 */
	@Override
	public void teleopPeriodic() {
		double driving_power = -(m_stick.getThrottle() -1) / 2;
		double xDrive = m_stick.getX() * turning_freq;
		double yDrive = m_stick.getY() * linear_velocity;
		double zDrive = m_stick.getZ() * turning_freq;
		
		if (m_stick.getRawButton(3))
		{
			driving_power += .5;
		}
		if(m_stick.getRawButtonPressed(7))
		{
			if (xTurningControls)
			{
				xTurningControls = false;
			}
			else
			{
				xTurningControls = true;
			}
			System.out.println(xTurningControls);
			
			if (m_stick.getTriggerPressed()) 
			{
				direction *= -1;
			}
			
			if (xTurningControls)
			{
				m_robotDrive.arcadeDrive(yDrive * driving_power, xDrive * driving_power);
			}
			else if (!xTurningControls)
			{
				m_robotDrive.arcadeDrive(yDrive * driving_power, zDrive * driving_power);
			}
			System.out.println("X-speed: " + xDrive + "\tY-speed: " + -yDrive + "\tZ-speed: " + zDrive + "\tSpeed: " + driving_power);
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
