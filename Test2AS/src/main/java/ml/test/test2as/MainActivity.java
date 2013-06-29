package ml.test.test2as;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.*;

public class MainActivity extends Activity implements View.OnClickListener {

    TextView mtextOutput;
    EditText mtextInput;
    TextView mWordCount;
    int cnt;
    final static String FILENAME="appnotes.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up widgets
        setUpWidgets();

        // load text from files
        loadTextFromFile();
    }

    private void loadTextFromFile() {
        File f;
        f = new File(getFilesDir(), FILENAME);
        cnt = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));

            String line;

            while((line=br.readLine())!=null) {
                mtextOutput.setText(line + "\n" + mtextOutput.getText());
                cnt++;
            }

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        doWordCount(cnt);
    }

    private void doWordCount(int x)
    {
        mWordCount.setText("Word count: "+x);
        return;
    }

    private void setUpWidgets() {
        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);

        mtextOutput = (TextView) findViewById(R.id.textView);
        mtextInput = (EditText)findViewById(R.id.textInput);

        // set scrollable
        mtextOutput.setMovementMethod(new ScrollingMovementMethod());

        mWordCount = (TextView) findViewById(R.id.textView2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
     }

    public int onClickTestMenuItem()
    {


        return 0;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.save)
        {
            String s = String.valueOf(mtextInput.getText());
            mtextOutput.setText(s+"\n"+mtextOutput.getText());

            // clear input
            mtextInput.setText("");
            cnt++;

            doWordCount(cnt);
            // Write data to file
            FileOutputStream fo;
            try {
                fo = openFileOutput(FILENAME, Context.MODE_APPEND);
                fo.write(s.getBytes());
                fo.write("\n".getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    
}
