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

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Form implements HandlesEventDispatching {

    private Label title, UserL, PasswL;
    private TextBox username;
    private PasswordTextBox password;
    private CheckBox buyer, seller;
    private Button login;
    private VerticalArrangement Varr1;
    private HorizontalArrangement Harr_EmptySpaceAfterTitle, Harr_UserInfo, Harr_Password, Harr_Checkboxes, H1_EmptySpaceBefore, H2_EmptySpaceAfter, Harr_EmptySpaceBeforeLoginBtn;
    private String passwordForURL = "", weblogin = "https://fachtnaroe.net/bananas?cmd=LOGIN&user=", webLogin2 = "&pass=", ResonseContent;
    private Web webLoginConnection;
    private Notifier GotTextNotifier;
    boolean buyerBool = false, salesBool = false;
    private static String usernameForURL = "", SessionId = "", pID = "";

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
            jsonLoginCheckAndValuePasser(params[1].toString(),params[3].toString());
            return true;
        }
        return false;
    }

    public void loginBtnClick() {
        if ((salesBool && buyerBool) || (!(salesBool || buyerBool))) {
            GotTextNotifier.ShowAlert("Check ONE box");
        } else {
            usernameForURL = username.Text();
            passwordForURL = password.Text();
            webLoginConnection.Url(weblogin + usernameForURL + webLogin2 + passwordForURL);
            webLoginConnection.Get();
        }
    }
    //this procedure can be called for both listViews, (Slightly Altered code I got from Fachtna that is more efficient than the previous code and uses the kawa-1.7 library)
    public void jsonLoginCheckAndValuePasser(String status, String textOfResponse) {
        if (status.equals("200")) try {
            // See:  https://stackoverflow.com/questions/5015844/parsing-json-object-in-java
            JSONObject parser = new JSONObject(textOfResponse);
            String status_json = parser.getString("Status");
            if(status_json.contains("OK")){
                pID = parser.getString("pID");
                SessionId = parser.getString("sessionID");
                if (salesBool && !buyerBool) {
                    Intent i = new Intent(getApplicationContext(), Sales.class);
                    startActivity(i);
                } else if (!salesBool && buyerBool) {
                    Intent i = new Intent(getApplicationContext(), Order.class);
                    startActivity(i);
                }
            }
        } catch (JSONException e) {
            // if an exception occurs, code for it in here
            GotTextNotifier.ShowMessageDialog("Error 3.353; JSON Exception (check password) ", "Information", "OK");
        }
        else {
            GotTextNotifier.ShowMessageDialog("Error 3.356; Problem connecting with server", "Information", "OK");
        }
    }

    public static String getUsername() {
        return usernameForURL;
    }

    public static String getSessionID() {
        return SessionId;
    }

    public static String getPID() {
        return pID;
    }
}
