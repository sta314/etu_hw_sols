`timescale 1ns / 1ps


module yerliaraba(
        input sensor1,sensor2,sensor3,sensor4,sensor5,sensor6,sensor7,
        output Out1,Out2,Out3,Out4,Out5,Out6
    );
    
    wire nSensor1,nSensor3,nSensor4;
    not (nSensor1,sensor1);
    not (nSensor3,sensor3);
    not (nSensor4,sensor4);
    
    and (Out1,nSensor1,sensor7);
   
    and (Out2,sensor1,sensor7);
    
    and (Out3,nSensor1,nSensor3,sensor4,sensor7);
    
    and (Out4,nSensor1,sensor3,nSensor4,sensor7);
    
    buf (Out5,sensor5);
	
    buf (Out6,sensor6);
endmodule
