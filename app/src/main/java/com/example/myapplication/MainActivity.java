package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView solutionTv, resultTV;
    Button btnC, btnBracketOpen, btnBracketClose;
    Button btnDivide, btnMultiply, btnSubstract, btnAdd, btnEquals;
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    Button btnAC, btnDot;
    Button btnHis;
    String hisCalc;

    void assignId(Button btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        solutionTv = (TextView) findViewById(R.id.solutions_tv);
        resultTV = (TextView) findViewById(R.id.result_tv);

        assignId(btnC, R.id.button_c);
        assignId(btnBracketOpen, R.id.button_openbracket);
        assignId(btnBracketClose, R.id.button_closebracket);
        assignId(btnDivide, R.id.button_divide);
        assignId(btnMultiply, R.id.button_multiply);
        assignId(btnSubstract, R.id.button_substract);
        assignId(btnAdd, R.id.button_add);
        assignId(btnEquals, R.id.button_equals);

        assignId(btn0, R.id.button_0);
        assignId(btn1, R.id.button_1);
        assignId(btn2, R.id.button_2);
        assignId(btn3, R.id.button_3);
        assignId(btn4, R.id.button_4);
        assignId(btn5, R.id.button_5);
        assignId(btn6, R.id.button_6);
        assignId(btn7, R.id.button_7);
        assignId(btn8, R.id.button_8);
        assignId(btn9, R.id.button_9);
        assignId(btnAC, R.id.button_ac);
        assignId(btnDot, R.id.button_dot);
        assignId(btnHis, R.id.button_his);

    }

    @Override
    public void onClick(View v) {

        Button btn = (Button) v;
        String btnText = btn.getText().toString();
        String datatoCal = solutionTv.getText().toString();
        Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);


        if(btnText.equals("AC")){
            solutionTv.setText("");
            resultTV.setText("0");
            return;
        }
        if(btnText.equals("=")){

            solutionTv.setText(resultTV.getText());
            hisCalc += datatoCal.toString() + "=" + resultTV.getText().toString();
            return;
        }
        if(btnText.equals("C")){
            datatoCal = datatoCal.substring(0, datatoCal.length() -1 );
        }else{
            if(datatoCal.equals("0")){
                datatoCal = btnText;
            }else{
                datatoCal += btnText;
            }
        }
        if(btnText.equals("H")){
            solutionTv.setText("");
            intent.putExtra("H", hisCalc);
            startActivity(intent);
            return;
        }

        solutionTv.setText(datatoCal);
        if(datatoCal.length() > 0){
            String result = getResult(datatoCal);
            if(!result.equals("Error")){
                resultTV.setText(result);
            }
        }else{
            datatoCal = "";
        }

    }
    String getResult(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String result = context.evaluateString(scriptable, data, "Javascript",1,null).toString();
            if(result.endsWith(".0")){
                result = result.replace(".0","");
            }
            return result;
        }catch(Exception e){
            return "Error";
        }
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("solution",solutionTv.getText().toString());
        outState.putString("result",resultTV.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        solutionTv.setText(savedInstanceState.getString("solution"));
        resultTV.setText(savedInstanceState.getString("result"));
    }
}