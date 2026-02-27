package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.FuelConstants.*;
import static frc.robot.Constants.LifterConstants.*;

public class LifterSubsystem extends SubsystemBase {
  private final SparkMax rightLifter;
  private final SparkMax leftLifter;

  SparkBaseConfig leftConfig = new SparkMaxConfig();
  SparkBaseConfig rightConfig = new SparkMaxConfig();

  DigitalInput leftLimit = new DigitalInput(0);
  DigitalInput rightLimit = new DigitalInput(1);


  
  
  /** Creates a new CANBallSubsystem. */
  // public CANFuelSubsystem(LightsSubsystem lights) {
  public LifterSubsystem() {
    // this.lights = lights;

    leftLifter = new SparkMax(LEFT_LIFTER_MOTOR_ID, MotorType.kBrushless);
    rightLifter = new SparkMax(RIGHT_LIFTER_MOTOR_ID, MotorType.kBrushless);
    
    //figure out leader follower config
    leftConfig
      .idleMode(IdleMode.kBrake);

    rightConfig
      .idleMode(IdleMode.kBrake);

    //apply configs
    leftLifter.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightLifter.configure(rightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

  }

  public void runLifter(double speed){
    if(leftLimit.get()){
      leftLifter.set(0);
      leftLifter.getEncoder().setPosition(0);
    }else{
      leftLifter.set(speed);
    }

    if(rightLimit.get()){
      rightLifter.set(0);
      leftLifter.getEncoder().setPosition(0);

    }else{
      rightLifter.set(speed);
    }

    // leftLifter.set(speed);
    // rightLifter.set(speed);
  }

  public Command runLifterCommand(double speed){
    return run(()->runLifter(0.4));
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("leftLimit", leftLimit.get());
    SmartDashboard.putBoolean("rightLimit", rightLimit.get());
    SmartDashboard.putNumber("leftLiftEncoder", leftLifter.getEncoder().getPosition());
    SmartDashboard.putNumber("rightLiftEncoder", rightLifter.getEncoder().getPosition());

  }
}
