package jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
public class JdbcDemo {
    static Connection myconn;	
    static Statement st;
        void getConnection(String username, String password){
            try {
                myconn = DriverManager.getConnection("jdbc:mysql://localhost:3306/deepak", "root", "deepak123");
                st=myconn.createStatement();
                //String user,pass;
                //Scanner s = new Scanner(System.in);
                //System.out.println("enter username");
                //user=s.nextLine();

                //System.out.println("enter password");
                //pass=s.nextLine();
                ResultSet result=st.executeQuery("select * from Login");
                //User u = new User();
                //u.level=-1;
                while(result.next()){
                    if(username.equals(result.getString("userID")) && password.equals(result.getString("password"))){
                            System.out.println("succesfully logged in!");
//                            u.level=Integer.parseInt(result.getString("level"));
//                            u.name=result.getString("name");
//                            u.photo=result.getString("image");
                            break;
                    }

                    //System.out.println(result.getString("username")+", "+result.getString("password"));
                }
//                if(u.level==-1){
//                        System.out.println("incorrect username/password");
//                }
//                return u;
            } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
//            return null;
        }
    void deleteCriminal(int cid){
        try{
            st.executeUpdate("DELETE FROM `deepak`.`criminal` WHERE `cid`='"+cid+"';");
        }catch(Exception e){
            e.printStackTrace();
        }
                
    }
    
    void addCrime(int cid, String type, String date, String city){
         String query2="INSERT INTO `deepak`.`crime` (`type`, `date`, `cityOfCrime`) VALUES ('"+type+"', '"+date+"', '"+city+"');";
         int crimeid;
         try{
             st.executeUpdate(query2);
             query2="select crimeid from `deepak`.`crime` where type='"+type+"' and date='"+date+"' and cityOfCrime='"+city+"';";
             System.out.println("crime id - ");
             
             ResultSet result=st.executeQuery(query2);
             result.next();
             crimeid=Integer.parseInt(result.getString("crimeid"));
             query2="INSERT INTO `deepak`.`crime_done_by` (`cid`, `crimeid`) VALUES ('"+cid+"', '"+crimeid+"');";
             st.executeUpdate(query2);
             query2="select";
         }catch(Exception e){
             e.printStackTrace();
         }
         
    }
    ArrayList<Integer> searchDNA(String dna){
    	try{
            String query2;
            query2="select cid, dna from `deepak`.`crime` where dna='"+dna+"';";
            
            st.executeQuery(query2);
            
            ResultSet result=st.executeQuery(query2);
            ArrayList<Integer> a = new ArrayList<Integer>();
            while(result.next()){
            	a.add(Integer.parseInt(result.getString("cid")));
            }
            return a;
        }catch(Exception e){
            e.printStackTrace();
        }
    	return null;
    }
    
    ArrayList<Integer> unsolvedCrimes(){
		ArrayList<Integer> a = new ArrayList<Integer>();
		try{
	        ResultSet result=st.executeQuery("select * from crime where crimeStatus = 'unsolved'");
	        
	        while(result.next()){
	                a.add(Integer.parseInt(result.getString("crimeid")));
	                System.out.println(result.getString("type"));
	                //System.out.println(result.getString("username")+", "+result.getString("password"));
	        }
	        
		} catch (Exception e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		}
	
	    	return a;
    }
    
    Criminal returnEverything(int cid){
        Criminal t = new Criminal();
        String query="select * from criminal natural join crime_done_by natural join crime where cID="+cid+";";
        try{
            ResultSet r = st.executeQuery(query);
            r.next();
            t.name=r.getString("name");
            t.age=Integer.parseInt(r.getString("age"));
            t.cid=Integer.parseInt(r.getString("cID"));
            t.type=r.getString("type");
            t.dateOfCrime=r.getString("date");
            t.placeofcrime=r.getString("cityOfCrime");
            t.status=r.getString("crimeStatus");
            return t;
        }catch(Exception e){
        e.printStackTrace();
        }
        return t;
    }
    
    ArrayList<Integer> searchByName(String name, int age, String crimecity, String datestart){
        try{
        	ArrayList<Integer> x = new ArrayList<Integer>();
        	int i=0;
        	String query="select * from criminal natural join crime_done_by natural join crime";
            if((age!=-1) || (!crimecity.equals("")) ||( !datestart.equals("")) || (!datestart.equals(""))){
            	query+=" where ";
            }
            if(age!=-1){
            	i++;
            	query+=" age>="+age+" ";
            }
            if(!crimecity.equals("")){
            	
            	if(i==1)
            		query+=" and ";
            	query+=" cityOfCrime='"+crimecity+"' ";
            	i++;
            }
            if(!datestart.equals("")){
            	
            	if(i>=1)
            		query+=" and ";
            	query+="date > '"+datestart+"' ";
            	i++;
            }
//            if(!dateend.equals("")){
//            	if(i>=1)
//            		query+=" and ";
//            	
//            	query+="date = '"+dateend+"' ";
//            }
            query+=";";
            System.out.println(query);
            
        	ResultSet result=st.executeQuery(query);
            int flag=0;
            int cid=0;
            while(result.next()){
                System.out.println(result.getString("date"));
            	if(result.getString("name").toLowerCase().contains(name)){
                    flag=1;
                    System.out.println("got it - "+result.getString("cid")+", "+result.getString("age"));
                    cid=Integer.parseInt(result.getString("cid"));
                    x.add(cid);
                }

                //System.out.println(result.getString("username")+", "+result.getString("password"));
            }
            if(flag==0){
                System.out.println("name doesn't exist");
                return x;
            }
            return x;
        } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
        return null;
    }

    void addCriminal(Criminal c){

            try {
//                    int cid=10001;
//                    ResultSet result=st.executeQuery("select * from criminal");
//                    while(result.next()){
//                            //find the last id
//                            cid=Integer.parseInt(result.getString("cID"));
//                            //System.out.println(result.getString("username")+", "+result.getString("password"));
//                    }
//                    cid++;
                    String query="INSERT INTO `deepak`.`criminal` (`name`, `age`, `homeAddress`,  `status`) VALUES ('"
                                    +c.name+"', '"+c.age+"', '"+c.home+"', '"+c.status+"');";
                    st.executeUpdate(query);

//                    result=st.executeQuery("select * from crime");
//                    while(result.next()){
//                            //find the last id
//                            c.crimeid=Integer.parseInt(result.getString("crimeID"));
//                            //System.out.println(result.getString("username")+", "+result.getString("password"));
//                    }
//                    c.crimeid++;
//
                    String query3="select * from criminal where name='"+c.name+"' and age="+c.age+";";
                    ResultSet t = st.executeQuery(query3);
                    t.next();
                    c.cid=Integer.parseInt(t.getString("cID"));
//                    
                    String query2="INSERT INTO `deepak`.`crime` (`type`, `date`, `cityOfCrime`) VALUES ('"+c.type+"', '"+c.dateOfCrime+"', '"+c.placeofcrime+"');";
                    st.executeUpdate(query2);
//
                    query3="INSERT INTO `deepak`.`description` (`cID`, `eyeColor`, `hairColor`, `height`, `scars`, `DNA`, `weight`) VALUES ("+c.cid+", '"+c.eye+"', '"+c.hair+"', '"+c.height+"', '"+c.special+"', '"+c.dna+"', "+c.weight+");";
                    st.executeUpdate(query3);
                    
//                    
                    query3="select * from crime where type='"+c.type+"' and date='"+c.dateOfCrime+"';";
                    t = st.executeQuery(query3);
                    t.next();
                    c.crimeid=Integer.parseInt(t.getString("crimeID"));
                    query3="INSERT INTO `deepak`.`crime_done_by` (`cID`, `crimeid`) VALUES ("+c.cid+", '"+c.crimeid+"');";
                    st.executeUpdate(query3);
//                    
                    
//                    query3="INSERT INTO `deepak`.`description` (`cID`, `eyeColor`, `hairColor`, `height`, `scars`, `DNA`, 'weight') VALUES ("+c.cid+", '"+c.eye+"', '"+c.hair+"', '"+c.height+"', '"+c.special+"', '"+c.dna+"', '"+c.weight+"');";
//                    st.executeUpdate(query3);
                    System.out.println("done");
            } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }


    }
    
    public static void main(String args[]){
        System.out.println("");
        JdbcDemo j = new JdbcDemo();
        j.getConnection("deepak", "deepak");
        Criminal a = j.returnEverything(10034);
        System.out.println(a.name+" "+a.age+" "+a.placeofcrime+" "+a.dateOfCrime);
//        Criminal c = new Criminal();
//        c.age=12;
//        c.name="asdasd";
//        c.status="not decided";
//        j.addCriminal(c);
////ArrayList cids=j.searchByName("puru", 19, "africa", "1999-1-1", "2020-1-1");
//        
//        j.unsolvedCrimes();
    }
}
