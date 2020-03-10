/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.FlipRelay;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());

  public Joystick primaryController = new Joystick(0);
  public Joystick secondaryController = new Joystick(1);
  public JoystickButton someButton = new JoystickButton(primaryController, 2);

  public OI() {
    someButton.whenPressed( new FlipRelay());
  }
  
  public double getPrimaryControllerLeftStickY() {
    return primaryController.getRawAxis(1);
    // return primaryController.getY(Hand.kLeft);
    // return primaryController.getLeftStickY();
  }

  public double getPrimaryControllerRightStickX() {
    return primaryController.getRawAxis(4);
    // return primaryController.getX(Hand.kRight);

    // return primaryController.getRightStickX();
  }

  public double getSecondaryControllerLeftStickY() {
    return secondaryController.getRawAxis(1);
    // return secondaryController.getY(Hand.kLeft);
    // return secondaryController.getLeftStickY();
  }

  public double getSecondaryControllerRightStickY() {
    return secondaryController.getRawAxis(5); // return secondaryController.getY(Hand.kRight);
                                              // return secondaryController.getLeftStickY();
  }

  public double getSecondaryControllerRightStickX() {
    return secondaryController.getRawAxis(4);
    // return secondaryController.getX(Hand.kRight);
    // return secondaryController.getRightStickX();
  }

  public double getSecondaryControllerLeftTrigger() {
    return secondaryController.getRawAxis(2);
  }

  public double getSecondaryControllerRightTrigger() {
    return secondaryController.getRawAxis(3);
  }

}
