package net.fachtnaroe.red_bananas;

import android.graphics.Color;
import android.util.Log;

import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.ListView;
import com.google.appinventor.components.runtime.Notifier;
import com.google.appinventor.components.runtime.VerticalArrangement;
import com.google.appinventor.components.runtime.Web;
import com.google.appinventor.components.runtime.util.YailList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Order extends Form implements HandlesEventDispatching {
    private Button BTN_BuyItem;
    private VerticalArrangement VArr;
    private Label LBL_Title, LBL_UserN, LBL_UserTXT, LBL_pID, LBL_pIDTXT, LBL_AvToOrdr, LBL_Credit, LBL_CreditTXT, LBL_Ordered;
    private HorizontalArrangement HArr_UserInfo, HArr_Credit_BuyBtn, Harr_Credit, Harr_BuyBtn;
    private ListView ThingsAvailableToBuy_ListView, ThingsOrdered_ListView;
    private String baseURL = "https://fachtnaroe.net/bananas?",
            SessionID = MainActivity.getSessionID(),
            pID = MainActivity.getPID(),
            username = MainActivity.getUsername(),
            getCreditURL = baseURL + "sessionID=" + SessionID + "&entity=person&method=GET&pID=" + pID;
    private Web Web_TfS, Web_TB, Web_Credit, Web_Credit2, Web_PlaceOrder;
    private Notifier messages;

    protected void $define() {
        VArr = new VerticalArrangement(this);
        VArr.Height(LENGTH_FILL_PARENT);
        VArr.Width(LENGTH_FILL_PARENT);
        VArr.BackgroundColor(Component.COLOR_BLUE);

        LBL_Title = new Label(VArr);
        LBL_Title.Width(LENGTH_FILL_PARENT);
        LBL_Title.FontSize(20);
        LBL_Title.FontBold(true);
        LBL_Title.Text("Food Delivery Service");
        LBL_Title.TextColor(Component.COLOR_WHITE);
        LBL_Title.TextAlignment(Component.ALIGNMENT_CENTER);

        HArr_UserInfo = new HorizontalArrangement(VArr);
        HArr_UserInfo.Width(LENGTH_FILL_PARENT);
        HArr_UserInfo.HeightPercent(5);
        HArr_UserInfo.BackgroundColor(Component.COLOR_GRAY);

        LBL_UserN = new Label(HArr_UserInfo);
        LBL_UserN.FontSize(15);
        LBL_UserN.Text("Username:");
        LBL_UserN.TextColor(Component.COLOR_WHITE);

        LBL_UserTXT = new Label(HArr_UserInfo);
        LBL_UserTXT.Width(LENGTH_FILL_PARENT);
        LBL_UserTXT.FontSize(15);
        LBL_UserTXT.Text(username);
        LBL_UserTXT.TextColor(COLOR_RED);

        LBL_pID = new Label(HArr_UserInfo);
        LBL_pID.FontSize(15);
        LBL_pID.Text("pID:");
        LBL_pID.TextColor(Component.COLOR_WHITE);

        LBL_pIDTXT = new Label(HArr_UserInfo);
        LBL_pIDTXT.FontSize(15);
        LBL_pIDTXT.Text(pID);
        LBL_pIDTXT.TextColor(COLOR_RED);

        LBL_AvToOrdr = new Label(VArr);
        LBL_AvToOrdr.Width(LENGTH_FILL_PARENT);
        LBL_AvToOrdr.FontSize(14);
        LBL_AvToOrdr.Text("Things Available For Purchase:");
        LBL_AvToOrdr.TextColor(Component.COLOR_WHITE);

        ThingsAvailableToBuy_ListView = new ListView(VArr);
        ThingsAvailableToBuy_ListView.Width(LENGTH_FILL_PARENT);
        ThingsAvailableToBuy_ListView.Height(LENGTH_FILL_PARENT);
        ThingsAvailableToBuy_ListView.TextSize(20);
        ThingsAvailableToBuy_ListView.TextColor(Component.COLOR_WHITE);

        HArr_Credit_BuyBtn = new HorizontalArrangement(VArr);
        HArr_Credit_BuyBtn.Width(LENGTH_FILL_PARENT);
        HArr_Credit_BuyBtn.HeightPercent(7);
        HArr_Credit_BuyBtn.BackgroundColor(Component.COLOR_GRAY);

        Harr_Credit = new HorizontalArrangement(HArr_Credit_BuyBtn);
        Harr_Credit.Width(LENGTH_FILL_PARENT);
        Harr_Credit.HeightPercent(7);
        Harr_Credit.BackgroundColor(Component.COLOR_GRAY);

        LBL_Credit = new Label(Harr_Credit);
        LBL_Credit.FontSize(20);
        LBL_Credit.Width(LENGTH_FILL_PARENT);
        LBL_Credit.TextColor(Component.COLOR_WHITE);
        LBL_Credit.Text("Credit  ");

        LBL_CreditTXT = new Label(Harr_Credit);
        LBL_CreditTXT.FontSize(20);
        LBL_CreditTXT.TextColor(Component.COLOR_BLACK);
        LBL_CreditTXT.Width(LENGTH_FILL_PARENT);

        Harr_BuyBtn = new HorizontalArrangement(HArr_Credit_BuyBtn);
        Harr_BuyBtn.Width(LENGTH_FILL_PARENT);
        Harr_BuyBtn.HeightPercent(7);
        Harr_BuyBtn.BackgroundColor(Component.COLOR_GRAY);

        BTN_BuyItem = new Button(Harr_BuyBtn);
        BTN_BuyItem.Text("Buy");
        BTN_BuyItem.Width(LENGTH_FILL_PARENT);
        BTN_BuyItem.TextColor(COLOR_BLACK);

        LBL_Ordered = new Label(VArr);
        LBL_Ordered.Width(LENGTH_FILL_PARENT);
        LBL_Ordered.FontSize(12);
        LBL_Ordered.Text("Things I've Ordered:");
        LBL_Ordered.TextColor(Component.COLOR_WHITE);

        ThingsOrdered_ListView = new ListView(VArr);
        ThingsOrdered_ListView.Width(LENGTH_FILL_PARENT);
        ThingsOrdered_ListView.HeightPercent(30);
        ThingsOrdered_ListView.TextColor(Component.COLOR_WHITE);
        ThingsOrdered_ListView.TextSize(20);

        messages = new Notifier(this);
        messages.BackgroundColor(Component.COLOR_RED);
        messages.TextColor(Component.COLOR_WHITE);

        Web_TfS = new Web(this);
        Web_TfS.Url(baseURL + "sessionID=" + SessionID + "&entity=thing&method=GET");
        Web_TfS.Get();

        Web_TB = new Web(this);
        Web_TB.Url(baseURL + "sessionID=" + SessionID + "&entity=prettyorders&method=GET");
        Web_TB.Get();

        Web_Credit = new Web(this);
        Web_Credit.Url(getCreditURL);
        Web_Credit.Get();

        Web_Credit2 = new Web(this);

        Web_PlaceOrder = new Web(this);

        EventDispatcher.registerEventForDelegation(this, formName, "Click");
        EventDispatcher.registerEventForDelegation(this, formName, "Initialize");
        EventDispatcher.registerEventForDelegation(this, "GotTextEvent", "GotText");
    }

    public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params) {
        if (eventName.equals("Click")) {
            if (component.equals(BTN_BuyItem)) {
                buyThis(ThingsAvailableToBuy_ListView.Selection());
                return true;
            } else {
                return false;
            }
        }
        if (component.equals(Web_TfS) && eventName.equals("GotText")) {
            //calling the procedure For the ListView containing the Items that are available to buy
            jsonSortAndListViewForBuyerScreen(params[1].toString(), (String) params[3],"thing", "null");
            return true;
        }
        if (component.equals(Web_TB) && eventName.equals("GotText")) {
            //calling the procedure For the ListView containing the Items that the buyer has ordered
            jsonSortAndListViewForBuyerScreen(params[1].toString(), (String) params[3],"prettyorders", "buyerID");
            return true;
        }
        if (component.equals(Web_Credit) && eventName.equals("GotText")) {
            Log.w("TIN**",(String) params[3]);
            JsonCreditThings(params[1].toString(), (String) params[3]);
            return true;
        }
        if (component.equals(Web_PlaceOrder) && eventName.equals("GotText")) {
            String response = ((String) params[3]);
            if (response.contains("OK")) {
                String oid = response.substring(22, response.length() - 2);
                messages.ShowAlert("Your Order Has Been Placed : " + oid);

            }
            return true;
        }
        return false;
    }

    public void buyThis(String x) {
        if ((ThingsAvailableToBuy_ListView.Selection().isEmpty())) {
            messages.ShowAlert("No Item Selected");
        } else {
            int i = x.indexOf("]");
            int euro = x.indexOf("€") + 1;
            String price = x.substring(euro);
            String oldCredit = LBL_CreditTXT.Text().replace("€", "");
            String y = x.substring(1, i);
            String[] url_IDs = y.split(":");
            Web_PlaceOrder.Url(baseURL + "sessionID=" + SessionID + "&entity=orders&method=POST&tID=" + url_IDs[0] + "&sellerID=" + url_IDs[1] + "&slotNum=1&buyerID=" + pID);
            Web_PlaceOrder.Get();
            creditUpdateAfterBuy(Double.parseDouble(oldCredit), Double.parseDouble(price));
        }
    }
    public void creditUpdateAfterBuy(Double x, Double y) {
        Double i = x - y;
        String p = Double.toString(i);
        LBL_CreditTXT.Text("€" + p);
        Web_Credit2.Url(getCreditURL+ "&Credit=" + p);
        Web_Credit2.Get();
    }
    public void JsonCreditThings(String status, String textOfResponse) {
//        int start = Y.lastIndexOf("Credit") + 9;
//        int finish = Y.lastIndexOf("Email") - 3;
//        String Rep1 = Y.substring(start, finish);

        Log.w("personTING**","1");
        if (status.equals("200")) try {
            Log.w("personTING**","2");
            JSONObject parser = new JSONObject(textOfResponse);
            String bean = parser.getString("person");
            Log.w("personTING**",parser.getString("person"));
            //JSONObject credit =

            //LBL_CreditTXT.Text("€" + parser.getString("Credit"));
    } catch (JSONException e) {
        // if an exception occurs, code for it in here
        messages.ShowMessageDialog("Error 3.353; JSON Exception (check password) ", "Information", "OK");
    }
        else {
        messages.ShowMessageDialog("Error 3.356; Problem connecting with server", "Information", "OK");
    }

    }
    //this procedure can be called for both listViews, (Slightly Altered code I got from Fachtna that is more efficient than the previous code and uses the kawa-1.7 library)
    public void jsonSortAndListViewForBuyerScreen(String status, String textOfResponse, String tableName, String fieldName) {
        List<String> ListViewItemArray;
        if (status.equals("200")) try {
            ListViewItemArray = new ArrayList<String>();
            // See:  https://stackoverflow.com/questions/5015844/parsing-json-object-in-java
            JSONObject parser = new JSONObject(textOfResponse);
            if (!parser.getString(tableName).equals("")) {
                JSONArray jsonIsMySon = parser.getJSONArray(tableName);
                for (int i = 0; i < jsonIsMySon.length(); i++) {
                    String oneEntryInTheListView = "";
                    //add data from table to the sting above by getting the field name you want from the brief ( example where field name is "sellerID": oneEntryInTheListView = jsonIsMySon.getJSONObject(i).getString("sellerID"); )
                    //formats entries the ListView containing the items in thing table
                    if (tableName.equals("thing") && fieldName.equals("null")){
                        oneEntryInTheListView = "[" + jsonIsMySon.getJSONObject(i).getString("tID")
                                + " : " + jsonIsMySon.getJSONObject(i).getString("tSoldBy")
                                + "] " + jsonIsMySon.getJSONObject(i).getString("tName")
                                + " (" + jsonIsMySon.getJSONObject(i).getString("tDescription")
                                + ") €" + jsonIsMySon.getJSONObject(i).getString("tPrice");
                        ListViewItemArray.add(oneEntryInTheListView);
                    }
                    //formats entries the ListView containing the orders buyer has placed
                    else if ((tableName.equals("prettyorders") && fieldName.equals("buyerID")) && (Integer.valueOf(jsonIsMySon.getJSONObject(i).getString(fieldName)).equals( Integer.valueOf(pID)))) {
                        oneEntryInTheListView = "[" + jsonIsMySon.getJSONObject(i).getString("oID")
                                + "] " + jsonIsMySon.getJSONObject(i).getString("tName")
                                + " from " + jsonIsMySon.getJSONObject(i).getString("seller")
                                + " [ tID: " + jsonIsMySon.getJSONObject(i).getString("tID") + " ]";
                        ListViewItemArray.add(oneEntryInTheListView);
                    }
                }
                YailList tempData = YailList.makeList(ListViewItemArray);
                if (tableName.equals("prettyorders") && fieldName.equals("buyerID")) {
                    ThingsOrdered_ListView.Elements(tempData);
                }
                if (tableName.equals("thing") && fieldName.equals("null")) {
                    ThingsAvailableToBuy_ListView.Elements(tempData);
                }
            }
        } catch (JSONException e) {
            // if an exception occurs, code for it in here
            messages.ShowMessageDialog("Error 3.353; JSON Exception (check password) ", "Information", "OK");
        }
        else {
            messages.ShowMessageDialog("Error 3.356; Problem connecting with server", "Information", "OK");
        }
    }
}