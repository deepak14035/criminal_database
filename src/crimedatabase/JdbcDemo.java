package crimedatabase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class JdbcDemo {
        static Connection myconn;	
        static Statement st;
            public JdbcDemo(){
                try {
                    myconn = DriverManager.getConnection("jdbc:mysql://localhost:3306/deepak", "root", "deepak123");
                    st=myconn.createStatement();
                    String user,pass;
                    Scanner s = new Scanner(System.in);
                    System.out.println("enter username");
                    user=s.nextLine();

                    System.out.println("enter password");
                    pass=s.nextLine();
                    ResultSet result=st.executeQuery("select * from Login");
                    int flag=0, level;
                    while(result.next()){
                            if(user.equals(result.getString("username")) && pass.equals(result.getString("password"))){
                                    System.out.println("succesfully logged in!");
                                    flag=1;
                                    level=Integer.parseInt(result.getString("level"));
                                    break;
                            }

                            //System.out.println(result.getString("username")+", "+result.getString("password"));
                    }
                    if(flag==0){
                            System.out.println("incorrect username/password");
                    }
                    
                    s.close();
                } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }

                }

                void searchByName(String name){
                        try{
                                ResultSet result=st.executeQuery("select * from criminal");
                                int flag=0, level;
                                while(result.next()){
                                        if(name.contains(result.getString("name"))){
                                                flag=1;
                                                System.out.println(result.getString("cid")+result.getString("age"));
                                                level=Integer.parseInt(result.getString("level"));
                                                break;
                                        }

                                        //System.out.println(result.getString("username")+", "+result.getString("password"));
                                }
                                if(flag==0){
                                        System.out.println("incorrect username/password");
                                }
                                
                        } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                }

                void addCriminal(Criminal c){
                        Scanner s = new Scanner(System.in);
//                        String name,home,status,sentence;
//                        int age;
//                        System.out.println("name-");
//                        name=s.nextLine();
//                        System.out.println("age-");
//                        age=s.nextInt();
//                        System.out.println("address-");
//                        home=s.nextLine();
//                        System.out.println("sentence (DD/MM/YY)-");
//                        sentence=s.nextLine();
//                        System.out.println("status-");
//                        status=s.nextLine();
//                        System.out.println("home-");
//                        home=s.nextLine();
                        try {
                                int cid=10001;
                                ResultSet result=st.executeQuery("select * from criminal");
                                while(result.next()){
                                        //find the last id
                                        cid=Integer.parseInt(result.getString("cID"));
                                        //System.out.println(result.getString("username")+", "+result.getString("password"));
                                }
                                cid++;
                                String query="INSERT INTO `deepak`.`criminal` (`name`, `cID`, `age`, `homeAddress`, `sentence`, `status`) VALUES ('"
                                                +c.name+"', '"
                                                + c.cid+"', '"+c.age+"', '"+c.home+"', '"+c.sentence+"', '"+c.status+"');";
                                st.executeUpdate(query);

//                                String eye,hair,special,dna;
//                                int height;
//                                System.out.println("eye-");
//                                eye=s.nextLine();
//                                System.out.println("hair");
//                                hair=s.nextLine();
//                                System.out.println("special marks-");
//                                special=s.nextLine();
//                                System.out.println("dna-");
//                                dna=s.nextLine();
//                                System.out.println("height-");
//                                height=s.nextInt();
//                                int crimeid=10001;
                                result=st.executeQuery("select * from crime");
                                while(result.next()){
                                        //find the last id
                                        c.crimeid=Integer.parseInt(result.getString("crimeID"));
                                        //System.out.println(result.getString("username")+", "+result.getString("password"));
                                }
                                c.crimeid++;
//                                String type,date,placeofcrime;
//                                System.out.println("type of crime-");
//                                type=s.nextLine();
//                                System.out.println("date of crime-");
//                                date=s.nextLine();
//                                System.out.println("place of crime-");
//                                placeofcrime=s.nextLine();


                                String query2="INSERT INTO `deepak`.`crime` (`crimeID`, `type`, `date`, `placeOfCrime`) VALUES ("+c.crimeid+", '"+c.type+"', '"+c.date+"', '"+c.placeofcrime+"');";
                                st.executeUpdate(query2);

                                String query3="INSERT INTO `deepak`.`description` (`cID`, `eyeColor`, `hairColor`, `height`, `scars`, `DNA`) VALUES ("+c.cid+", '"+c.eye+"', '"+c.hair+"', '"+c.height+"', '"+c.special+"', '"+c.dna+"');";
                                st.executeUpdate(query3);

                        } catch (SQLException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }


                }
}
