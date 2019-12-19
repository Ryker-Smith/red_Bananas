package net.fachtnaroe.red_bananas;

import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.Notifier;
import com.google.appinventor.components.runtime.TextBox;
import com.google.appinventor.components.runtime.VerticalArrangement;
import com.google.appinventor.components.runtime.Web;

import org.json.JSONException;
import org.json.JSONObject;

public class NewSaleItem extends Form implements HandlesEventDispatching {
    private VerticalArrangement VArr, VArr_EmptySpace;
    private HorizontalArrangement HArr_Price;
    private Button saveThisBtn;
    private TextBox nameTxtBox, descriptionTxtBox, priceTxtBox;
    private Label nameLabel, descriptionLabel, priceLabel, euroSymbolLablel, titleLabel;
    private Web webSaveNewItem;
    private Notifier savedNotifier;
    private String baseURL = "https://fachtnaroe.net/bananas?";
    private String[] startValue;

    protected void $define() {
        this.BackgroundColor(Component.COLOR_ORANGE);
        //[0]=extra quotation mark(not to be used) [1]=pId [2]=Username [3]=SessionID [4]=extra quotation mark(not to be used)
        startValue = this.startupValue.split("<SPLIT>");
        VArr = new VerticalArrangement(this);
        VArr.Height(LENGTH_FILL_PARENT);
        VArr.Width(LENGTH_FILL_PARENT);
        VArr.BackgroundColor(Component.COLOR_ORANGE);
        VArr.Image("FDS_PossibleLogo_04.png");

        titleLabel = new Label(VArr);
        titleLabel.Width(LENGTH_FILL_PARENT);
        titleLabel.HeightPercent(20);
        titleLabel.Text("Food Delivery Service");
        titleLabel.FontSize(36);
        titleLabel.FontBold(true);
        titleLabel.TextAlignment(Component.ALIGNMENT_CENTER);

        nameLabel = new Label(VArr);
        nameLabel.Width(LENGTH_FILL_PARENT);
        nameLabel.Text("Name");
        nameLabel.FontSize(14);
        nameLabel.FontBold(true);

        nameTxtBox = new TextBox(VArr);
        nameTxtBox.Width(LENGTH_FILL_PARENT);
        nameTxtBox.BackgroundColor(Component.COLOR_WHITE);
        nameTxtBox.FontSize(14);

        descriptionLabel = new Label(VArr);
        descriptionLabel.Width(LENGTH_FILL_PARENT);
        descriptionLabel.Text("Description");
        descriptionLabel.FontSize(14);
        descriptionLabel.FontBold(true);

        descriptionTxtBox = new TextBox(VArr);
        descriptionTxtBox.Width(LENGTH_FILL_PARENT);
        descriptionTxtBox.BackgroundColor(Component.COLOR_WHITE);
        descriptionTxtBox.FontSize(14);

        priceLabel = new Label(VArr);
        priceLabel.Width(LENGTH_FILL_PARENT);
        priceLabel.Text("Price");
        priceLabel.FontSize(14);
        priceLabel.FontBold(true);

        HArr_Price = new HorizontalArrangement(VArr);
        HArr_Price.Width(LENGTH_FILL_PARENT);

        euroSymbolLablel = new Label(HArr_Price);
        euroSymbolLablel.WidthPercent(5);
        euroSymbolLablel.Text("€");
        euroSymbolLablel.FontSize(14);
        euroSymbolLablel.FontBold(true);

        priceTxtBox = new TextBox(HArr_Price);
        priceTxtBox.Width(LENGTH_FILL_PARENT);
        priceTxtBox.BackgroundColor(Component.COLOR_WHITE);
        priceTxtBox.FontSize(14);

        saveThisBtn = new Button(VArr);
        saveThisBtn.Width(LENGTH_FILL_PARENT);
        saveThisBtn.Text("SAVE THIS");
        saveThisBtn.FontSize(14);

        VArr_EmptySpace = new VerticalArrangement(VArr);
        VArr_EmptySpace.Height(LENGTH_FILL_PARENT);

        webSaveNewItem = new Web(this);

        savedNotifier = new Notifier(this);
        savedNotifier.BackgroundColor(Component.COLOR_RED);
        savedNotifier.TextColor(Component.COLOR_WHITE);

        EventDispatcher.registerEventForDelegation(this, formName, "Click");
        EventDispatcher.registerEventForDelegation(this, formName, "GotText");
    }

    public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params) {
        if (eventName.equals("Click")) {
            if (component.equals((saveThisBtn))) {
                if (!(nameTxtBox.Text().equals("")||descriptionTxtBox.Text().equals("")||priceTxtBox.Text().equals(""))) {
                    saveThis();
                }
                else {
                    savedNotifier.ShowAlert("Error please fill all fields");
                }
                return true;
            }
            return true;
        }
        else if (eventName.equals("GotText")) {
            jsonSavedCheck(params[1].toString(),params[3].toString());
            return true;
        }
        return false;
    }
    public void saveThis() {
        webSaveNewItem.Url(baseURL + "sessionID=" + startValue[3] + "&method=POST&entity=thing&tName=" + nameTxtBox.Text() + "&tDescription=" + descriptionTxtBox.Text() + "&tPrice=" + priceTxtBox.Text() + "&tSoldBy=" + startValue[1]);
        webSaveNewItem.Get();
    }
    public void SalesGo() {
        switchFormWithStartValue("Sales",this.startupValue);
    }
    public void jsonSavedCheck(String status, String textOfResponse) {
        if (status.equals("200")) try {
            // See:  https://stackoverflow.com/questions/5015844/parsing-json-object-in-java
            JSONObject parser = new JSONObject(textOfResponse);
            String status_json = parser.getString("Status");
            if(status_json.contains("OK")) {
                savedNotifier.ShowAlert("Item has been saved");
                SalesGo();
            }
            else{
                savedNotifier.ShowAlert("Item Has Not been Saved");
            }
        } catch (JSONException e) {
            // if an exception occurs, code for it in here
            savedNotifier.ShowMessageDialog("Error 3.353; JSON Exception (check password) ", "Information", "OK");
        }
        else {
            savedNotifier.ShowMessageDialog("Error 3.356; Problem connecting with server", "Information", "OK");
        }
    }

}