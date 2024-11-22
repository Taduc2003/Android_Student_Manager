package vn.edu.hust.activityexamples

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_layout)

        val position = intent.getIntExtra("position", -1)
        val hoten = intent.getStringExtra("hoten") ?: ""
        val mssv = intent.getStringExtra("mssv") ?: ""

        val editHoten = findViewById<EditText>(R.id.edit_hoten)
        val editMssv = findViewById<EditText>(R.id.edit_mssv)

        editHoten.setText(hoten)
        editMssv.setText(mssv)

        if (editHoten == null || editMssv == null) {
            Log.e("EditStudentActivity", "EditText không tồn tại!")
        }

        setResult(Activity.RESULT_CANCELED)

        findViewById<Button>(R.id.button_ok).setOnClickListener {
            val intent = Intent()
            intent.putExtra("position", position)
            intent.putExtra("hoten", editHoten.text.toString())
            intent.putExtra("mssv", editMssv.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }

        findViewById<Button>(R.id.button_cancel).setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}