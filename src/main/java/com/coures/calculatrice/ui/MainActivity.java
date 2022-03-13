package com.coures.calculatrice.ui;

import android.content.pm.ActivityInfo;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coures.calculatrice.R;
import com.coures.calculatrice.adapters.HistoryAdapter;
import com.coures.calculatrice.model.History;
import com.coures.calculatrice.utils.HistoryManager;
import com.coures.calculatrice.utils.Util;
import com.github.ayaanqui.expressionresolver.Resolver;
import com.github.ayaanqui.expressionresolver.objects.Response;

public class MainActivity extends AppCompatActivity {

    //numbers
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;
    //operations
    private Button btnAdd, btnMinus, btnMul, btnDiv, btnMod;
    //others
    private Button btnParentheses, btnClear, btnAddMinusPlus, btnDot, btnEqual;
    //function
    private Button btnE, btnRad, btnDeg, btnCos, btnSin, btnTan, btnACos,
            btnASin, btnATan, btnLn, btnLog, btnExp, btnAbs, btnFact, btnSqrt;

    private EditText input;
    //
    private ImageView btnDelete;
    private ImageView btnAdv;
    //
    private TextView result;
    //
    private StringBuffer expression;
    private static final String TAG = MainActivity.class.getName();
    //
    private RelativeLayout parentHistory;
    private RecyclerView rvHistory;
    private Button btnClearHistory;
    private ImageView btnHistory;
    private HistoryAdapter historyAdapter;
    private boolean isHistoryOpened = false;
    //
    private static Resolver expressionResolver = new Resolver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expression = new StringBuffer();
        initInput();
        initView();
        addListeners();
        if (Util.isLandscape(MainActivity.this)) {
            initViewFun();
            addListenersFunction();
        }
        initHistory();
    }

    private void initHistory(){
        parentHistory = findViewById(R.id.parentHistory);
        rvHistory = findViewById(R.id.rvHistory);
        btnClearHistory = findViewById(R.id.btnClearHistory);
        btnHistory = findViewById(R.id.btnHistory);
        historyAdapter = new HistoryAdapter(this,HistoryManager.get());
        rvHistory.setHasFixedSize(true);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setAdapter(historyAdapter);
        btnClearHistory.setOnClickListener((e)->{
           HistoryManager.clear();
           historyAdapter.notifyDataSetChanged();
           toggleHistory();
        });
        btnHistory.setOnClickListener((e)->{
            toggleHistory();
        });
    }

    private void showHistory(){
        if (isHistoryOpened){
            btnHistory.setBackgroundResource(R.drawable.ic_history_active);
            Util.slideRight(this,parentHistory);
        }else {
            Util.slideLeft(this,parentHistory);
            btnHistory.setBackgroundResource(R.drawable.ic_history);
        }
    }
    private void toggleHistory(){
        isHistoryOpened=!isHistoryOpened;
        if (isHistoryOpened)
        parentHistory.setVisibility(View.VISIBLE);
        showHistory();
    }

    private void initInput() {
        input = findViewById(R.id.input);
        input.setEnabled(false);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (expression.length()>0)
                expression.delete(0, expression.length());
                expression.append(charSequence.toString());
                if (TextUtils.isEmpty(charSequence))
                    btnDelete.setEnabled(false);
                else
                    btnDelete.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
    }
    private void initView() {

        //numbers
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnParentheses = findViewById(R.id.btnKeyParentheses);
        btnAdv = findViewById(R.id.btnAdv);
        //operation
        btnAdd = findViewById(R.id.btnAdd);
        btnMinus = findViewById(R.id.btnMinus);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);
        btnMod = findViewById(R.id.btnMod);
        //others
        btnClear = findViewById(R.id.btnClear);
        btnAddMinusPlus = findViewById(R.id.btnAddMinusPlus);
        btnDot = findViewById(R.id.btnDot);
        btnEqual = findViewById(R.id.btnEqual);

        result = findViewById(R.id.result);

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setEnabled(false);


    }
    private void addListeners() {
        btn0.setOnClickListener((v) -> handleInputNumbers("0"));
        btn1.setOnClickListener((v) -> handleInputNumbers("1"));
        btn2.setOnClickListener((v) -> handleInputNumbers("2"));
        btn3.setOnClickListener((v) -> handleInputNumbers("3"));
        btn4.setOnClickListener((v) -> handleInputNumbers("4"));
        btn5.setOnClickListener((v) -> handleInputNumbers("5"));
        btn6.setOnClickListener((v) -> handleInputNumbers("6"));
        btn7.setOnClickListener((v) -> handleInputNumbers("7"));
        btn8.setOnClickListener((v) -> handleInputNumbers("8"));
        btn9.setOnClickListener((v) -> handleInputNumbers("9"));
        btnParentheses.setOnClickListener((e) -> {
            if (Util.isParenthesesClosed(input.getText().toString())) {
                handleInputParentheses("(");
            } else {
                handleInputParentheses(")");
            }
        });
        btnParentheses.setOnLongClickListener((e)->{
            handleInput(")");
            return true;
        });
        //operation
        btnAdd.setOnClickListener((v) -> handleInputOperators("+"));
        btnMinus.setOnClickListener((v) -> handleInputOperators("-"));
        btnMul.setOnClickListener((v) -> handleInputOperators("x"));
        btnDiv.setOnClickListener((v) -> handleInputOperators("/"));
        btnMod.setOnClickListener((v) -> handleInputOperators("x0.01"));
        //others
        btnClear.setOnClickListener((v) -> {
            input.setText("");
            result.setText("");
        });
        btnAddMinusPlus.setOnClickListener((v) -> {
            handleInput("(-");
        });
        btnDot.setOnClickListener((v) -> {
            if (Util.canAddDot(input.getText().toString()))
                handleInput(".");
        });
        btnEqual.setOnClickListener((e) -> {

            Response response  = expressionResolver
                    .setExpression(cleanExpression())
                    .solveExpression();
            if (!response.success){
                Toast.makeText(MainActivity.this,response.errors[0],Toast.LENGTH_LONG).show();
            }else {
                double res = response.result;
                result.setText(String.valueOf(res));
                HistoryManager.add(new History(expression.toString(),res));
                historyAdapter.notifyDataSetChanged();
                expression = new StringBuffer();
                input.setText("");

            }


        });

        btnDelete.setOnClickListener((e) -> {
            if (expression.length() > 0 && Util.isOpenParentheses(expression.charAt(expression.length() - 1))) {
                deleteFunction();
            } else {
                deleteLetter();
            }

        });
        btnAdv.setOnClickListener((e) -> {
            toggleOrientation();
        });
    }
    private String cleanExpression(){
        StringBuilder s = new StringBuilder();
        for (int i=0;i<expression.length();i++){
            if (expression.charAt(i) == 'x')
                s.append('*');
            else s.append(expression.charAt(i));
        }
        return s.toString();
    }
    private void toggleOrientation(){
        if(Util.isLandscape(MainActivity.this)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            btnAdv.setBackgroundResource(R.drawable.ic_scientific_calculator_24);
        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            btnAdv.setBackgroundResource(R.drawable.ic_scientific_calculator_active_24);
        }
    }

    private void deleteLetter(){
        expression.deleteCharAt(expression.length() - 1);
        input.setText(expression.toString());
    }
    private void deleteFunction() {
        String res = Util.isFunction(expression.toString());
        if (res != null){
            int length = expression.length();
            for(int i=0; i<=res.length() ;i++)
                expression.deleteCharAt(length-1-i);
            input.setText(expression.toString());
        }
        else
            deleteLetter();

    }

    private void initViewFun() {
        //functions
        btnE = findViewById(R.id.btnE);
        btnRad = findViewById(R.id.btnRad);
        btnDeg = findViewById(R.id.btnDeg);
        btnCos = findViewById(R.id.btnCos);
        btnSin = findViewById(R.id.btnSin);
        btnTan = findViewById(R.id.btnTan);
        btnACos = findViewById(R.id.btnArcCos);
        btnASin = findViewById(R.id.btnArcSin);
        btnATan = findViewById(R.id.btnArcTan);
        btnLn = findViewById(R.id.btnLn);
        btnLog = findViewById(R.id.btnLog);
        btnExp = findViewById(R.id.btnExp);
        btnAbs = findViewById(R.id.btnAbs);
        btnFact = findViewById(R.id.btnFact);
        btnSqrt = findViewById(R.id.btnSqrt);


    }
    private void addListenersFunction() {
        btnE.setOnClickListener((v) -> handleInputFunction("e"));
        btnRad.setOnClickListener((v) -> handleInputFunction("rad("));
        btnDeg.setOnClickListener((v) -> handleInputFunction("deg("));
        btnCos.setOnClickListener((v) -> handleInputFunction("cos("));
        btnSin.setOnClickListener((v) -> handleInputFunction("sin("));
        btnTan.setOnClickListener((v) -> handleInputFunction("tan("));
        btnACos.setOnClickListener((v) -> handleInputFunction("arccos("));
        btnASin.setOnClickListener((v) -> handleInputFunction("arcsin("));
        btnATan.setOnClickListener((v) -> handleInputFunction("arctan("));
        btnLn.setOnClickListener((v) -> handleInputFunction("ln("));
        btnLog.setOnClickListener((v) -> handleInputFunction("log("));
        btnExp.setOnClickListener((v) -> handleInputFunction("exp("));
        btnAbs.setOnClickListener((v) -> handleInputFunction("abs("));
        btnFact.setOnClickListener((v) -> handleInputFunction("fact("));
        btnSqrt.setOnClickListener((v) -> handleInputFunction("sqrt("));
    }
    private void handleInputFunction(String s) {
        if (expression.length() > 0
                && !Util.isOperator(expression.charAt(expression.length() - 1))
                && !Util.isOpenParentheses(expression.charAt(expression.length() - 1)))
            handleInput("x" + s);
        else
            handleInput(s);

       // Log.e(TAG,Util.isFunction(expression.toString()));
    }
    private void handleInputParentheses(String c ) {
       if ("(".equals(c)){
           if (expression.length()>0)
                if(  Util.isCloseParentheses(expression.charAt(expression.length()-1))
                   ||Util.isNumber(expression.charAt(expression.length()-1)))
                    handleInput("x");

       }
       else{

           if (expression.length()>0 && Util.isOpenParentheses(expression.charAt(expression.length()-1)))
               return;
        }
       handleInput(c);
    }
    private void handleInputOperators(String s) {
        if (expression.length() > 0
                &&
                (Util.isOperator(expression.charAt(expression.length() - 1))
                || expression.charAt(expression.length()-1) == '.'
                || Util.isOpenParentheses(expression.charAt(expression.length() - 1)))
                 )
            expression.deleteCharAt(expression.length() - 1);
        handleInput(s);
    }
    private void handleInputNumbers(String s) {
        if (Util.isNumber(s.charAt(0)))
            if (expression.length()>0 && Util.isCloseParentheses(expression.charAt(expression.length()-1)))
                expression.append("x");
        handleInput(s);
    }
    private void handleInput(String s) {
        input.setText(expression.append(s).toString());
    }


}