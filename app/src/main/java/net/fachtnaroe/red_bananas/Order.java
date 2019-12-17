package net.fachtnaroe.red_bananas;

import android.graphics.Color;

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
    private Label LBL_Title, LBL_UserN, LBL_UserTXT, LBL_pID, LBL_pIDTXT, LBL_AvToOrdr, LBL_Credit, CreditAmount_Label, LBL_Ordered;
    private HorizontalArrangement HArr_UserInfo, HArr_Credit_BuyBtn, Harr_Credit, Harr_BuyBtn;
    private ListView ThingsAvailableToBuy_ListView, ThingsOrdered_ListView;
    private String baseURL = "https://fachtnaroe.net/bananas?",
    getCreditURL="&entity=person&method=GET&pID=",
    sID="sessionID=";
//            SessionID = MainActivity.getSessionID(),
//            pID = MainActivity.getPID(),
//            username = MainActivity.getUsername(),
           // getCreditURL = baseURL + "sessionID=" + SessionID + "&entity=person&method=GET&pID=" + pID;
    private String[] startValue;
    private Web Web_TfS, Web_TB, Web_Credit, Web_Credit2, Web_PlaceOrder;
    private Notifier messages;

    protected void $define() {
        this.BackgroundColor(Component.COLOR_ORANGE);
        //[0]=extra quotation mark(not to be used) [1]=pId [2]=Username [3]=SessionID [4]=extra quotation mark(not to be used)
        startValue = this.startupValue.split("<SPLIT>");
        VArr = new VerticalArrangement(this);
        VArr.Height(LENGTH_FILL_PARENT);
        VArr.Width(LENGTH_FILL_PARENT);
        VArr.BackgroundColor(Component.COLOR_ORANGE);
        VArr.Image("FDS_PossibleLogo_04.png");

        LBL_Title = new Label(VArr);
        LBL_Title.Width(LENGTH_FILL_PARENT);
        LBL_Title.FontSize(20);
        LBL_Title.FontBold(true);
        LBL_Title.Text("Food Delivery Service");
        LBL_Title.TextColor(Component.COLOR_WHITE);
        LBL_Title.TextAlignment(Component.ALIGNMENT_CENTER);
        LBL_Title.BackgroundColor(Color.parseColor("#005200"));

        HArr_UserInfo = new HorizontalArrangement(VArr);
        HArr_UserInfo.Width(LENGTH_FILL_PARENT);
        HArr_UserInfo.HeightPercent(5);
        HArr_UserInfo.BackgroundColor(Component.COLOR_NONE);

        LBL_UserN = new Label(HArr_UserInfo);
        LBL_UserN.FontSize(15);
        LBL_UserN.Text("Username:");
        LBL_UserN.TextColor(Component.COLOR_BLACK);

        LBL_UserTXT = new Label(HArr_UserInfo);
        LBL_UserTXT.Width(LENGTH_FILL_PARENT);
        LBL_UserTXT.FontSize(15);
//        LBL_UserTXT.Text(username);
        LBL_UserTXT.Text(startValue[2]);
        LBL_UserTXT.TextColor(COLOR_RED);
        LBL_UserTXT.FontBold(true);

        LBL_pID = new Label(HArr_UserInfo);
        LBL_pID.FontSize(15);
        LBL_pID.Text("pID:");
        LBL_pID.TextColor(Component.COLOR_BLACK);

        LBL_pIDTXT = new Label(HArr_UserInfo);
        LBL_pIDTXT.FontSize(15);
//        LBL_pIDTXT.Text(pID);
        LBL_pIDTXT.Text(startValue[1]);
        LBL_pIDTXT.TextColor(COLOR_RED);
        LBL_pIDTXT.FontBold(true);

        LBL_AvToOrdr = new Label(VArr);
        LBL_AvToOrdr.Width(LENGTH_FILL_PARENT);
        LBL_AvToOrdr.FontSize(12);
        LBL_AvToOrdr.Text("Things Available For Purchase:");
        LBL_AvToOrdr.TextColor(Component.COLOR_WHITE);
        LBL_AvToOrdr.FontItalic(true);
        LBL_AvToOrdr.BackgroundColor(Color.parseColor("#005200"));

        ThingsAvailableToBuy_ListView = new ListView(VArr);
        ThingsAvailableToBuy_ListView.Width(LENGTH_FILL_PARENT);
        ThingsAvailableToBuy_ListView.Height(LENGTH_FILL_PARENT);
        ThingsAvailableToBuy_ListView.TextSize(50);
        ThingsAvailableToBuy_ListView.TextColor(Component.COLOR_BLACK);
        ThingsAvailableToBuy_ListView.SelectionColor(Color.parseColor("#009F00"));
        ThingsAvailableToBuy_ListView.BackgroundColor(Component.COLOR_NONE);

        HArr_Credit_BuyBtn = new HorizontalArrangement(VArr);
        HArr_Credit_BuyBtn.Width(LENGTH_FILL_PARENT);
        HArr_Credit_BuyBtn.HeightPercent(7);
        HArr_Credit_BuyBtn.BackgroundColor(Component.COLOR_NONE);

        Harr_Credit = new HorizontalArrangement(HArr_Credit_BuyBtn);
        Harr_Credit.Width(LENGTH_FILL_PARENT);
        Harr_Credit.HeightPercent(7);
        Harr_Credit.BackgroundColor(Component.COLOR_NONE);

        LBL_Credit = new Label(Harr_Credit);
        LBL_Credit.FontSize(20);
        LBL_Credit.Width(LENGTH_FILL_PARENT);
        LBL_Credit.TextColor(Component.COLOR_BLACK);
        LBL_Credit.Text("Credit ");
        LBL_Credit.BackgroundColor(Component.COLOR_NONE);

        CreditAmount_Label = new Label(Harr_Credit);
        CreditAmount_Label.FontSize(20);
        CreditAmount_Label.TextColor(Component.COLOR_BLACK);
        CreditAmount_Label.Width(LENGTH_FILL_PARENT);
        CreditAmount_Label.BackgroundColor(Component.COLOR_NONE);

        Harr_BuyBtn = new HorizontalArrangement(HArr_Credit_BuyBtn);
        Harr_BuyBtn.Width(LENGTH_FILL_PARENT);
        Harr_BuyBtn.HeightPercent(7);
        Harr_BuyBtn.BackgroundColor(Component.COLOR_NONE);

        BTN_BuyItem = new Button(Harr_BuyBtn);
        BTN_BuyItem.Text("Buy");
        BTN_BuyItem.Width(LENGTH_FILL_PARENT);
        BTN_BuyItem.TextColor(COLOR_BLACK);

        LBL_Ordered = new Label(VArr);
        LBL_Ordered.Width(LENGTH_FILL_PARENT);
        LBL_Ordered.FontSize(12);
        LBL_Ordered.Text("Things I've Ordered:");
        LBL_Ordered.TextColor(Component.COLOR_WHITE);
        LBL_Ordered.FontItalic(true);
        LBL_Ordered.BackgroundColor(Color.parseColor("#005200"));

        ThingsOrdered_ListView = new ListView(VArr);
        ThingsOrdered_ListView.Width(LENGTH_FILL_PARENT);
        ThingsOrdered_ListView.HeightPercent(30);
        ThingsOrdered_ListView.TextColor(Component.COLOR_BLACK);
        ThingsOrdered_ListView.TextSize(50);
        ThingsOrdered_ListView.SelectionColor(Color.parseColor("#009F00"));
        ThingsOrdered_ListView.BackgroundColor(Component.COLOR_NONE);

        messages = new Notifier(this);
        messages.BackgroundColor(Component.COLOR_RED);
        messages.TextColor(Component.COLOR_WHITE);

        Web_TfS = new Web(this);
        Web_TfS.Url(baseURL + sID + startValue[3] + "&entity=thing&method=GET");
        Web_TfS.Get();

        Web_TB = new Web(this);
        Web_TB.Url(baseURL + sID + startValue[3] + "&entity=prettyorders&method=GET");
        Web_TB.Get();

        Web_Credit = new Web(this);
        Web_Credit.Url(baseURL + sID + startValue[3] + getCreditURL);
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
                buyItemAndUpdateCredit(ThingsAvailableToBuy_ListView.Selection());
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
            //calling procedure to show credit amount
            jsonSortAndListViewForBuyerScreen(params[1].toString(), (String) params[3],"person", "Credit");
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

    public void buyItemAndUpdateCredit(String listSelection) {
        if ((ThingsAvailableToBuy_ListView.Selection().isEmpty())) {
            messages.ShowAlert("No Item Selected");
        } else {
            int i = listSelection.indexOf("]");
            int euro = listSelection.indexOf("€") + 1;
            String price = listSelection.substring(euro);
            String oldCredit = CreditAmount_Label.Text().replace("€", "");
            String y = listSelection.substring(1, i);
            String[] url_IDs = y.split(":");
            Web_PlaceOrder.Url(baseURL + sID + startValue[3] + "&entity=orders&method=POST&tID=" + url_IDs[0] + "&sellerID=" + url_IDs[1] + "&slotNum=1&buyerID=" + startValue[1]);
            Web_PlaceOrder.Get();
            Double diffCredit =Double.parseDouble(oldCredit) -Double.parseDouble(price);
            String newCredit = Double.toString(diffCredit);
            CreditAmount_Label.Text("€" + newCredit);
            Web_Credit2.Url("https://fachtnaroe.net/bananas?sessionID=a1b2c3d4&entity=person&method=GET&pID=" + startValue[1] + "&Credit=" + newCredit);
            Web_Credit2.Get();
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
                    //gets Credit amount
                    if(tableName.equals("person") && fieldName.equals("Credit")){
                        oneEntryInTheListView = jsonIsMySon.getJSONObject(i).getString("Credit");
                        ListViewItemArray.add(oneEntryInTheListView);
                    }
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
                    else if ((tableName.equals("prettyorders") && fieldName.equals("buyerID")) && (Integer.valueOf(jsonIsMySon.getJSONObject(i).getString(fieldName)).equals( Integer.valueOf(startValue[1])))) {
                        oneEntryInTheListView = "[" + jsonIsMySon.getJSONObject(i).getString("oID")
                                + "] " + jsonIsMySon.getJSONObject(i).getString("tName")
                                + " from " + jsonIsMySon.getJSONObject(i).getString("seller")
                                + " [ tID: " + jsonIsMySon.getJSONObject(i).getString("tID") + " ]";
                        ListViewItemArray.add(oneEntryInTheListView);
                    }
                }
                YailList tempData = YailList.makeList(ListViewItemArray);
                if(tableName.equals("person") && fieldName.equals("Credit")){
                    CreditAmount_Label.Text("€" + tempData.get(1));
                }
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