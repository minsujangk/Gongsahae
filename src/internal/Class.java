package internal;

import java.util.ArrayList;

public class Class {
	String subjectName;
	String subjectCode;
	int subjectScore;
	String teacherName;
	int classNumber;
	ArrayList<String> classTime;
	String subjectDepartment;

	public Class() {
		// TODO Auto-generated constructor stub
	}

	public Class(String subjectDepartment, String subjectName, String subjectCode, int subjectScore, String teacherName, int classNumber,
			ArrayList<String> classTime) {
		this.subjectDepartment = subjectDepartment;
		this.subjectName = subjectName;
		this.subjectCode = subjectCode;
		this.subjectScore = subjectScore;
		this.teacherName = teacherName;
		this.classNumber = classNumber;
		this.classTime = classTime;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public int getSubjectScore() {
		return subjectScore;
	}

	public void setSubjectScore(int subjectScore) {
		this.subjectScore = subjectScore;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public int getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(int classNumber) {
		this.classNumber = classNumber;
	}

	public ArrayList<String> getClassTime() {
		return classTime;
	}

	public void setClassTime(ArrayList<String> classTime) {
		this.classTime = classTime;
	}

	public String getSubjectDepartment() {
		return subjectDepartment;
	}

	public void setSubjectDepartment(String subjectDepartment) {
		this.subjectDepartment = subjectDepartment;
	}

	public String getClassTimeText() {
		String temp = "";
		for (int i = 0; i < classTime.size(); i++) {
			temp += classTime.get(i);
			if (i != classTime.size() - 1)
				temp += "/";
		}
		return temp;
	}

	public String getSubjectInfo() {
		return subjectName + " - " + classNumber + "분반 - " + teacherName + " - " + getClassTimeText();
	}

	public String getClassInfoForTable() {
		return subjectName + "" + classNumber + "분반" + teacherName;
	}
}
