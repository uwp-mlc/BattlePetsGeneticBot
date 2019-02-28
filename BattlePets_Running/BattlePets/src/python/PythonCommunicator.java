package python;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PythonCommunicator {
	
	//public void getAISkillChoosen();
	
	public static void pythonChooseSkill()
	{
		String currentdirectory;
		try {
			currentdirectory = new java.io.File( "." ).getCanonicalPath();
			System.out.println(currentdirectory);

		    // Process builder makes it easy to pass parameters. Integers must be passed as strings
		    ProcessBuilder pb = new ProcessBuilder("python",currentdirectory+"\\src\\python\\test.py","Hello 1","Hello 2");
		    
		    Process p = pb.start();
		    
		    // Used buffered input reader to see python output
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String output = in.readLine();

            System.out.println(output);

            in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
