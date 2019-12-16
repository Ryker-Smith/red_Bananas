package net.fachtnaroe.red_bananas;

import android.content.Intent;

import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.CheckBox;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.Notifier;
import com.google.appinventor.components.runtime.PasswordTextBox;
import com.google.appinventor.components.runtime.TextBox;
import com.google.appinventor.components.runtime.VerticalArrangement;
import com.google.appinventor.components.runtime.Web;

public class MainActivity extends Form implements HandlesEventDispatching {

    private Label title, UserL, PasswL;
    private TextBox username;
    private PasswordTextBox password;
    private CheckBox buyer, seller;
    private Button login;
    private VerticalArrangement Varr1;
    private HorizontalArrangement Harr_EmptySpaceAfterTitle, Harr_UserInfo, Harr_Password, Harr_Checkboxes, H1_EmptySpaceBefore, H2_EmptySpaceAfter, Harr_EmptySpaceBeforeLoginBtn;
    private String Spass = "", weblogin = "https://fachtnaroe.net/bananas?cmd=LOGIN&user=", webLogin2 = "&pass=", ResonseContent;
    private Web webLoginConnection;
    private Notifier GotTextNotifier;
    boolean buyerBool = false, salesBool = false;
    private static String Suser = "", SessionId = "", pID = "";

    protected void $define() {
        webLoginConnection = new Web(this);

        GotTextNotifier = new Notifier(this);
        GotTextNotifier.BackgroundColor(Component.COLOR_BLACK);
        GotTextNotifier.TextColor(Component.COLOR_PINK);

        Varr1 = new VerticalArrangement(this);
        Varr1.Height(LENGTH_FILL_PARENT);
        Varr1.Width(LENGTH_FILL_PARENT);
        Varr1.BackgroundColor(COLOR_PINK);

        title = new Label(Varr1);
        title.Width(LENGTH_FILL_PARENT);
        title.HeightPercent(20);
        title.Text("Food Delivery Service");
        title.TextColor(COLOR_BLACK);
        title.FontBold(true);
        title.FontSize(30);
        title.TextAlignment(Component.ALIGNMENT_CENTER);

        Harr_EmptySpaceAfterTitle = new HorizontalArrangement(Varr1);
        Harr_EmptySpaceAfterTitle.Height(LENGTH_FILL_PARENT);

        Harr_UserInfo = new HorizontalArrangement(Varr1);
        Harr_UserInfo.Width(LENGTH_FILL_PARENT);
        Harr_UserInfo.HeightPercent(13);

        UserL = new Label(Harr_UserInfo);
        UserL.Text("Username");
        UserL.WidthPercent(30);

        username = new TextBox(Harr_UserInfo);
        username.Width(LENGTH_FILL_PARENT);
        username.Text("redshop");

        Harr_Password = new HorizontalArrangement(Varr1);
        Harr_Password.Width(LENGTH_FILL_PARENT);
        Harr_Password.HeightPercent(15);

        PasswL = new Label(Harr_Password);
        PasswL.WidthPercent(30);
        PasswL.Text("Password");
        PasswL.TextColor(COLOR_BLACK);

        password = new PasswordTextBox(Harr_Password);
        password.Width(LENGTH_FILL_PARENT);
        password.Text("tcfetcfe");

        Harr_Checkboxes = new HorizontalArrangement(Varr1);
        Harr_Checkboxes.Width(LENGTH_FILL_PARENT);
        Harr_Checkboxes.HeightPercent(20);

         H1_EmptySpaceBefore = new HorizontalArrangement(Harr_Checkboxes);
        H1_EmptySpaceBefore.WidthPercent(20);

        buyer = new CheckBox(Harr_Checkboxes);
        buyer.Text("Buyer");
        buyer.TextColor(COLOR_BLACK);
        buyer.FontSize(25);
        buyer.Width(LENGTH_FILL_PARENT);

        seller = new CheckBox(Harr_Checkboxes);
        seller.Text("Seller");
        seller.TextColor(COLOR_BLACK);
        seller.FontSize(25);

        H2_EmptySpaceAfter = new HorizontalArrangement(Harr_Checkboxes);
        H2_EmptySpaceAfter.WidthPercent(20);

        Harr_EmptySpaceBeforeLoginBtn = new HorizontalArrangement(Varr1);
        Harr_EmptySpaceBeforeLoginBtn.Height(LENGTH_FILL_PARENT);

        login = new Button(Varr1);
        login.Width(LENGTH_FILL_PARENT);
        login.HeightPercent(20);
        login.Text("Login");
        login.FontSize(25);
        login.TextAlignment(Component.ALIGNMENT_CENTER);
        login.TextColor(Component.COLOR_PINK);
        login.BackgroundColor(COLOR_BLACK);

        EventDispatcher.registerEventForDelegation(this, formName, "Click");
        EventDispatcher.registerEventForDelegation(this, formName, "GotText");
        EventDispatcher.registerEventForDelegation(this, "ChangedEvent", "Changed");
    }

    public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params) {
        if (eventName.equals("Click")) {
            if (component.equals(login)) {
                loginBtnClick();
                return true;
            }
        }

        if (component.equals(buyer) && eventName.equals("Changed")) {
            buyerBool = !buyerBool;
            return true;
        }
        if (component.equals(seller) && eventName.equals("Changed")) {
            salesBool = !salesBool;
            return true;
        }
        if (eventName.equals("GotText")) {
            sortJsonDeet(params);
            loginResult(params);
            return true;
        }
        return false;
    }

    public void loginBtnClick() {
        if ((salesBool && buyerBool) || (!(salesBool || buyerBool))) {
            GotTextNotifier.ShowAlert("Check ONE box");
        } else {
            Suser = username.Text();
            Spass = password.Text();
            webLoginConnection.Url(weblogin + Suser + webLogin2 + Spass);
            webLoginConnection.Get();
        }
    }

    public void loginResult(Object[] params) {
        ResonseContent = (String) params[3];
        if (ResonseContent.contains("OK")) {
            GotTextNotifier.ShowAlert("OK");
            this.BackgroundColor(COLOR_GREEN);
            NextPlaceGo();
        } else {
            GotTextNotifier.ShowAlert("Nuh uh");
            this.BackgroundColor(COLOR_RED);
        }
    }

    public void NextPlaceGo() {
        if (salesBool && !buyerBool) {
            Intent i = new Intent(getApplicationContext(), Sales.class);
            startActivity(i);
        } else if (!salesBool && buyerBool) {
            Intent i = new Intent(getApplicationContext(), Order.class);
            startActivity(i);
        }
    }

    public void sortJsonDeet(Object[] params) {
        String jsonString = (String) params[3];
        int sessionIDFirstChar = (jsonString.indexOf("sessionID")) + 12;
        int pID_FirstChar = (jsonString.indexOf("pID")) + 8;
        SessionId = jsonString.substring(sessionIDFirstChar, sessionIDFirstChar + 8);
        pID = jsonString.substring(pID_FirstChar, pID_FirstChar + 2);
    }

    public static String getUsername() {
        return Suser;
    }

    public static String getSessionID() {
        return SessionId;
    }

    public static String getPID() {
        return pID;
    }
}
