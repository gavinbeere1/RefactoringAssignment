package bankexercise;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileSaving extends BankApplication {


	
	public void saveOpenValues(){		
		if (openValues){
			surnameTextField.setEditable(false);
			firstNameTextField.setEditable(false);
				
			table.get(currentItem).setSurname(surnameTextField.getText());
			table.get(currentItem).setFirstName(firstNameTextField.getText());
		}
	}	
	
	public void displayDetails(int currentItem) {	
				
		accountIDTextField.setText(table.get(currentItem).getAccountID()+"");
		accountNumberTextField.setText(table.get(currentItem).getAccountNumber());
		surnameTextField.setText(table.get(currentItem).getSurname());
		firstNameTextField.setText(table.get(currentItem).getFirstName());
		accountTypeTextField.setText(table.get(currentItem).getAccountType());
		balanceTextField.setText(table.get(currentItem).getBalance()+"");
		if(accountTypeTextField.getText().trim().equals("Current"))
			overdraftTextField.setText(table.get(currentItem).getOverdraft()+"");
		else
			overdraftTextField.setText("Only applies to current accs");
	
	}
	
	

	
	public static void openFileRead()
	   {
		
		table.clear();
			
		filechooser = new JFileChooser();
		int returnVal = filechooser.showOpenDialog(null);
		 
        if (returnVal == JFileChooser.APPROVE_OPTION) {

        } else {
                }

			
		      try // open file
		      {
		    	  if(filechooser.getSelectedFile()!=null)
		    		  input = new RandomAccessFile( filechooser.getSelectedFile(), "r" );
		      } // end try
		      catch ( IOException ioException )
		      {
		    	  JOptionPane.showMessageDialog(null, "File Does Not Exist.");
		      } // end catch
			
	   } // end method openFile
	
	
	

	
	public static void saveToFileAs()
	   {
		
		filechooser = new JFileChooser();
		
		 int returnVal = filechooser.showSaveDialog(null);
         if (returnVal == JFileChooser.APPROVE_OPTION) {
             File file = filechooser.getSelectedFile();
           
             fileToSaveAs = file.getName();
             JOptionPane.showMessageDialog(null, "Accounts saved to " + file.getName());
         } else {
             JOptionPane.showMessageDialog(null, "Save cancelled by user");
         }
        
     	    
	         try {
	        	 if(filechooser.getSelectedFile()==null){
	        		 JOptionPane.showMessageDialog(null, "Cancelled");
	        	 }
	        	 else
	        		 output = new RandomAccessFile(filechooser.getSelectedFile(), "rw" );
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      
	      
	     
	   }
	
	public static void closeFile() 
	   {
	      try // close file and exit
	      {
	         if ( input != null )
	            input.close();
	      } // end try
	      catch ( IOException ioException )
	      {
	         
	    	  JOptionPane.showMessageDialog(null, "Error closing file.");
	      } // end catch
	   } // end method closeFile
	
	public static void readRecords()
	   {
	
	      RandomAccessBankAccount record = new RandomAccessBankAccount();

	      

	      try // read a record and display
	      {
	         while ( true )
	         {
	            do
	            {
	            	if(input!=null)
	            		record.read( input );
	            } while ( record.getAccountID() == 0 );

	       
	            
	            BankAccount ba = new BankAccount(record.getAccountID(), record.getAccountNumber(), record.getFirstName(),
	                    record.getSurname(), record.getAccountType(), record.getBalance(), record.getOverdraft());
	            
	            
	            Integer key = Integer.valueOf(ba.getAccountNumber().trim());
			
				int hash = (key%TABLE_SIZE);
		
				
				while(table.containsKey(hash)){
			
					hash = hash+1;
				}
				
	            table.put(hash, ba);
		

	         } // end while
	      } // end try
	      catch ( EOFException eofException ) // close file
	      {
	         return; // end of file was reached
	      } // end catch
	      catch ( IOException ioException )
	      {
	    	  JOptionPane.showMessageDialog(null, "Error reading file.");
	         System.exit( 1 );
	      } // end catch
	   }
	
public static void saveToFile(){
		
	
		RandomAccessBankAccount record = new RandomAccessBankAccount();
	

	      
	      for (Map.Entry<Integer, BankAccount> entry : table.entrySet()) {
			   record.setAccountID(entry.getValue().getAccountID());
			   record.setAccountNumber(entry.getValue().getAccountNumber());
			   record.setFirstName(entry.getValue().getFirstName());
			   record.setSurname(entry.getValue().getSurname());
			   record.setAccountType(entry.getValue().getAccountType());
			   record.setBalance(entry.getValue().getBalance());
			   record.setOverdraft(entry.getValue().getOverdraft());
			   
			   if(output!=null){
			   
			      try {
						record.write( output );
					} catch (IOException u) {
						u.printStackTrace();
					}
			   }
			   
			}
    	  
	      
	}
	
	
}
