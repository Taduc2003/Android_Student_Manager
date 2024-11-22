package vn.edu.hust.activityexamples

data class StudentModel(var studentName: String, var studentId: String){
    override fun toString(): String {
        return "$studentName - $studentId"
    }
}

