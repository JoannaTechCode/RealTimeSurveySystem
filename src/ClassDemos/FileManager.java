package ClassDemos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;

import static java.lang.Math.ceil;

public class FileManager {
    // region Setup
    String filename="SurveyByNetwork_SampleData.txt";
    String hashedFilename="HashedBinaryTree.txt";
    // endregion

    // region Methods

    /**
     * This method implements writing an array whose type is RecyclerData to a text file and saves the file to the disk.
     * @param hashedFile an array whose type is RecyclerData.
     */

    public void writeDataToFile(HashSet hashedFile)
    {
        try {
            BufferedWriter write=new BufferedWriter(new FileWriter(hashedFilename));
            /*for(int i=0; i<data.length;i++)
            {
                if(data[i]==null)
                {
                    break;
                }
                write.write(data[i].recyclerName+";"+data[i].recyclerAddress+";"+ data[i].recyclerPhone+";"
                        +data[i].recyclerWeb+";"+data[i].recycles);
                write.newLine();
            }*/

            String data=String.valueOf(hashedFile);
            write.write(data);
            write.close();
        } catch (Exception e)
        {
            System.err.println("Error Writing File");
        }

    }


    /**
     * This method implements reading all record lines from the text file under the specified path,
     * and then saving the lines into an array typed RecyclerData.
     *
     * @return  an array typed RecyclerData.
     */
    public  ArrayList<Object[]> readDataFromFile()
    {
        ArrayList<Object[]> data=new ArrayList<>();

        
        String[] fileData=new String[500];

        int count=0;
        try {
            BufferedReader reader=new BufferedReader(new FileReader(filename));
            String line;
           
            while((line=reader.readLine())!=null)
               {
                   fileData[count]=line;
                   count++;
               }
            int ammountOfQuestion=count/8;
            for (int i = 1; i <ammountOfQuestion; i++) {
                Object[] question=new Object[8];
                for (int j = 0; j < 8; j++) {

                    question[j]=fileData[i*8+j];

                }

                data.add(question);
            }


            
            reader.close();
            return data;
        } catch (Exception e) {
            return data;
        }

    }
    // endregion
}
