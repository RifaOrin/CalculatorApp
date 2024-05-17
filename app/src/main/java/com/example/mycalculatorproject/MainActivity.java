package com.example.mycalculatorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView inputTV, outputTV;
    private Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    private Button btnDelete,btnAdd,btnMinus,btnMultiply,btnDiv,btnEqual,btnDot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);

        inputTV = findViewById(R.id.input);
        outputTV = findViewById(R.id.output);

        func(btn0, R.id.zero);
        func(btn1, R.id.one);
        func(btn2, R.id.two);
        func(btn3, R.id.three);
        func(btn4, R.id.four);
        func(btn5, R.id.five);
        func(btn6, R.id.six);
        func(btn7, R.id.seven);
        func(btn8, R.id.eight);
        func(btn9, R.id.nine);
        func(btnDelete, R.id.delete);
        func(btnAdd, R.id.add);
        func(btnMinus, R.id.minus);
        func(btnMultiply, R.id.multiply);
        func(btnDiv, R.id.divide);
        func(btnEqual, R.id.equal);
        func(btnDot, R.id.dot);

    }
    void func (Button button, int id){
        button = findViewById(id);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Button clickBtn = (Button) v;
        String btnText = clickBtn.getText().toString();
        String calculateStored = inputTV.getText().toString();

        while (btnText.equals("Delete")){
            inputTV.setText("");
            outputTV.setText("0");
            return;
        }
        if (btnText.equals("=")){
            inputTV.setText(outputTV.getText().toString());
            outputTV.setText("0");
            return;
        }
        calculateStored += btnText;

        inputTV.setText(calculateStored);

        String res = getResult(calculateStored);
        if(!res.equals("Error")){
            res = res.replaceAll("\\.0$", "");
            int dotIndex = res.indexOf(".");
            if (dotIndex != -1) {
                int endIndex = Math.min(dotIndex + 4, res.length());
                res = res.substring(0, endIndex);
            }

            outputTV.setText(res);
        }
    }
    String getResult (String data){
        try{
            data = data.replace("×", "*")
                    .replace("÷", "/");

            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String resultOutput = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            return resultOutput;
        }
        catch(Exception e){
            return "Error";
        }

    }
}