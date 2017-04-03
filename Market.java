/*
    Locker Utility
    Single Person Usage
*/

import java.io.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import java.awt.color.*;
/*
 The Query Box
*/
class QueryBox extends Frame
{
    Label l1;
    static TextArea ta;
    Button b;
    QueryBox()
    {
        setSize(500,300);
        setVisible(true);
        setLayout(new FlowLayout());
        b=new Button("Submit");
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){                
                try(FileWriter fw = new FileWriter("RequestLogs.dat", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw))
                    {
                        out.println("*********");
			out.println(ta.getText());
			out.println("*********");
                    } catch (IOException k) {
                        System.out.println(k);
                    }
                      System.exit(0);          }
        });
        l1=new Label("Place Your Query here, along with your contact information");
        ta=new TextArea();
        add(l1);
        add(ta);
        add(b);
    }
}

/* The starting UI Class
   Provides the options for the user to choose
*/

class Start extends Frame
{
    Button b1,b2,b3,b4;
    static String Sequence;
    static boolean Authentic;
    Start()
    {
        Authentic=false;
        b1=new Button("Set new Lock Sequence");
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                setSequence();
            }
        });
        b1.setBackground(Color.ORANGE);
        b2=new Button("Unlock Application");
        b2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                unlockApplication();
            }
        });
        b2.setBackground(Color.GREEN);
        b3=new Button("Quit");
        b3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        b3.setBackground(Color.red);
        b4=new Button("Contact Admin");
        b4.setBackground(Color.WHITE);
        b4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                QueryBox QB=new QueryBox();
            }
        });
        add(b1);
        add(b2);
        add(b4);
        add(b3);
        setSize(500,100);
        setVisible(true);
        setLayout(new FlowLayout());
        setBackground(new Color(10,10,100));
    }
    void setSequence(){
        Lock l=new Lock();
    }
    void unlockApplication(){
        Unlock u=new Unlock();
    }
}

/*
    The interface for locking is provided here.
    Uses TextBased data files to store login credentials.
    Overwrites any existing credentials.
*/
class Lock extends Frame implements ActionListener
{
    Button[] b;
    Button quit,submit;
    String temp;
    Panel p1,p2;
    boolean first_press;
    Lock()
    {
        first_press=true;
        quit=new Button("Quit");
        quit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        submit=new Button("Submit");
        submit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("Your pattern is has been saved");
                try {
			File file = new File("Crypt.dat");
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(temp);
			fileWriter.flush();
			fileWriter.close();
		} 
                
                catch(IOException f){
                    System.out.println(f);
                }
            }
        });
        p1=new Panel(new GridLayout(3,3,0,0));
        add(p1);
        p2=new Panel(new FlowLayout());
        p2.setBackground(new Color(40,40,255));
        add(p2);
        p2.add(quit);
        p2.add(submit);
        b=new Button[9];
        for(int i=0;i<b.length;i++)
        {
            b[i]=new Button(String.valueOf(i));
            b[i].addActionListener(this);
            p1.add(b[i]);
        }
        setVisible(true);
        setSize(300,200);
        setLayout(new GridLayout(1,1,0,0));
        setBackground(new Color(40,40,255));
    }
    public void actionPerformed(ActionEvent e)
    {
        if(first_press){
            temp=e.getActionCommand();
            first_press=false;
        }
        else{
            temp+=e.getActionCommand();
        }
    }
    
}

/*
    The UI for unlock is provided here
    Reads credentials from a textBased Data file
*/

class Unlock extends Frame implements ActionListener
{
    Button[] b;
    Button quit,unlock;
    String temp;
    Panel p1,p2;
    boolean first_press;
    Unlock()
    {
        first_press=true;
        quit=new Button("Quit");
        quit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        unlock=new Button("Unlock");
        unlock.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    Start.Sequence=new Scanner(new File("crypt.dat")).useDelimiter("\\A").next();
                if(Start.Sequence.equals(temp))
                {
                    Start.Authentic=true;
                    System.out.println("You are now an Authentic User");
                }
                else
                {
                    System.out.println("Sorry! Invalid Input "+Start.Sequence);
                }
                }
                catch(FileNotFoundException FOF){
                    System.out.println(FOF);
                }
            }
        });
        p1=new Panel(new GridLayout(3,3,0,0));
        add(p1);
        p2=new Panel(new FlowLayout());
        p2.setBackground(new Color(40,40,255));
        add(p2);
        p2.add(quit);
        p2.add(unlock);
        b=new Button[9];
        for(int i=0;i<b.length;i++)
        {
            b[i]=new Button(String.valueOf(i));
            b[i].addActionListener(this);
            p1.add(b[i]);
        }
        setVisible(true);
        setSize(300,200);
        setLayout(new GridLayout(1,1,0,0));
        setBackground(new Color(40,40,255));
    }
    public void actionPerformed(ActionEvent e)
    {
        if(first_press){
            temp=e.getActionCommand();
            first_press=false;
        }
        else{
            temp+=e.getActionCommand();
        }
    }
}

class Locker
{
    public static void main(String[] args)
    {
        Start s=new Start();
    }
}