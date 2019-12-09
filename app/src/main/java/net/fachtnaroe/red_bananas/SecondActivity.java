package net.fachtnaroe.red_bananas;

import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.VerticalScrollArrangement;
import android.content.Intent;


public class SecondActivity extends Form implements HandlesEventDispatching {
    private Button butt1;
    private VerticalScrollArrangement VArr;
    private Label deletThisLater;

    protected void $define() {
        VArr = new VerticalScrollArrangement(this);

        butt1 = new Button(VArr);
        butt1.Text("Go Back");
        butt1.WidthPercent(100);
        butt1.HeightPercent(10);

        deletThisLater = new Label(VArr);
        deletThisLater.WidthPercent(100);
        deletThisLater.FontSize(12);
        deletThisLater.Text("Welcome to A second Screen I am proud of you human");

        EventDispatcher.registerEventForDelegation( this, formName, "Click" );
    }
    public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params){
        if (eventName.equals("Click")){
            mainActGo();
            return true;
        }
        else {
            return false;
        }
    }
    public void mainActGo(){
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }
}

