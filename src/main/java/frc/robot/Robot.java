/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ExampleSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static ExampleSubsystem m_subsystem = new ExampleSubsystem();
  public static OI m_oi;
  public static DrivetrainSubsystem drivetrain;
  public static Relay lightRelay = new Relay(0);
  double leftJoyY = 0, rightJoyX = 0;
  double leftOutput, rightOutput, slopeSpeed;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    drivetrain = new DrivetrainSubsystem();
    m_oi = new OI();
    m_chooser.setDefaultOption("Default Auto", new ExampleCommand());
    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", m_chooser);
    System.out.println("Current Relay:" + lightRelay.get());
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    leftJoyY = Robot.m_oi.getPrimaryControllerLeftStickY();
    rightJoyX = Robot.m_oi.getPrimaryControllerRightStickX();
    System.out.println("Joystick 0: " +Robot.m_oi.getPrimaryControllerLeftStickY());
    System.out.println("Joystick 1: " + Robot.m_oi.getPrimaryControllerRightStickX());
    // If it IS within the deadzone
    if (leftJoyY > -RobotMap.joystickDeadzone && leftJoyY < RobotMap.joystickDeadzone) {
      leftJoyY = 0;
    }
    if (rightJoyX > -RobotMap.joystickDeadzone && rightJoyX < RobotMap.joystickDeadzone) {
      rightJoyX = 0;
    }
    // If the right joystick (i.e: the turning axis) is not 0, then use Arcade drive
    // normally.
    // Otherwise, the robot should be going straight. Therefore, use
    // DriveStraightPID to go straight.
    // But if both joystick values are within the deadzones, they will both be 0.
    // Apply no power in that case.
    //System.out.println("Accelerate drive speed: " + Robot.drivetrain.accelerateDriveSpeed());
    //  System.out.println("Sloped drive speed: " + Robot.drivetrain.slopeDriveSpeed());
    if (leftJoyY !=0 || rightJoyX != 0) {
      slopeSpeed = Robot.drivetrain.slopeDriveSpeed(Robot.m_oi.getPrimaryControllerLeftStickY());
      rightJoyX = Robot.drivetrain.slopeDriveSpeed(rightJoyX);

      System.out.println("Current slopeSpeed: " + slopeSpeed);
      leftOutput = slopeSpeed - rightJoyX;
      rightOutput = slopeSpeed + rightJoyX;
      Robot.drivetrain.setLeftPower(leftOutput);
      Robot.drivetrain.setRightPower(rightOutput);
      System.out.println("Left Drive vs. Right Drive: " + leftOutput + " vs. " + rightOutput);
    }
    // right joystick is in the deadzone, left joystick is not; apply PID to keep
    // the robot going straight
    // both joystick values are within the deadzone
    else {
      Robot.drivetrain.setLeftPower(0.0);
      Robot.drivetrain.setRightPower(0.0);
    }
    drivetrain.updateOutputs();
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
  public static boolean isWithinDeadzone(double inputDouble) {
    if (inputDouble > -RobotMap.joystickDeadzone && inputDouble < RobotMap.joystickDeadzone) {
      return true;
    }
    return false;
  }
}
