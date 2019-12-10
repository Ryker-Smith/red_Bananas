package net.fachtnaroe.red_bananas;
//Test comment

import android.content.Intent;

import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.CheckBox;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.PasswordTextBox;
import com.google.appinventor.components.runtime.TextBox;
import com.google.appinventor.components.runtime.VerticalScrollArrangement;
import com.google.appinventor.components.runtime.Web;
import com.google.appinventor.components.runtime.Notifier;

public class MainActivity extends Form implements HandlesEventDispatching {

    private Label title, UserL,PasswL;
    private TextBox username;
    private PasswordTextBox password;
    private CheckBox buyer,seller;
    private Button login;
    private VerticalScrollArrangement Varr1;
    private HorizontalArrangement Harr1,Harr2,Harr3,Harr4,Harr5;
    private String Spass="",weblogin="https://fachtnaroe.net/bananas?cmd=LOGIN&user=",webLogin2="&pass=",ResonseContent;
    private Web webLoginConnection;
    private Notifier GotTextNotifier;
    boolean buyerBool=false,salesBool=false;
    private static String Suser="",SessionId="",pID="";

    protected void $define(){
        webLoginConnection = new Web(this);
        this.BackgroundColor(COLOR_PINK);

        GotTextNotifier = new Notifier(this);
        GotTextNotifier.BackgroundColor(Component.COLOR_BLACK);
        GotTextNotifier.TextColor(Component.COLOR_PINK);

        Varr1 = new VerticalScrollArrangement(this);
        Varr1.WidthPercent(100);
        Varr1.HeightPercent(100);

        title = new Label(Varr1);
        title.WidthPercent(100);
        title.HeightPercent(20);
        title.Text("Food Delivery Service");
        title.TextColor(COLOR_BLACK);
        title.FontBold(true);
        title.FontSize(50);
        title.TextAlignment(Component.ALIGNMENT_CENTER);

        Harr1 = new HorizontalArrangement(Varr1);
        Harr1.WidthPercent(100);
        Harr1.HeightPercent(15);

        Harr2 = new HorizontalArrangement(Varr1);
        Harr2.WidthPercent(100);
        Harr2.HeightPercent(13);

        Harr3 = new HorizontalArrangement(Varr1);
        Harr3.WidthPercent(100);
        Harr3.HeightPercent(15);


        Harr4 = new HorizontalArrangement(Varr1);
        Harr4.WidthPercent(100);
        Harr4.HeightPercent(20);
        Harr4.AlignVertical(Component.ALIGNMENT_CENTER);

        Harr5 = new HorizontalArrangement(Varr1);
        Harr5.WidthPercent(100);
        Harr5.HeightPercent(20);
        Harr5.AlignVertical(Component.ALIGNMENT_CENTER);

        UserL = new Label(Harr2);
        UserL.Text("Username");
        UserL.WidthPercent(LENGTH_FILL_PARENT);

        username = new TextBox(Harr2);
        username.WidthPercent(70);

        PasswL = new Label(Harr3);
        PasswL.WidthPercent(LENGTH_FILL_PARENT);
        PasswL.Text("Password");
        PasswL.TextColor(COLOR_BLACK);

        password = new PasswordTextBox(Harr3);
        password.WidthPercent(70);

        buyer = new CheckBox(Harr4);
        buyer.Text("Buyer");
        buyer.TextColor(COLOR_BLACK);
        buyer.FontSize(25);
        buyer.WidthPercent(50);

        seller = new CheckBox(Harr4);
        seller.Text("Seller");
        seller.TextColor(COLOR_BLACK);
        seller.FontSize(25);
        seller.WidthPercent(50);

        login = new Button(Harr5);
        login.WidthPercent(100);
        login.HeightPercent(20);
        login.Text("Login");
        login.FontSize(25);
        login.TextAlignment(Component.ALIGNMENT_CENTER);
        login.TextColor(COLOR_PINK);
        login.BackgroundColor(COLOR_BLACK);

        EventDispatcher.registerEventForDelegation( this, formName, "Click" );
        EventDispatcher.registerEventForDelegation( this, formName, "GotText" );
        EventDispatcher.registerEventForDelegation( this, "ChangedEvent", "Changed" );
    }
    public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params)
    {
        if (eventName.equals("Click")){
            if (component.equals(login))
            {
                loginBtnClick();
                return true;
            }
        }

        if( component.equals(buyer) && eventName.equals("Changed") ){
            if(buyerBool){
                buyerBool=false;
            }
            else {
                buyerBool=true;
            }
            return true;
        }
        if( component.equals(seller) && eventName.equals("Changed") ){
            if(salesBool){
                salesBool=false;
            }
            else {
                salesBool=true;
            }
            return true;
        }
        if(eventName.equals("GotText")){
            sortJsonDeet(params);
            loginResult(params);
            return true;
        }
        return false;
    }
    public void loginBtnClick() {
      if((salesBool&&buyerBool)||(!(salesBool||buyerBool))){
            GotTextNotifier.ShowAlert("Check ONE box");
        }
        else {
            Suser = username.Text();
            Spass = password.Text();
            webLoginConnection.Url(weblogin + Suser + webLogin2 + Spass);
            webLoginConnection.Get();
        }
    }
    public  void loginResult(Object[] params){
        ResonseContent=(String) params[3];
        if(ResonseContent.contains("OK")){
            GotTextNotifier.ShowAlert("OK");
            this.BackgroundColor(COLOR_GREEN);
            NextPlaceGo();
        }
        else{
            GotTextNotifier.ShowAlert("Nuh uh");
            this.BackgroundColor(COLOR_RED);
        }
    }
    public void NextPlaceGo() {
        if(salesBool&&!buyerBool){
            Intent i = new Intent(getApplicationContext(), Sales.class);
            startActivity(i);
        }
        else if(!salesBool&&buyerBool){
            Intent i = new Intent(getApplicationContext(), Order.class);
            startActivity(i);
        }
    }
    public void sortJsonDeet(Object[] params){
        String jsonString = (String)params[3];;
        int sessionIDFirstChar =(jsonString.indexOf("sessionID"))+12;
        int pID_FirstChar = (jsonString.indexOf("pID"))+8;
        SessionId=jsonString.substring(sessionIDFirstChar,sessionIDFirstChar+8);
        pID=jsonString.substring(pID_FirstChar,pID_FirstChar+2);
    }
    public static String getUsername(){
        return Suser;
    }
    public static String getSessionID(){
        return SessionId;
    }
    public static String getPID(){
        return pID;
    }
}
