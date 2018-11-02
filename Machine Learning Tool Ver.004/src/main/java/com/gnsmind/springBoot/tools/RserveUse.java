package com.gnsmind.springBoot.tools;

import org.rosuda.REngine.*;
import org.rosuda.REngine.Rserve.*;

public class RserveUse {

	public RserveUse() {
		// TODO Auto-generated constructor stub
	}

	public void getMain() throws REXPMismatchException {

		try {

			RConnection c = getLocalRconnection();// make a new local connection
			double[] myvalues = {1.0, 1.5, 2.2, 0.5, 0.9, 1.12};										
			double d[] = c.eval("rnorm(10)").asDoubles();
			org.rosuda.REngine.REXP result = c.eval("R.version.string");
			System.out.println(result.asString());
			
			c.eval(String.format("g <- '%s'", "Hello R…"));
			result = c.eval("g");
			System.out.println("Greeting R test …: " + result.asString());
			c.eval("data = read.table(\"http://data.princeton.edu/wws509/datasets/cuse.dat\", header=T)");
			org.rosuda.REngine.RList list = c.eval("data").asList();
			/*
			RVector vector = c.eval("data").as
			System.out.println(factor.toString());
			*/
			String[] keys = list.keys();
			for (String key : keys) {
			    
				System.out.println("column->" + key);
			}
			
			c.assign("myvalues", myvalues);

			org.rosuda.REngine.REXP x2 = c.eval("mean(myvalues)");

			System.out.println(x2.asDouble());

			x2 = c.eval("sd(myvalues)");

			System.out.println(x2.asDouble());
			/*
			for(int i=0;i!=x.length();i++){
				
				System.out.println("data : " + );
			}
			*/
		} catch (REngineException e) {

			System.out.println(e.getMessage());
		}

	}

	private static RConnection getLocalRconnection() {
		try {
			return new RConnection();
		} catch (RserveException e) {
			// If we get this kind of error, it may be because Rserve is not
			// running
			if (e.getMessage().equals("Cannot connect: Connection refused")) {
				try {
					String rserveStartCommand = "R CMD Rserve --vanilla";
					// do a blocking call to the shell command for starting
					// Rserve
					int exitValue = Runtime.getRuntime().exec(rserveStartCommand).waitFor();
					// if it returned success, try connecting again
					if (exitValue == 0)
						return new RConnection();
					// otherwise, Rserve is probably not installed
					else
						System.err.println("Could not start Rserve - is it installed properly? Shell command \""
										+ rserveStartCommand
										+ "\" exited with exit value "
										+ exitValue + ".");
				} catch (Exception e1) {
				 	
					System.out.println(e.getMessage());
				}
			} else
		
				System.out.println(e.getMessage());
		}
		return null;
	}
}
