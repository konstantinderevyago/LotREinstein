package by.jetfire.lotreinstein.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.plattysoft.leonids.ParticleSystem;
import com.plattysoft.leonids.modifiers.ScaleModifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import by.jetfire.lotreinstein.R;
import by.jetfire.lotreinstein.entity.TaskItem;
import by.jetfire.lotreinstein.utils.Utils;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_container)
    protected LinearLayout container;
    @BindView(R.id.main_win)
    protected TextView win;

    private List<TaskItem> taskItems;
    private Stack<List<TaskItem>> previousStack;
    private Stack<List<TaskItem>> nextStack;
    private List<TaskItem> currentTaskState;

    private boolean menuClicked;

    private ParticleSystem particleSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        taskItems = Utils.getTaskItems(this);
        nextStack = new Stack<>();
        previousStack = new Stack<>();

        buildTable(container);
        currentTaskState = getTaskState();
    }

    private void buildTable(ViewGroup view) {
        if (taskItems != null && !taskItems.isEmpty()) {
            buildTitleColumn(view);
            for (TaskItem taskItem : taskItems) {
                buildColumn(view, taskItem.getNumber());
            }
        }
    }

    private void buildTitleColumn(ViewGroup view) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.column_item, view, false);
        buildTextView(linearLayout, R.string.number);
        buildTextView(linearLayout, R.string.name);
        buildTextView(linearLayout, R.string.color);
        buildTextView(linearLayout, R.string.alcohol);
        buildTextView(linearLayout, R.string.smoke);
        buildTextView(linearLayout, R.string.thing);
        view.addView(linearLayout);
    }

    private void buildColumn(ViewGroup view, int number) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.column_item, view, false);
        buildTextView(linearLayout, String.valueOf(number));
        buildSpinner(linearLayout, R.array.names);
        buildSpinner(linearLayout, R.array.colors);
        buildSpinner(linearLayout, R.array.alcohols);
        buildSpinner(linearLayout, R.array.smokes);
        buildSpinner(linearLayout, R.array.things);
        view.addView(linearLayout);
    }

    private void buildTextView(ViewGroup view, int textRes) {
        buildTextView(view, getString(textRes));
    }

    private void buildTextView(ViewGroup view, String text) {
        TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.text_item, view, false);
        textView.setText(text);
        view.addView(textView);
    }

    private void buildSpinner(ViewGroup view, int arrayRes) {
        Spinner spinner = (Spinner) LayoutInflater.from(this).inflate(R.layout.spinner_item, view, false);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(arrayRes));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        final boolean[] create = {true};
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!create[0]) {
                    if (!menuClicked) {
                        List<TaskItem> taskState = getTaskState();
                        if (!checkDuplicates(taskState)) {
                            nextStack.clear();
                            previousStack.push(currentTaskState);
                            currentTaskState = taskState;
                        } else {
                            menuClicked = true;
                            setTaskState(currentTaskState);
                            showDuplicatesDialog();
                        }
                    } else {
                        menuClicked = false;
                    }

                    checkWin();
                } else {
                    create[0] = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        view.addView(spinner);
    }

    private List<TaskItem> getTaskState() {
        List<TaskItem> taskItems = new ArrayList<>();
        for (int i = 1; i < container.getChildCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) container.getChildAt(i);
            Spinner nameSpinner = (Spinner) linearLayout.getChildAt(1);
            Spinner colorSpinner = (Spinner) linearLayout.getChildAt(2);
            Spinner alcoholSpinner = (Spinner) linearLayout.getChildAt(3);
            Spinner smokeSpinner = (Spinner) linearLayout.getChildAt(4);
            Spinner thingSpinner = (Spinner) linearLayout.getChildAt(5);

            taskItems.add(new TaskItem(i, (String) nameSpinner.getSelectedItem(),
                    (String) colorSpinner.getSelectedItem(), (String) alcoholSpinner.getSelectedItem(),
                    (String) smokeSpinner.getSelectedItem(), (String) thingSpinner.getSelectedItem()));
        }
        return taskItems;
    }

    private void setTaskState(List<TaskItem> taskItems) {
        for (int i = 1; i < container.getChildCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) container.getChildAt(i);
            Spinner nameSpinner = (Spinner) linearLayout.getChildAt(1);
            Spinner colorSpinner = (Spinner) linearLayout.getChildAt(2);
            Spinner alcoholSpinner = (Spinner) linearLayout.getChildAt(3);
            Spinner smokeSpinner = (Spinner) linearLayout.getChildAt(4);
            Spinner thingSpinner = (Spinner) linearLayout.getChildAt(5);

            TaskItem taskItem = taskItems.get(i - 1);
            nameSpinner.setSelection(getArrayPosition(R.array.names, taskItem.getName()));
            colorSpinner.setSelection(getArrayPosition(R.array.colors, taskItem.getColor()));
            alcoholSpinner.setSelection(getArrayPosition(R.array.alcohols, taskItem.getAlcohol()));
            smokeSpinner.setSelection(getArrayPosition(R.array.smokes, taskItem.getSmoke()));
            thingSpinner.setSelection(getArrayPosition(R.array.things, taskItem.getThing()));
        }
    }

    private int getArrayPosition(int arrayRes, String item) {
        return Arrays.asList(getResources().getStringArray(arrayRes)).indexOf(item);
    }

    private boolean checkDuplicates(List<TaskItem> taskItems) {
        for (TaskItem taskItem : taskItems) {
            String taskName = taskItem.getName();
            String taskColor = taskItem.getColor();
            String taskAlcohol = taskItem.getAlcohol();
            String taskSmoke = taskItem.getSmoke();
            String taskThing = taskItem.getThing();
            for (TaskItem item : taskItems) {
                if (!taskItem.equals(item)) {
                    String name = item.getName();
                    String color = item.getColor();
                    String alcohol = item.getAlcohol();
                    String smoke = item.getSmoke();
                    String thing = item.getThing();
                    if ((!TextUtils.isEmpty(taskName) && !TextUtils.isEmpty(name) && taskName.equals(name)) ||
                            (!TextUtils.isEmpty(taskColor) && !TextUtils.isEmpty(color) && taskColor.equals(color)) ||
                            (!TextUtils.isEmpty(taskAlcohol) && !TextUtils.isEmpty(alcohol) && taskAlcohol.equals(alcohol)) ||
                            (!TextUtils.isEmpty(taskSmoke) && !TextUtils.isEmpty(smoke) && taskSmoke.equals(smoke)) ||
                            (!TextUtils.isEmpty(taskThing) && !TextUtils.isEmpty(thing) && taskThing.equals(thing))) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private void showDuplicatesDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.duplicate_title)
                .setMessage(R.string.duplicate_message)
                .setPositiveButton(R.string.fine, null)
                .show();
    }

    private void checkWin() {
        if (taskItems.equals(currentTaskState)) {
            container.setEnabled(false);

            ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
            animator.setDuration(1000);
            animator.setInterpolator(new OvershootInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();

                    win.setScaleX(value);
                    win.setScaleY(value);

                    win.setRotation(360 * value);
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    win.setVisibility(View.VISIBLE);

                    particleSystem = new ParticleSystem(MainActivity.this, 90, R.drawable.star, 1000, R.id.main_root);
                    particleSystem.setSpeedByComponentsRange(-0.1f, 0.1f, -0.1f, 0.02f)
                            .setAcceleration(0.000003f, 90)
                            .setInitialRotationRange(0, 360)
                            .setRotationSpeed(120)
                            .setFadeOut(1000)
                            .addModifier(new ScaleModifier(0f, 1.5f, 0, 1500))
                            .emit(container, 100);
                }
            });
            animator.start();
        }
    }

    private void hideWin() {
        container.setEnabled(true);
        win.setVisibility(View.GONE);
        if (particleSystem != null) {
            particleSystem.cancel();
        }
    }

    @OnClick(R.id.main_win)
    protected void onWinClick() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.undo:
                undo();
                return true;
            case R.id.redo:
                redo();
                return true;
            case R.id.conditions:
                showConditionsDialog();
                return true;
            case R.id.restart:
                showRestartDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void undo() {
        if (previousStack.size() > 0) {
            menuClicked = true;

            hideWin();

            nextStack.push(currentTaskState);
            currentTaskState = previousStack.pop();
            setTaskState(currentTaskState);
        }
    }

    private void redo() {
        if (nextStack.size() > 0) {
            menuClicked = true;

            previousStack.push(currentTaskState);
            currentTaskState = nextStack.pop();
            setTaskState(currentTaskState);
        }
    }

    private void showConditionsDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.conditions)
                .setMessage(getConditions())
                .show();
    }

    private String getConditions() {
        return getString(R.string.condition_1) + "\n" +
                getString(R.string.condition_2) + "\n" +
                getString(R.string.condition_3) + "\n" +
                getString(R.string.condition_4) + "\n" +
                getString(R.string.condition_5) + "\n" +
                getString(R.string.condition_6) + "\n" +
                getString(R.string.condition_7) + "\n" +
                getString(R.string.condition_8) + "\n" +
                getString(R.string.condition_9) + "\n" +
                getString(R.string.condition_10) + "\n" +
                getString(R.string.condition_11) + "\n" +
                getString(R.string.condition_12) + "\n" +
                getString(R.string.condition_13) + "\n" +
                getString(R.string.condition_14) + "\n" +
                getString(R.string.condition_15);
    }

    private void showRestartDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.restart_message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hideWin();

                        previousStack.clear();
                        nextStack.clear();
                        clearTable();
                        currentTaskState = getTaskState();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void clearTable() {
        for (int i = 1; i < container.getChildCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) container.getChildAt(i);
            ((Spinner) linearLayout.getChildAt(1)).setSelection(0);
            ((Spinner) linearLayout.getChildAt(2)).setSelection(0);
            ((Spinner) linearLayout.getChildAt(3)).setSelection(0);
            ((Spinner) linearLayout.getChildAt(4)).setSelection(0);
            ((Spinner) linearLayout.getChildAt(5)).setSelection(0);
        }
    }
}
