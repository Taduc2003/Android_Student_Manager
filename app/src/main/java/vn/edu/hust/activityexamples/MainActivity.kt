package vn.edu.hust.activityexamples

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var students: MutableList<StudentModel>
    private lateinit var adapter: ArrayAdapter<StudentModel>
    private lateinit var listView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        students = mutableListOf(
            StudentModel("Nguyễn Văn An", "SV001"),
            StudentModel("Trần Thị Bảo", "SV002"),
            StudentModel("Lê Hoàng Cường", "SV003"),
            StudentModel("Phạm Thị Dung", "SV004"),
            StudentModel("Đỗ Minh Đức", "SV005"),
            StudentModel("Vũ Thị Hoa", "SV006"),
            StudentModel("Hoàng Văn Hải", "SV007"),
            StudentModel("Bùi Thị Hạnh", "SV008"),
            StudentModel("Đinh Văn Hùng", "SV009"),
            StudentModel("Nguyễn Thị Linh", "SV010"),
            StudentModel("Phạm Văn Long", "SV011"),
            StudentModel("Trần Thị Mai", "SV012"),
            StudentModel("Lê Thị Ngọc", "SV013"),
            StudentModel("Vũ Văn Nam", "SV014"),
            StudentModel("Hoàng Thị Phương", "SV015"),
            StudentModel("Đỗ Văn Quân", "SV016"),
            StudentModel("Nguyễn Thị Thu", "SV017"),
            StudentModel("Trần Văn Tài", "SV018"),
            StudentModel("Phạm Thị Tuyết", "SV019"),
            StudentModel("Lê Văn Vũ", "SV020")
        )

        listView = findViewById(R.id.listView)
        adapter = ArrayAdapter<StudentModel>(this, android.R.layout.simple_list_item_1, students)
        listView.adapter = adapter

        // TODO: Thiet lap context menu cho doi tuong ListView
        registerForContextMenu(listView)
    }

  // TODO: Ham khoi tao cho context menu edit/remove
  override fun onCreateContextMenu(
    menu: ContextMenu?,
    v: View?,
    menuInfo: ContextMenu.ContextMenuInfo?
  ) {
    menuInflater.inflate(R.menu.context_menu, menu)
    super.onCreateContextMenu(menu, v, menuInfo)
  }

  // TODO: Ham xu ly su kien nhan vao context menu
  override fun onContextItemSelected(item: MenuItem): Boolean {
    val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
    val position = info.position
      if (position < 0 || position >= students.size) {
          Toast.makeText(this, "Vị trí không hợp lệ!", Toast.LENGTH_SHORT).show()
          return super.onContextItemSelected(item)
      }
    when (item.itemId) {
        R.id.edit -> {
            val selectedStudent = students[position]
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra("hoten", selectedStudent.studentName ?: "")
            intent.putExtra("mssv", selectedStudent.studentId ?: "")
            intent.putExtra("position", position)
            editlauncher.launch(intent)
        }
        R.id.remove -> {
            students.removeAt(position)
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Đã xóa sinh viên", Toast.LENGTH_SHORT).show()
        }

    }
    return super.onContextItemSelected(item)
  }
    private val editlauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val hoten = result.data?.getStringExtra("hoten")
            val mssv = result.data?.getStringExtra("mssv")
            val position = result.data?.getIntExtra("position", -1) ?: -1

            if (position == -1) { // Thêm mới
                students.add(StudentModel(hoten ?: "", mssv ?: ""))
            } else { // Chỉnh sửa
                students[position].studentName = hoten ?: ""
                students[position].studentId = mssv ?: ""
            }
            adapter.notifyDataSetChanged()
        }
    }
  // TODO: Ham khoi tao option menu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.option_menu, menu)
    return super.onCreateOptionsMenu(menu)
  }

  // TODO: Ham xu ly su kien nhan vao option menu
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
      when (item.itemId) {
          R.id.add_new -> {
              val intent = Intent(this, AddStudentActivity::class.java)
              launcher.launch(intent)
          }
      }
      return super.onOptionsItemSelected(item)
  }

    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val hoten = result.data?.getStringExtra("hoten")
            val mssv = result.data?.getStringExtra("mssv")

            if (!hoten.isNullOrEmpty() && !mssv.isNullOrEmpty()) {
                students.add(StudentModel(hoten, mssv)) // Thêm sinh viên vào danh sách
                (listView.adapter as ArrayAdapter<StudentModel>).notifyDataSetChanged() // Cập nhật giao diện
                Toast.makeText(this, "Thêm sinh viên thành công!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Dữ liệu không hợp lệ!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Thêm sinh viên bị hủy!", Toast.LENGTH_SHORT).show()
        }
    }

}