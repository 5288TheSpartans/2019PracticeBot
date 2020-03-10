/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class FlipRelay extends InstantCommand {
  /**
   * Add your docs here.
   */
  public FlipRelay() {
    super();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    if(Robot.lightRelay.get() == Value.kOff) {
      Robot.lightRelay.set(Value.kForward);
      System.out.println("Set to kForward!");
    }
    else {
      Robot.lightRelay.set(Value.kOff);
      System.out.println("Set to KOff!");
    }

  }

}
