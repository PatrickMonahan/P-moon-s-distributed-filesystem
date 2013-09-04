package authServer;
import java.sql.Timestamp;
import java.util.Date;



public class AuthenticationToken {
		//encrypted with client key
		private String timestamp;
		private String clientTGSKey;
		
		//ticket info - encrypted with TGS key - also include clientTGSKey, timestamp
		private String userID, clientAddr;
		
		public AuthenticationToken(String userID){
				this.userID = userID;
				clientTGSKey = Integer.toString((int)(Math.random() * 1000));
				//System.out.println("Token has been generated!");
				
				//System.out.println("Time:: "+System.currentTimeMillis()/1000);
				timestamp = Integer.toString((int)System.currentTimeMillis()/1000);
				//System.out.println(timestamp);
		}
		
		public String getSessKey(){
			return clientTGSKey;
		}
		
		public String makeTimeStamp(){
			return (Integer.toString((int)System.currentTimeMillis()/1000));
		}
		public String getTimeStamp(){
			return timestamp;
		}
		public String getUserID(){
			return userID;
		}
		public String getExpiry(){
			return (timestamp + 20);
		}
		public String makeSessionKey(){
			
			return Integer.toString((int)(Math.random() * 1000));
		}
		
}

