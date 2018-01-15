package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Robotics on 1/12/2018.
 */

@TeleOp(name = "Holonomic Drive")
public class DriverControl extends OpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    @Override
    public void init() {

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    @Override
    public void loop() {

        allMovement();

        printRobot();
        printController();

    }

    public void allMovement(){

        float gamepad1LeftY = -gamepad1.left_stick_y;
        float gamepad1LeftX = gamepad1.left_stick_x;
        float gamepad1RightX = gamepad1.right_stick_x;

        // holonomic formulas

        float FrontLeft = -gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
        float FrontRight = gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
        float BackRight = gamepad1LeftY + gamepad1LeftX - gamepad1RightX;
        float BackLeft = -gamepad1LeftY + gamepad1LeftX - gamepad1RightX;

        // clip the right/left values so that the values never exceed +/- 1
        FrontRight = Range.clip(FrontRight, -1, 1);
        FrontLeft = Range.clip(FrontLeft, -1, 1);
        BackLeft = Range.clip(BackLeft, -1, 1);
        BackRight = Range.clip(BackRight, -1, 1);

        // write the values to the motors
        frontRight.setPower(FrontRight);
        frontLeft.setPower(FrontLeft);
        backLeft.setPower(BackLeft);
        backRight.setPower(BackRight);
    }

    public void printController(){
        print("***Controller 1 Info***");
        print("Up: " + gamepad1.dpad_up);
        print("Down: " + gamepad1.dpad_down);
        print("Left: " + gamepad1.dpad_left);
        print("Right: " + gamepad1.dpad_right);
        print("X: "+ gamepad1.x);
        print("Y:"+ gamepad1.y);
        print("B: "+ gamepad1.b);
        print("A: "+ gamepad1.a);
        print("Start: "+ gamepad1.start);
        print("Back: "+ gamepad1.back);
        print("LBumper: "+ gamepad1.left_bumper);
        print("RBumper: "+ gamepad1.right_bumper);
        print("LJoyStickButton: "+ gamepad1.left_stick_button);
        print("RJoyStickButton: "+ gamepad1.right_stick_button);
        print("RightTrigger: "+ String.format("%.2f", gamepad1.right_trigger));
        print("LeftTrigger: "+ String.format("%.2f", gamepad1.left_trigger));
        print("LeftJoyStickX"+ String.format("%.2f", gamepad1.left_stick_x));
        print("LeftJoyStickY"+ String.format("%.2f", -gamepad1.left_stick_y));
        print("RightJoyStickX"+ String.format("%.2f", gamepad1.right_stick_x));
        print("RightJoyStickY"+ String.format("%.2f", -gamepad1.right_stick_y));

        print("");

        print("***Controller 2 Info***");
        print("Up: "+ gamepad2.dpad_up);
        print("Down: "+ gamepad2.dpad_down);
        print("Left: "+ gamepad2.dpad_left);
        print("Right: "+ gamepad2.dpad_right);
        print("X: "+ gamepad2.x);
        print("Y:"+ gamepad2.y);
        print("B: "+ gamepad2.b);
        print("A: "+ gamepad2.a);
        print("Start: "+ gamepad2.start);
        print("Back: "+ gamepad2.back);
        print("LBumper: "+ gamepad2.left_bumper);
        print("RBumper: "+ gamepad2.right_bumper);
        print("LJoyStickButton: "+ gamepad2.left_stick_button);
        print("RJoyStickButton: "+ gamepad2.right_stick_button);
        print("RightTrigger: "+ String.format("%.2f", gamepad2.right_trigger));
        print("LeftTrigger: "+ String.format("%.2f", gamepad2.left_trigger));
        print("LeftJoyStickX"+ String.format("%.2f", gamepad2.left_stick_x));
        print("LeftJoyStickY"+ String.format("%.2f", -gamepad2.left_stick_y));
        print("RightJoyStickX"+ String.format("%.2f", gamepad2.right_stick_x));
        print("RightJoyStickY"+ String.format("%.2f", -gamepad2.right_stick_y));
    }

    public void printRobot(){

        print("***Robot Data***");
        print("Front Left Wheel Power: " + frontLeft.getPower());
        print("Front Right Wheel Power: " + frontRight.getPower());
        print("Back Left Wheel Power: " + backLeft.getPower());
        print("Back Right Wheel Power: " + backRight.getPower());

    }

    public void print(String str){
        telemetry.addLine(str);
    }

    public double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }
}
