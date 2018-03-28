package internal;

import java.util.ArrayList;

public class Randomize {
	ArrayList<ArrayList<internal.Class>> available;
	ArrayList<internal.Class> selectedList;
	ArrayList<internal.Class> checkedList;
	static ArrayList<String> required;
	ArrayList<internal.Class> classList;
	private static ArrayList<internal.Class> magicList;
	private static ArrayList<internal.Class> initialList;

	public Randomize(ArrayList<internal.Class> classList, ArrayList<internal.Class> selectedList, ArrayList<internal.Class> checkedList) {
		// TODO Auto-generated constructor stub
		required = new ArrayList<String>();
		available = new ArrayList<ArrayList<Class>>();
		magicList = new ArrayList<internal.Class>();
		for (internal.Class eachClass : selectedList) {
			if (!checkedList.contains(eachClass))
				magicList.add(eachClass);
		}
		initialList = new ArrayList<internal.Class>();
		for (internal.Class eachClass : selectedList) {
			if (checkedList.contains(eachClass))
				initialList.add(eachClass);
		}
		required = classListToRequired(magicList);
		this.selectedList = selectedList;
		this.checkedList = checkedList;
		this.classList = classList;

	}

	public static ArrayList<String> getAssignedClassTime(ArrayList<internal.Class> selectedList) {
		ArrayList<String> assignedClassTime = new ArrayList<String>();
		for (internal.Class selectedClass : selectedList) {
			for (String classTime : selectedClass.getClassTime()) {
				assignedClassTime.add(classTime);
			}
		}

		return assignedClassTime;
	}

	public static ArrayList<String> classListToRequired(ArrayList<internal.Class> selectedList) {
		ArrayList<String> array = new ArrayList<String>();
		for (int i = 0; i < selectedList.size(); i++) {
			array.add(selectedList.get(i).getSubjectName());
		}
		return array;
	}

	public void recursion() {

		recursion(initialList, 0);
	}

	public void recursion(ArrayList<internal.Class> selectedList, int check) {
		if (check == required.size())
			available.add(selectedList);
		else {
			String nowSubjectName = required.get(check);
			for (int i = 0; i < classList.size(); i++) {
				internal.Class nowClass = classList.get(i);
				if (nowClass.getSubjectName().equals(nowSubjectName) && !checkForCollision(selectedList, nowClass)) {
					ArrayList<internal.Class> tempList = (ArrayList<Class>) selectedList.clone();
					tempList.add(nowClass);
					recursion(tempList, check + 1);
				}
			}
		}
	}

	public static boolean checkForCollision(ArrayList<internal.Class> selectedList, internal.Class newClass) {
		ArrayList<String> assignedClassTime = getAssignedClassTime(selectedList);
		for (int i = 0; i < assignedClassTime.size(); i++) {
			for (int j = 0; j < newClass.getClassTime().size(); j++) {
				if (assignedClassTime.get(i).equals(newClass.getClassTime().get(j)))
					return true;
			}
		}
		return false;
	}

	public ArrayList<ArrayList<internal.Class>> getAvailable() {
		recursion();
		return available;
	}

	public void setAvailable(ArrayList<ArrayList<internal.Class>> available) {
		this.available = available;
	}

}
